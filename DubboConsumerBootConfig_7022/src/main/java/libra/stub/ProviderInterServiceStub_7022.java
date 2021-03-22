package libra.stub;

import java.util.List;
import java.util.Map;

import libra.inter.provider.ProviderInterService;
import libra.model.Table_data;

/**
 * 远程服务后，客户端通常只剩下接口，而实现全在服务器端，
 * 但提供方有些时候想在客户端也执行部分逻辑，比如：做 ThreadLocal 缓存，提前验证参数，调用失败后伪造容错数据等等，
 * 此时就需要在API中带上Stub，客户端生成 Proxy 实例，会把 Proxy 通过构造函数传给 Stub，然后把 Stub 暴露给用户，Stub可以决定要不要去调 Proxy。
 * 
 * 本地存根实现
 * 1、实现远程调用接口
 * 2、私有化接口成员，编写一个有参构造函数
 * @author libra
 */
public class ProviderInterServiceStub_7022 implements ProviderInterService{
	private final ProviderInterService service;
	/**
	 * 传入的是接口定义的远程代理对象
	 * @param service
	 */
	public ProviderInterServiceStub_7022(ProviderInterService service) {
		super();
		this.service = service;
	}

	@Override
	public List<?> getAll() {
		System.out.println( "ProviderInterServiceStub_7022【本地存根】.getAll()" );
		return service.getAll();
	}

	@Override
	public Table_data getOne(Integer id) {
		System.out.println( "ProviderInterServiceStub_7022【本地存根】.getOne()" );
		return service.getOne(id);
	}

	@Override
	public Map<String, List<Table_data>> delOne(Integer id) {
		System.out.println( "ProviderInterServiceStub_7022【本地存根】.delOne()" );
		return service.delOne(id);
	}

	@Override
	public Table_data insert(Table_data data) throws Exception {
		System.out.println( "ProviderInterServiceStub_7022【本地存根】.insert()" );
		return service.insert(data);
	}

	@Override
	public Map<String, Table_data> update(Table_data data) throws Exception {
		System.out.println( "ProviderInterServiceStub_7022【本地存根】.update()" );
		return service.update(data);
	}

}

