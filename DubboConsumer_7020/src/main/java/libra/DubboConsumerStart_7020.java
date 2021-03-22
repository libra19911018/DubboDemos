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
@ImportResource(value= {"classpath:consumer_7020.xml"})
public class DubboConsumerStart_7020 {
	public static void main(String[] args) {
		try {
			System.out.println( "Dubbo.Springboot.Start" );
			ConfigurableApplicationContext cac = org.springframework.boot.SpringApplication.run(DubboConsumerStart_7020.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
