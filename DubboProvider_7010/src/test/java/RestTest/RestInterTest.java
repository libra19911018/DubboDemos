package RestTest;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import libra.model.Table_data;

public class RestInterTest {
	
	RestTemplate restTemplate = null;
	String ip_port = "http://127.0.0.1:7010/dubboProvider/";
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAll() {
		try {
			List<Table_data> result = restTemplate.postForObject(ip_port+"inter/getAll", null, List.class);
			System.out.println( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getOne() {
		try {
			int id = 1;
			Table_data data = restTemplate.postForObject(ip_port+"inter/getOne?id="+id, null, Table_data.class);
			System.out.println( data );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void delOne() {
		try {
			int id = 2;
			Map<String, List<Table_data>> data = restTemplate.postForObject(ip_port+"inter/delOne?id="+id, null, Map.class);
			System.out.println( data );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertOne() {
		try {
			Table_data data = Table_data.builder().info("rest测试新增1").build();
			Table_data result = restTemplate.postForObject(ip_port+"inter/insert", data, Table_data.class);
			System.out.println( data );
			System.out.println( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateOne() {
		try {
			Table_data data = Table_data.builder().id(4)
					.info("rest测试修改_"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).build();
			Map<String, List<Table_data>> result = restTemplate.postForObject(ip_port+"inter/update", data, Map.class);
			System.out.println( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Before
	@Test
	public void restTemplate() {
		restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		list.add(new ByteArrayHttpMessageConverter());
		list.add(new AllEncompassingFormHttpMessageConverter());
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
	}
}