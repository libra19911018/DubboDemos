package libra.consts;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

// 获取YML配置文件中的指定前缀信息，映射到实体类
@ConfigurationProperties(prefix="project")
// Spring注解，将对象实例化注册进容器
@Component
// lombok注解，为对象生成getter、seteer方法等
@Data
public class ProjectEnv_7010 {
	public Boolean isMysql8;
	public String name;
	public String mysql;
	public String mysql8;
	
	// Spring注解，在对象实例化之后，立即调用
	@PostConstruct
	public void afterInit() {
		System.out.println( this );
	}
}
