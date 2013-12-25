package com.bbf.spring.taskExcutor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bbf.trace.service.LogBeanService;

public class AppTask {
	
	
	private static void runNewTask(ApplicationContext ctx,ThreadPoolTaskExecutor taskExecutor,int pageNum, int cnt)
	{
		 CalSiteLogTask calSiteLogTask = (CalSiteLogTask)ctx.getBean("calSiteLogTask"); 
	        calSiteLogTask.setName("calSiteLogTask"+pageNum);
	        calSiteLogTask.setBegin(cnt*pageNum);
			taskExecutor.execute(calSiteLogTask);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] springConfig  = 
			{	
				"applicationContext.xml" 
			};
	 
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext(springConfig);
	 
		ThreadPoolTaskExecutor taskExecutor = 
			    (ThreadPoolTaskExecutor)ctx.getBean("taskExecutor"); 
		LogBeanService logBeanService =(LogBeanService)ctx.getBean("logBeanService"); 
		//计算总次数
        int cnt=10000;
        int total=logBeanService.getAllLogCnt();
        System.out.println(total);
        int pageNum=0;
        int mod=total%cnt;
        if(mod>0)
        {
        	pageNum=total/cnt +1;
        }else
        {
        	pageNum=total/cnt;
        }
        int initTreadCounter=0;
		 // 检查活动的线程，如果活动线程数为0则关闭线程池 
        for(;;){ 
                int count = taskExecutor.getActiveCount(); 
                System.out.println("Active Threads : " + count); 
                try{ 
                        Thread.sleep(10000); 
                }catch(InterruptedException e){ 
                        e.printStackTrace(); 
                } 
                if(count==0){
                	//因为跑太多会oom，所以也设定每次的线程数 
                	int threadCnt=10;
                	  if(pageNum>initTreadCounter*threadCnt )
                	  {
                			  int begin= initTreadCounter*threadCnt;
                			  int setter=begin+threadCnt;
                			  
                			  for(int i=begin;i<setter;i++)
                		        {
                		        	 runNewTask(ctx,taskExecutor,i,cnt);
                		        }
                		  initTreadCounter=initTreadCounter+1;
                		  
                	  }else
                	  {
                		  taskExecutor.shutdown(); 
                          break; 
                	  }
                      
                } 
        } 
		 
		System.out.println("done");
	}
	  
}
