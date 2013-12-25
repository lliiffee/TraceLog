package com.bbf.trace.service;

import java.util.List;
import java.util.Map;

import com.bbf.trace.model.LogBean;

public interface LogBeanService {
	
  //  public abstract void save(LogBean log);
  //  public abstract void update(LogBean log);
 //   public abstract LogBean getLogBean(Integer id);
	public List<LogBean> getAllLog(int begin) ;
  //  public abstract void delete(Integer id);
	public int[] batchUpdate(final List<LogBean> beans,final Map<String,String> map);
	public int getAllLogCnt();
	public int[] batchUpdateMid(final List<LogBean> beans);
}
