package libra;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * SpringBoot启动入口 
 * @author libra
 */
//标注该类为SpringBoot的主配置类
@SpringBootApplication
//指定包扫描
@ComponentScan(value="libra")
//在配置类上标注引入外部的Spring配置xml文件
@ImportResource(value= {"classpath:provider_7010.xml"})
public class DubboServieStart_7010 {
	public static void main(String[] args) {
		try {
			System.out.println( "Dubbo.Springboot.Start" );
			ConfigurableApplicationContext cac = org.springframework.boot.SpringApplication.run(DubboServieStart_7010.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
