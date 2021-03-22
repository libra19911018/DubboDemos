package libra.config;

import java.util.ArrayList;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import libra.inter.provider.ProviderInterService;
import libra.service.BootInterServiceImpl_7012;

/**
 * Dubbo的相关配置类
 * 针对于xml文件中的<dubbo:xxx> 或者 yml和properties文件中的dubbo.xxx
 * 在配置类中就会有一个相对应的dubbo下的xxxConfig类对当前应用的dubbo进行配置
 * @author libra
 */
@Configuration
public class DubboConfig_7012 {
	/**
	 * 【应用配置】指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名） 
	 * 相当于
	 * <dubbo:application name="db-Consumer" owner="dubbo-test-consumer"></dubbo:application>
	 * name：必填，当前应用名称，用于注册中心计算应用间依赖关系，注意：消费者和提供者应用名不要一样，此参数不是匹配条件，你当前项目叫什么名字就填什么，和提供者消费者角色无关
	 * owner：选填，应用负责人，用于服务治理，请填写负责人公司邮箱前缀
	 */
	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig app = new ApplicationConfig();
		// 设置当前服务、应用的名字（同样的服务名字相同，不要和别的服务同名）
		app.setName("db-Boot-Config-Provider");
		app.setOwner("dubbo-boot-config-test-provider");
		return app;
	}
	
	/**
	 * 【注册中心配置】
	 * 相当于
	 * <!-- <dubbo:registry address="zookeeper://127.0.0.1:2181"></dubbo:registry> -->
	 * <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183"></dubbo:registry>
	 */
	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol("zookeeper");
		registry.setAddress("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		return registry;
	}
	
	/**
	 * 【远程调用配置】
	 * 相当于
	 * 	<dubbo:service interface="libra.inter.provider.ProviderInterService" ref="interServiceImpl" timeout="4000" version="1.0.0">
			<!-- <dubbo:method name="getAll" timeout="1000"></dubbo:method> -->
			<!-- <dubbo:method name="getOne" timeout="1000"></dubbo:method> -->
		</dubbo:service>
	 * interface：必填，provider中定义的功能服务接口全类名
	 * ref：必填，功能服务接口的具体实现类在容器中的beanID
	 * version：选填，用于标识服务版本
	 * timeout：选填，默认缺省值为<dubbo:provider timeout="1000"></dubbo:provider>，等待provider服务的最长时间
	 */
	@Bean
	public ServiceConfig<ProviderInterService> serviceConfig( @Autowired BootInterServiceImpl_7012 serviceImpl){
		ServiceConfig<ProviderInterService> service = new ServiceConfig<ProviderInterService>();
		service.setInterface(ProviderInterService.class);
		service.setRef(serviceImpl);
		service.setVersion("3.0.0");
		
		// 单方法级别设置
		MethodConfig method_getAll = new MethodConfig();
		method_getAll.setName("getAll");
		method_getAll.setTimeout(4000);
		
		ArrayList<MethodConfig> methods = new ArrayList<MethodConfig>() {
			/** */
			private static final long serialVersionUID = 5732295499254006475L;
			{
				add(method_getAll);
			}
		};
		service.setMethods(methods);
		
		return service;
	}
	
	
	/**
	 * 【提供者统一配置】
	 * 相当于
	 * <dubbo:provider timeout="2000"></dubbo:provider>
	 */
	@Bean
	public ProviderConfig providerConfig() {
		ProviderConfig provider = new ProviderConfig();
		provider.setTimeout(2000);
		return provider;
	}
	
	
	/**
	 * 【通信协议配置】与consumer交互
	 * 相当于
	 * <dubbo:protocol name="dubbo" port="20900"></dubbo:protocol>
	 */
	@Bean
	public ProtocolConfig protocolConfig() {
		ProtocolConfig protocol = new ProtocolConfig();
		// 定义协议名
		protocol.setName("dubbo");
		// 定义通信端口
		protocol.setPort(20902);
		return protocol;
	}
	
	
	/**
	 * 【Monitor监控中心配置】
	 * 相当于
	 * 以此种方式配置监控中心，表示从注册中心发现监控中心地址
	 * <dubbo:monitor protocol="registry"></dubbo:monitor>
	 * 以此种方式配置监控中心，通过制定address直连监控中心服务器地址，端口就是dubbo-monitor-simple下的conf/dubbo.properties的dubbo.protocol.port=7070
	 * <dubbo:monitor address="127.0.0.1:7070"></dubbo:monitor>
	 */
	@Bean
	public MonitorConfig monitorConfig() {
		MonitorConfig monitor = new MonitorConfig();
		monitor.setProtocol("registry");
		return monitor;
	}
}
