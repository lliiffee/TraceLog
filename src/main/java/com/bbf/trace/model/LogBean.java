package com.bbf.trace.model;

import java.util.Date;

public class LogBean {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	private Date accTime      ;
	private int accMis        ;
	private String accUrl     ;
	private String sid        ;
	private String refUrl     ;
	private String mid        ;
	private String ip         ;
	private String queryStr   ;
	private int duration;
	private int exitCode;
	private int timeOnSite;
	
	public int getTimeOnSite() {
		return timeOnSite;
	}
	public void setTimeOnSite(int timeOnSite) {
		this.timeOnSite = timeOnSite;
	}
	public int getExitCode() {
		return exitCode;
	}
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getAccTime() {
		return accTime;
	}
	public void setAccTime(Date accTime) {
		this.accTime = accTime;
	}
	public int getAccMis() {
		return accMis;
	}
	public void setAccMis(int accMis) {
		this.accMis = accMis;
	}
	public String getAccUrl() {
		return accUrl;
	}
	public void setAccUrl(String accUrl) {
		this.accUrl = accUrl;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getRefUrl() {
		return refUrl;
	}
	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	public LogBean()
	{
		
	}
	public LogBean( 
			int id,
			Date accTime      ,
			 int accMis        ,
			 String accUrl     ,
			 String sid        ,
			 String refUrl     ,
			 String mid        ,
			 String ip         ,
			 String queryStr,
			 int duration ,
			 int exitCode,
			 int timeOnSite
			)
	{
		this.accTime=accTime;
		this.accMis=accMis;
		this.accUrl=accUrl;
		this.sid=sid;
		this.refUrl=refUrl;
		this.mid=mid;
		this.ip=ip;
		this.queryStr=queryStr;
		this.id=id;
		this.duration=duration;
		this.exitCode= exitCode;
		this.timeOnSite=timeOnSite;
	}
	
}
