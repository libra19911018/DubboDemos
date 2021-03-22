package libra.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import libra.inter.provider.ProviderInterService;

/**
 * Dubbo相关配置类
 * @author libra
 */
@Configuration
public class DubboConfig_7022 {
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
		//name：必填，当前应用名称，用于注册中心计算应用间依赖关系，注意：消费者和提供者应用名不要一样，此参数不是匹配条件，你当前项目叫什么名字就填什么，和提供者消费者角色无关
		app.setName("db-Boot-Config-Consumer");
		//owner：选填，应用负责人，用于服务治理，请填写负责人公司邮箱前缀
		app.setOwner("dubbo-boot-config-test-consumer");
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
	 * 	<dubbo:reference version="1.0.0" timeout="600" retries="5" id="interService" 
			interface="libra.inter.provider.ProviderInterService" stub="libra.stub.ProviderInterServiceStub_7020" >
			<!-- <dubbo:method name="getAll" timeout="1000"></dubbo:method> -->
		</dubbo:reference>
	 * interface：必填，provider中定义的功能服务接口全类名
	 * id：必填，为provider中定义的功能接口，在当前consumer的IOC容器类定义全局唯一ID
	 * version：选填，用于标识服务版本，需要与provider的版本一致，consumer端填写“*”表示随机获取一个版本的接口调用
	 * timeout：选填，默认缺省值为<dubbo:consumer timeout="1000"></dubbo:consumer>，等待provider服务的最长时间
	 * retries：选填，默认缺省值为<dubbo:consumer retries="2"></dubbo:consumer>
			            配合timeout一起使用，重试次数不包含第一次，不需要重试请设为0
     * stub：服务接口客户端本地代理类名，用于在客户端执行本地逻辑，如本地缓存等，该本地代理类的构造函数必须允许传入远程代理对象【本地存根】
	 */
	@Bean
	public ReferenceConfig<ProviderInterService> referenceConfig(){
		ReferenceConfig<ProviderInterService> reference = new ReferenceConfig<ProviderInterService>();
		reference.setVersion("3.0.0");
		reference.setId("interService");
		reference.setInterface(ProviderInterService.class);
		reference.setStub("libra.stub.ProviderInterServiceStub_7022");
		return reference;
	}
	/**
	 * 【远程调用代理对象配置】
	 * 从相对应 ReferenceConfig中通过ReferenceConfig.get()方法获取Dubbo的代理对象，并存入IOC容器中
	 */
	@Bean
	public ProviderInterService interService() {
		 ReferenceConfig<ProviderInterService> reference = referenceConfig();
		 return reference.get();
	}
	
	
	/**
	 * 【消费者统一配置】
	 * 相当于
	 * <dubbo:consumer check="false"></dubbo:consumer>
	 */
	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumer = new ConsumerConfig();
		// 所有的服务启动均不做Provider检查
		consumer.setCheck(false);
		return consumer;
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
