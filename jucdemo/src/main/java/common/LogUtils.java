/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package common;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybs.web.constant.StringConst;
import com.ybs.web.dataserver.entity.sys.Log;
import com.ybs.web.dataserver.entity.user.User;
import com.ybs.web.dataserver.server.sys.LogService;
import com.ybs.web.dataserver.server.user.UserService;


/**
 * 字典工具类
 * @author ThinkGem
 * @version 2014-11-7
 */

@Service
public class LogUtils {
	
	@Autowired
	private LogService  logService;
	
	@Autowired
	private UserService userService;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static String check="check.do";
	public static String fileName="web_manager";
	/**
	 * 保存日志
	 */
	/*public  void saveLog(HttpServletRequest request, String title){
		saveLog(request, null, null, title);
	}*/
	
	




	/**
	 * 保存日志
	 */
	public   void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		//User user = SubjectUtils.getSubject();
		//if (user != null && user.getId() != null){
			Log log = new Log();
			String projectName = request.getContextPath();
			String uri=request.getRequestURI().substring(projectName.length());
			if(!uri.endsWith(check)&&!uri.contains("static")){
				log.setRemoteAddr(StringUtils.getRemoteAddr(request));
				log.setUserAgent(request.getHeader("user-agent"));
				//log.setParams(request.getParameterMap());
				//logger.info("params "+request.getParameterMap());
				log.setMethod(request.getMethod());
				log.setSysName(fileName);
				log.setRequestUri(request.getRequestURI().substring(projectName.length()));
				log.setRetStatus((ex == null) ? StringConst.LOG_SUCCESS:StringConst.LOG_EXCEPTION );
				//System.out.println(log.toString());
				logger.info((ex == null) ? StringConst.LOG_SUCCESS:StringConst.LOG_EXCEPTION );
				logService.insert(log);
			}
			
			//new SaveLogThread(log, handler, ex).start();
		//}
	}

	/**
	 * 保存日志
	 */
	public   void saveLog(HttpServletRequest request,String status,String message,String userID,String loginName){
		//User user = SubjectUtils.getSubject();
		//if (user != null && user.getId() != null){
			Log log = new Log();
			String projectName = request.getContextPath();
			String uri=request.getRequestURI().substring(projectName.length());
			if(!uri.endsWith(check)&&!uri.contains("static")){
				log.setRemoteAddr(StringUtils.getRemoteAddr(request));
				log.setUserAgent(request.getHeader("user-agent"));
				//log.setParams(request.getParameterMap());
				log.setMethod(request.getMethod());
				log.setSysName(projectName);
				log.setRequestUri(request.getRequestURI().substring(projectName.length()));
				log.setRetStatus(status);
				if(null!=message&&!"".equals(message)){
					log.setRetmsg(message);
				}
				if(null!=userID){
					log.setUserID(userID);
				}else{
					User user=userService.findByLoginName(loginName);
					if(user!=null){
						log.setUserID(user.getId());
					}
					
				}
				log.setWinName(StringConst.LOGIN_NAME);
				//System.out.println(log.toString());
				logService.save(log);
			}
			
			//new SaveLogThread(log, handler, ex).start();
		//}
	}
	
	/**
	 * 保存日志线程
	 */
/*	public  static class SaveLogThread extends Thread{
		
		private Log log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Log log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public  void run() {
		
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
				return;
			}
			// 保存日志信息
			
			logService.insert(log);
		}
	}*/



	
}
