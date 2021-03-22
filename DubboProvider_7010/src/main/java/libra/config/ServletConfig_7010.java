package libra.config;

import java.util.Arrays;

import javax.servlet.ServletContextListener;

import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import libra.servletPlugins.MyFilter_7010;
import libra.servletPlugins.MyListener_7010;
import libra.servletPlugins.MyServlet_7010;

/**
 * 注册Servlet3大组件
 * @author libra
 */
@Configuration
public class ServletConfig_7010 {
	
	@Bean
	public ServletRegistrationBean<DefaultServlet> myServletRegistrationBean3() {
		DefaultServlet defaultServlet = new DefaultServlet();
		ServletRegistrationBean<DefaultServlet> myServletRegistrationBean = new ServletRegistrationBean<DefaultServlet>(defaultServlet, "*.js","*.css");
		return myServletRegistrationBean;
	}
	/*
	 * 注册Servlet 
	 */
	@Autowired
	MyServlet_7010 myServlet;
	@Bean
	public ServletRegistrationBean<MyServlet_7010> myServletRegistrationBean1() {
		 ServletRegistrationBean<MyServlet_7010> myServletRegistrationBean = new ServletRegistrationBean<>();
		 myServletRegistrationBean.setServlet( myServlet );
		 myServletRegistrationBean.setUrlMappings(Arrays.asList("/myServlet"));
		 return myServletRegistrationBean;
	}
	
	
	/*
	 * 注册Filter
	 */
	@Autowired
	MyFilter_7010 myFilter;
	@Bean
	public FilterRegistrationBean<MyFilter_7010> myFilterRegistrationBean1() {
		FilterRegistrationBean<MyFilter_7010> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(myFilter);
		// “/myServlet1”手动注册的servlet，“/test/test”容器中的controller下的方法。拦截器不作用servlet
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/myServlet","/test/test"));
		return filterRegistrationBean;
	}
	
	/*
	 * 注册Listener
	 static {
		Set<Class<?>> types = new HashSet<Class<?>>();
		types.add(ServletContextAttributeListener.class);
		types.add(ServletRequestListener.class);
		types.add(ServletRequestAttributeListener.class);
		types.add(HttpSessionAttributeListener.class);
		types.add(HttpSessionListener.class);
		types.add(ServletContextListener.class);
		SUPPORTED_TYPES = Collections.unmodifiableSet(types);
	}
	 */
	@Autowired
	MyListener_7010 myListener;
	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> myServletListenerRegistrationBean(){
		ServletListenerRegistrationBean<ServletContextListener> ServletContextListener = new ServletListenerRegistrationBean<>();
		ServletContextListener.setListener( myListener );
		return ServletContextListener;
	}
}
