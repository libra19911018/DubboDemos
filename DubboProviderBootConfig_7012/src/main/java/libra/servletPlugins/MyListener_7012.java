package libra.servletPlugins;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener_7012 implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
    	String realpath = sc.getRealPath("/");
    	System.out.println( "项目启动:"+realpath );
		System.out.println("DubboProviderBoot_7011.MyListener...init");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("DubboProviderBoot_7011.MyListener...destory");
	}
}
