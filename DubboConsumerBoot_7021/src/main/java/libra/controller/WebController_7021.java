package libra.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import libra.inter.provider.ProviderInterService;
import libra.model.Table_data;

@Controller
@RequestMapping("boot/web")
public class WebController_7021 {
	/*
	 *  如果有多个相同接口的服务实现，通过服务版本version来匹配相对应的服务
	 *  timeout：选填，等待provider服务的最长时间
	 *  retries：选填，配合timeout一起使用，重试次数不包含第一次，不需要重试请设为0
	 ******************************************************************
	 *【服务启动时检查】在启动时检查依赖的服务是否可用
	 * Dubbo会在启动时检查依赖服务是否可用，不可用会抛出异常，阻止Spring初始化完成，以便上线时能及早发现问题，默认check="true"。
	 * 可以通过 check="false" 关闭检查，比如，测试时，有些服务不关心，或者出现了循环依赖，必须有一方先启动。
	 */
	@DubboReference(version="2.0.0", check=false, retries=3, timeout=2000, stub = "libra.stub.ProviderInterServiceStub_7021" )
	ProviderInterService interService;
	
	@RequestMapping("getAll")
	public String getAll( HttpServletRequest req ) {
		List<?> list = interService.getAll();
		req.setAttribute("all", list);
		return "all";
	}
	
	@RequestMapping("getOne")
	public String getOne( Integer id, HttpServletRequest req ) {
		Table_data data = interService.getOne(id);
		req.setAttribute("data", data);
		return "data";
	}
	
	@RequestMapping("delOne")
	public String delOne( Integer id, HttpServletRequest req ) {
		Map<String, List<Table_data>> data = interService.delOne(id);
		req.setAttribute("data", data);
		return "delete";
	}
	
	@RequestMapping("insert")
	public String insert( String info, HttpServletRequest req ) throws Exception {
		req.setAttribute("reqBefore", interService.getAll() );
		Table_data reqData = interService.insert(Table_data.builder().info(info).build());
		req.setAttribute("reqData", reqData);
		req.setAttribute("reqAfter", interService.getAll() );
		return "insert";
	}
	
	@RequestMapping("update")
	public String update( Integer id, String info, HttpServletRequest req ) throws Exception {
		Map<String, Table_data> data = interService.update( Table_data.builder().id(id).info(info).build() );
		req.setAttribute("data", data );
		return "update";
	}
}
