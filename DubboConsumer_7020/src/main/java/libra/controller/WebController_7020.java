package libra.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import libra.inter.provider.ProviderInterService;
import libra.model.Table_data;

@Controller
@RequestMapping("web")
public class WebController_7020 {
	
	// 通过Spring容器加载外部定义的consumer_7020.xml中的配置文件中定义的<dubbo:reference>中的beanID自动注入
	@Autowired
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
