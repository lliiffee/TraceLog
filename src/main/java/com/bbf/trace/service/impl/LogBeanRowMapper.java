package com.bbf.trace.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.bbf.trace.model.LogBean;

public class LogBeanRowMapper  implements RowMapper{

	@Override
	public Object mapRow(ResultSet set, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
		LogBean log=new LogBean(
				set.getInt("id"),
				new Date(	
				set.getTimestamp("acc_time").getTime())
				,set.getInt("acc_mis")
				, set.getString("acc_url"   ),
				 set.getString("sid"      )  ,
				 set.getString("ref_url"   )  ,
				 set.getString("mid"      )  ,
				 set.getString("ip"       )  ,
				 set.getString("query_str" ),
				 set.getInt("duration"),
				 set.getInt("exit_code"),
				 set.getInt("time_on_site")
				 );
		return log;
		/*
		 * public Object mapRow(ResultSet set, int index) throws SQLException {
          Person person = new Person(set.getInt("id"), set.getString("name"), set
                  .getInt("age"), set.getString("sex"));
         return person;
      }
		 */
	}

}
