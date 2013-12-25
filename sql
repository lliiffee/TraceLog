sed:
  cat /usr/local/tomcat_shop/logs/MMS.log |  sed --expression='s/] \[ERROR] /,/g' --expression='s/\[//g'  --expression='s/,/:/'
 cat /usr/local/tomcat_shop/logs/MMS.log |  sed --expression='s/] \[ERROR] /,/g' --expression='s/\[//g'
  

./trace_log.sh MMS.log tst.log

cat /usr/local/tomcat_shop/logs/MMS.log |  sed --expression="s/] \[ERROR] /''',/g" --expression="s/\[/'''/g"  --expression="s/,/''','''/"

LOAD DATA  INFILE "/usr/local/tomcat_shop//a.log" INTO TABLE mms_trace_log_tmp FIELDS TERMINATED BY "," ENCLOSED BY "'''";

mysqlimport -u root -p -d e_member  --fields-terminated-by=, --fields-enclosed-by="  mms_trace_log_tmp a.txt

insert into mms_edw_t07_easytrace (ip,visittime,url,referrer,loginname,sessionid,activetime,timeonsite,IsEntrance,IsBounce,IsBounce10)
 ( select ip,concat(acc_time,':',acc_mis),acc_url,ref_url,mid,sid ,duration,time_on_site, case when exit_code in (-1,-3 )then 0   else -1 end as IsEntrance
 ,case when exit_code in (-2,-3 )then 0   else -1 end as IsBounce
 ,case when (exit_code in (-2,-3) and time_on_site < 10000)then 0   else -1 end as IsBounce from mms_trace_log_tmp where sid !='' )
 
 
CREATE TABLE `mms_trace_log_tmp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_time` datetime DEFAULT NULL,
  `acc_mis` varchar(3) DEFAULT NULL,
  `acc_url` varchar(255) DEFAULT NULL,
  `sid` varchar(80) DEFAULT NULL,
   `mid` varchar(30) DEFAULT NULL,
     `ip` varchar(20) DEFAULT NULL,
  `ref_url` varchar(255) DEFAULT NULL,
  `query_str` varchar(255) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `exit_code` int(11) DEFAULT NULL,
  `time_on_site` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


mysqlimport -u root -pdcbicc106 -d e_member  -h 192.168.0.32 --fields-terminated-by=',' --fields-enclosed-by='"'  mms_trace_log_tmp  ${base_path}/${des_file_name}

LOAD DATA  INFILE "/home/mobile_shop/cal_log/tomcat_mms_log/103/ana.log" INTO TABLE mms_trace_log_tmp (acc_time, acc_mis, acc_url,sid,ref_url,mid,ip,query_str) FIELDS TERMINATED BY "," ENCLOSED BY "\"";

LOAD DATA  INFILE "/home/mobile_shop/cal_log/tomcat_mms_log/103/ana.log" INTO TABLE mms_trace_log_tmp FIELDS TERMINATED BY "," (acc_time, acc_mis, acc_url,sid,ref_url,mid,ip,query_str);
 LOAD DATA  INFILE '/home/mobile_shop/cal_log/tomcat_mms_log/103/ana.log' INTO TABLE mms_trace_log_tmp FIELDS TERMINATED BY ',' 
