package libra;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 外置Tomcat启动入口
 * @author libra
 */
public class ServletInitializer_7010 extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("Dubbo.ServletInitializer.Start");
		return application.sources(DubboServieStart_7010.class);
	}
}