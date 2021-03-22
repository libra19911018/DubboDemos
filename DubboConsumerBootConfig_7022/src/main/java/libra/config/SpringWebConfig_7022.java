package libra.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，作用等同于spring的配置文件
 * @author libra
 */
@Configuration
public class SpringWebConfig_7022  implements WebMvcConfigurer {
	
	// 添加请求映射
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 如果是以java -jar x.war方式运行，需要手动添加首页访问映射
		registry.addViewController("/").setViewName("../../index");
		WebMvcConfigurer.super.addViewControllers(registry);
	}

	@Bean
	public SimpleDateFormat sdf() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
//	@Autowired
//	private SelfInterceptor selfInterceptor;
	
	
	/**
	 * SpringBoot2中，因为升级了Spring5版本
	 * 导致自定义拦截器会拦截我们的静态资源请求，我们需要手动把他们排除出拦截器拦截路径
	 * 1.1、如果有多个拦截器，现采取在web.xml中配置defaultServlet的方式配置静态资源访问
	 * 1.2、注册defaultServlet，处理所有静态资源
	 * 1.1 or 1.2 两种二选一
	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor( selfInterceptor ).addPathPatterns("/**")
////			.excludePathPatterns("/**/*.js","/**/*.css")
//			;
//	}	
}
