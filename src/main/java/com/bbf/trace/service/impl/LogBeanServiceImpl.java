package com.bbf.trace.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.bbf.trace.model.LogBean;
import com.bbf.trace.service.LogBeanService;

public class LogBeanServiceImpl implements LogBeanService {

	
	 private DataSource dataSource;
     /**
      * spring提供的jdbc操作辅助类
 */
     private JdbcTemplate jdbcTemplate;
 
     // 设置数据源
     public void setDataSource(DataSource dataSource) {
         this.jdbcTemplate = new JdbcTemplate(dataSource);
     }
     
	@Override
	public List<LogBean> getAllLog(int begin) {
		List<LogBean> list = jdbcTemplate.query("select id,acc_time,acc_mis,acc_url,sid,ref_url,mid,ip,query_str,0 as duration,0 as exit_code,0 as time_on_site from mms_trace_log_tmp where sid!='' order by sid ,id  limit ? ,10000 ",new Object[]{begin},new int[]{java.sql.Types.INTEGER}, new LogBeanRowMapper());
		return list;
	}
	
	
	@Override
	public int getAllLogCnt() {
		int cnt = jdbcTemplate.queryForInt("select count(id)   from mms_trace_log_tmp where sid!='' ");
		return cnt;
	}
	
/*	
	public int[] batchUpdate(final List<LogBean> beans) {
        int[] updateCounts = jdbcTemplate.batchUpdate(
                "update mms_trace_log_tmp set duration = ?,exit_code=?,time_on_site=?,mid=?  where id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, beans.get(i).getDuration());
                        ps.setInt(2, beans.get(i).getExitCode());
                        ps.setInt(3, beans.get(i).getTimeOnSite());
                        ps.setString(4, beans.get(i).getMid());
                        ps.setInt(5, beans.get(i).getId());
                     
                    }

                    public int getBatchSize() {
                        return beans.size();
                    }
                } );
        return updateCounts;
    }
*/	
	public int[] batchUpdate(final List<LogBean> beans,final Map<String,String> map) {
        
        int[] updateCounts = (int[]) this.jdbcTemplate.execute("update mms_trace_log_tmp set duration = ?,exit_code=?,time_on_site=?,mid=?  where id = ?",
        		new PreparedStatementCallback(){   
            public Object doInPreparedStatement(PreparedStatement ps)throws SQLException, DataAccessException {   
                int length = beans.size();   
                ps.getConnection().setAutoCommit(false);   
                for(int i=0;i<length;i++){   
                	 ps.setInt(1, beans.get(i).getDuration());
                     ps.setInt(2, beans.get(i).getExitCode());
                     ps.setInt(3, beans.get(i).getTimeOnSite());
                     ps.setString(4, map.get(beans.get(i).getSid()));
                     ps.setInt(5, beans.get(i).getId());
                    ps.addBatch();   
                }   
                Object o = ps.executeBatch();   
                ps.getConnection().commit();   
                ps.getConnection().setAutoCommit(true);   
                             //如果用<aop:config>  来控制事务，需要把上一行注掉，否则会报错   
                return  o;   
            }});  
        
        return updateCounts;
    }
	
	
	public int[] batchUpdateMid(final List<LogBean> beans) {

        
        
        int[] updateCounts = (int[]) this.jdbcTemplate.execute(" update mms_trace_log_tmp set mid = ?  where sid = ? ",
        		new PreparedStatementCallback(){   
            public Object doInPreparedStatement(PreparedStatement ps)throws SQLException, DataAccessException {   
                int length = beans.size();   
                ps.getConnection().setAutoCommit(false);   
                for(int i=0;i<length;i++){   
                	ps.setString(1, beans.get(i).getMid());
                    ps.setString(2, beans.get(i).getSid());
                    ps.addBatch();   
                }   
                Object o = ps.executeBatch();   
                ps.getConnection().commit();   
                ps.getConnection().setAutoCommit(true);   
                             //如果用<aop:config>  来控制事务，需要把上一行注掉，否则会报错   
                return  o;   
            }});  
        
        return updateCounts;
    }
	
	
	 

}






/*
public void save(Person person) {
jdbcTemplate.update("insert into person(name,age,sex)values(?,?,?)",
    new Object[] { person.getName(), person.getAge(),
            person.getSex() }, new int[] { java.sql.Types.VARCHAR,
            java.sql.Types.INTEGER, java.sql.Types.VARCHAR });
}

public void update(Person person) {
jdbcTemplate.update("update person set name=?,age=?,sex=? where id=?",
    new Object[] { person.getName(), person.getAge(),
            person.getSex(), person.getId() }, new int[] {
            java.sql.Types.VARCHAR, java.sql.Types.INTEGER,
            java.sql.Types.VARCHAR, java.sql.Types.INTEGER });

}

public Person getPerson(Integer id) {
Person person = (Person) jdbcTemplate.queryForObject(
    "select * from person where id=?", new Object[] { id },
    new int[] { java.sql.Types.INTEGER }, new PersonRowMapper());
return person;

}

@SuppressWarnings("unchecked")
public List<Person> getPerson() {
List<Person> list = jdbcTemplate.query("select * from person", new PersonRowMapper());
return list;

}

public void delete(Integer id) {
jdbcTemplate.update("delete from person where id = ?", new Object[] { id },
    new int[] { java.sql.Types.INTEGER });

}
*/