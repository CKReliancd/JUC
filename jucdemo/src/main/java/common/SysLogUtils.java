package common;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**项目名称：ybs-web 
 * @version 版本标识
 * @author zlm
 * @since JDK 1.7.0_80
 * 创建时间: @date 2018年2月6日 下午2:30:55
 * 文件名称:SysLogUtils.java
 * 类说明:
 */
public class SysLogUtils {
	   private static String address;   
	    private static String port; 
	    private final static Logger logger = LoggerFactory.getLogger(SysLogUtils.class);
	 static {   
		     InputStream is = SysLogUtils.class.getClassLoader().getResourceAsStream("datasource.properties");
			Properties prop = new Properties();  
	        try {   
	            prop.load(is);   
	            address = prop.getProperty("sys_log_address").trim();   
	            port = prop.getProperty("sys_log_port").trim();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    } 
	 
	 
	public static void sendSysLog(String msg,String loginName,HttpServletRequest request){
		 try
		    {
		      SyslogIF syslog = Syslog.getInstance("udp");
		      syslog.getConfig().setHost(address);
		      syslog.getConfig().setPort(Integer.parseInt(port));
		      logger.info("log server: "+address+":"+port);
		      StringBuilder sb = new StringBuilder();
		     //  builder.append("Jan 31 18:08:19 DEVELOPMENT13 sshd[1616]: Accepted password for anlianjie from 168.33.211.239 port 5928 ssh2;");
		      sb.append(getTime()+" ");
		      sb.append(getHostName()+" ");
		      sb.append("sshd[1616]: ");
		      sb.append(msg);
		      sb.append(" for "+loginName+" from ");
		      sb.append(StringUtils.getRemoteAddr(request)+" port ");
		      sb.append(request.getLocalPort()+" ");
		      sb.append("ssh2;");
		      logger.info("发送内容: "+sb.toString());
		      logger.info("开始发送-----------");
		      syslog.log(0, URLDecoder.decode(sb.toString(), "gbk"));
		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		    }
	}

  public static String getHostName(){
	  InetAddress netAddress=null;
	  String name =null;
	  try {
		 netAddress = InetAddress.getLocalHost();
		 if(null != netAddress){
			// String ip = netAddress.getHostAddress();
			  name = netAddress.getHostName(); 
			  logger.info("name  "+name);;  
	        } 
		 return name;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
  }	
  	

  public static String getTime(){
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d HH:mm:ss ", Locale.ENGLISH);
	 Date date = new Date();
	String str=dateFormat.format(date);
	return str;
 } 	
	
	public static void  main(String[]args){
	   System.out.println(getHostName());
	}
	
}
