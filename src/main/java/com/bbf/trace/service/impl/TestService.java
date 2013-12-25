package com.bbf.trace.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bbf.trace.model.LogBean;
import com.bbf.trace.service.LogBeanService;

public class TestService {
	
	 public static void main(String[] args) {
		           ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
		  
		           LogBeanService personService = (LogBeanService) act
		                  .getBean("logBeanService");
		           
		           for (LogBean lg:personService.getAllLog(0))
		           {
		        	   System.out.println(lg.getAccUrl());
		           }
	 }

}
