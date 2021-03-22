package libra.inter.provider;

import java.util.List;
import java.util.Map;

import libra.model.Table_data;

public interface ProviderInterService {
	
	public List<?> getAll();
	
	public Table_data getOne( Integer id );
	
	public Map<String, List<Table_data>> delOne( Integer id );
	
	public Table_data insert( Table_data data ) throws Exception;
	
	public Map<String, Table_data> update( Table_data data ) throws Exception;
}
