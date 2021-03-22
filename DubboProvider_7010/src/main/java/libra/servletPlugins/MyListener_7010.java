package libra.servletPlugins;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

@Component
public class MyListener_7010 implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
    	String realpath = sc.getRealPath("/");
    	System.out.println( "项目启动:"+realpath );
		System.out.println("DubboProvider_7010.MyListener...init");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("DubboProvider_7010.MyListener...destory");
	}
}