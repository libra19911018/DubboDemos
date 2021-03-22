package libra.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import libra.dao.Dao;
import libra.inter.provider.ProviderInterService;
import libra.model.Table_data;

@Service
/*
 * 
 */
@DubboService(version = "2.0.0")
public class BootInterServiceImpl_7011 implements ProviderInterService{
	@Autowired
	Dao dao;
	@Autowired
	SimpleDateFormat sdf;
	
	String srcFrom = "DubboProviderBoot_7011";
	
	public List<?> getAll(){
		List<Table_data> result = dao.find(Table_data.class, "select * from table_data", new Object[] {});
		try {
			System.out.println( "BootInterServiceImpl_7011.getAll()->测试@DubboReference(retries=xx)" );
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.stream().forEach( (data) -> { data.setSrcFrom_(srcFrom); } );
		return result;
	}
	
	public Table_data getOne( Integer id ){
		Table_data data = dao.findByPK(Table_data.class, id);
		if( data != null ) {
			data.setSrcFrom_(srcFrom);
		}
		return data;
	}
	
	public Map<String, List<Table_data>> delOne( Integer id ) {
		HashMap<String, List<Table_data>> data = new HashMap<String, List<Table_data>>();
		
		List<Table_data> before = dao.find(Table_data.class, "select * from table_data", new Object[] {});
		before.stream().forEach( (before_data) -> { before_data.setSrcFrom_(srcFrom); } );
		dao.deleteByPK(Table_data.class, id);
		List<Table_data> after = dao.find(Table_data.class, "select * from table_data", new Object[] {});
		after.stream().forEach( (after_data) -> { after_data.setSrcFrom_(srcFrom); } );
		
		data.put("before", before);
		data.put("after", after);
		return data;
	}
	
	public Table_data insert( @RequestBody Table_data data ) throws Exception {
		data.setCreateTime(sdf.format(new Date()));
		data.setSrcFrom_(srcFrom);
		dao.save(data);
		return data;
	}
	
	public Map<String, Table_data> update( @RequestBody Table_data data ) throws Exception {
		HashMap<String, Table_data> result = new HashMap<String, Table_data>();
		
		Table_data before = dao.findByPK(Table_data.class, data.getId());
		if( before != null ) {
			before.setSrcFrom_(srcFrom);
		}
		result.put("before", before);
		dao.update("update table_data set info = ? where id = ?", data.getInfo(), data.getId());
		Table_data after = dao.findByPK(Table_data.class, data.getId());
		if( after != null ) {
			after.setSrcFrom_(srcFrom);
		}
		result.put("after", after);
		
		return result;
	}
}
