package common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.ybs.web.dataserver.server.sys.MenuService;

/**
 *项目启动的时候自动执行
 *把当前目录的菜单文件夹的菜单插入到数据库 
 * @author zlm
 *
 */

public class ImportMenu implements ServletContextListener {
	
	@Autowired
	private MenuService menuService;
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		menuService.importMenu();
	}

}
