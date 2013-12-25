package com.bbf.spring.taskExcutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbf.trace.model.LogBean;
import com.bbf.trace.service.LogBeanService;
@Component 
@Scope("prototype") 
public class CalSiteLogTask implements Runnable {

	 @Autowired
	 private LogBeanService logBeanService;
	 
	 String name; 
	 int begin;
	 
	 
     public void setBegin(int begin) {
		this.begin = begin;
	}

	public void setName(String name) { 
             this.name = name; 
     } 

	public void run() {
		System.out.println(name+ "begin");
		int n=0;
		LogBean tmp=null;
		String mid_tmp="";
		List<LogBean> list=logBeanService.getAllLog(begin); 
		int timeOnSite=0;
		int listSize=list.size();
		List<LogBean> beans=new ArrayList<LogBean>();
		Map<String,String> map =new HashMap<String,String>();
		 for (LogBean lg:list)
         {
      	   //System.out.println(lg.getAccUrl());
			 
			 n++;
			 
			 
			 if(tmp!=null)
			 { 
				
				 //new id = pre rec id
				if(lg.getSid().equals(tmp.getSid()))
				 {
						long dur= (lg.getAccTime().getTime()-tmp.getAccTime().getTime())+(lg.getAccMis()-tmp.getAccMis());
						
						timeOnSite=timeOnSite+(int)dur;
						
						tmp.setDuration((int)dur);
						tmp.setTimeOnSite(timeOnSite);
						
						if(lg.getMid()!=null&&!"".equals(lg.getMid()))
						 {
							 tmp.setMid(lg.getMid());
							 if(map.get(lg.getSid())==null)
							 {
								 beans.add(lg);
								 map.put(lg.getSid(), lg.getMid());
							 }
							
						 }else  if(tmp.getMid()!=null&&!"".equals(tmp.getMid()))
						 {
							 lg.setMid(tmp.getMid());
							 
						 }
						//the last one
						if(n==listSize)
						 {
							 lg.setExitCode(-2);
							 lg.setTimeOnSite(timeOnSite);
						 }
						 
				 }else //new id is new != pre rec id
				 {
					 tmp.setTimeOnSite(timeOnSite);
					 // -2 means exit url  -3 means both in and out
					 tmp.setExitCode(-2+tmp.getExitCode());
					 // -1 means enter url
					 if(n==listSize)
					 {
						 lg.setExitCode(-3);
						 lg.setTimeOnSite(timeOnSite);
					 }else
					 {
						 lg.setExitCode(-1);
					 }
					 
					 timeOnSite=0;
				 }

			 }else
			 {
				//最后一个 
				 if(n==listSize)
				 {
					 lg.setExitCode(-3);
					 lg.setTimeOnSite(timeOnSite);
				 }else
				 {
					 lg.setExitCode(-1);
				 }
				 timeOnSite=0;
			 }
			 
			
			 tmp=lg;
         }
		// this.logBeanService.batchUpdateMid(beans);
		 this.logBeanService.batchUpdate(list,map);
		 
		 System.out.println(name+ "end"+n);
	}

}
