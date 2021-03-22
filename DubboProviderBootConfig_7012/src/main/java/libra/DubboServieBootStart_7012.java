package libra;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBoot启动入口 
 * @author libra
 */
//标注该类为SpringBoot的主配置类
@SpringBootApplication
//指定包扫描
@ComponentScan(value="libra")
/*
 * 开启基于注解的dubbo功能
 * @EnableDubbo整合了三个注解@EnableDubboConfig、@DubboComponentScan、@EnableDubboLifecycle
 * @EnableDubbo：开启注解Dubbo功能 ，其中可以加入scanBasePackages属性配置包扫描的路径，用于扫描并注册bean
 */
//@EnableDubbo(scanBasePackages = {"libra"})
//启动器启动时，扫描本目录以及子目录带有的@WebFilter、@WebListener、@WebServlet注解
@ServletComponentScan(value = "libra")
//在配置类上标注引入外部的Spring配置xml文件
//@ImportResource(value= {"classpath:provider_7010.xml"})
public class DubboServieBootStart_7012 {
	public static void main(String[] args) {
		try {
			System.out.println( "Dubbo.Springboot.Start" );
			ConfigurableApplicationContext cac = org.springframework.boot.SpringApplication.run(DubboServieBootStart_7012.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
