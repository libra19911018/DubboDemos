package libra.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import libra.model.Table_data;
import libra.service.BootInterServiceImpl_7012;

@RestController
@RequestMapping("bootConfig/inter")
public class BootInterConterller_7012 {
	@Autowired
	BootInterServiceImpl_7012 service;
	
	@RequestMapping("getAll")
	public List<?> getAll(){
		return service.getAll();
	}
	
	@RequestMapping("getOne")
	public Table_data getOne( Integer id ){
		return service.getOne(id);
	}
	
	@RequestMapping("delOne")
	public Map<String, List<Table_data>> delOne( Integer id ) {
		return service.delOne(id);
	}
	
	@RequestMapping("insert")
	public Table_data insert( @RequestBody Table_data data ) throws Exception {
		return service.insert(data);
	}
	
	@RequestMapping("update")
	public Map<String, Table_data> update( @RequestBody Table_data data ) throws Exception {
		return service.update(data);
	}
}
