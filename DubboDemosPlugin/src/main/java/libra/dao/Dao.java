package libra.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * 数据库访问基类
 * @author Libra
 */
@Component
public class Dao {

	/** log4j */
	private final Logger log = LoggerFactory.getLogger(Dao.class);

	private final String DEFAULT_PRIMARY_KEY_NAME = "id"; // 默认主键列名
	@Autowired
	private JdbcTemplate jt;
	private String primaryKey = DEFAULT_PRIMARY_KEY_NAME; // 数据库表主键名

	/**
	 * 返回主键名，目前默认主键列名为"id"
	 * 
	 * @return
	 */
	public String getPrimaryKey() {
		return DEFAULT_PRIMARY_KEY_NAME;
	}

	// ---- 与模型关联方法 S ----
	/**
	 * 分页查询并返回按模型封装的分页结果
	 * 
	 * @param modelClass
	 *            与数据库表对应的模型class
	 * @param pageNumber
	 *            从1开始的页码
	 * @param pageSize
	 *            每页行数
	 * @param select
	 *            Select语句，例如"SELECT *"
	 * @param sqlExceptSelect
	 *            除Select之外的其他语句，例如"FROM user WHERE name=?"
	 * @param paras
	 *            预编译语句的参数，无查询参数时设置为null或空数组
	 * @return
	 */
	public <M> Page<M> paginate(Class<M> modelClass, int pageNumber,
			int pageSize, String select, String sqlExceptSelect, Object[] paras) {
		if ((pageNumber < 1) || (pageSize < 1)) {
			throw new RuntimeException(
					"pageNumber and pageSize must be more than 0");
		}
		if (paras == null)
			paras = new Object[0];
		long totalRow = 0L;
		int totalPage = 0;
		showSql("SELECT IFNULL(COUNT(*),0) " + sqlExceptSelect);
		totalRow = this.queryNumber(
				"SELECT IFNULL(COUNT(*),0) " + sqlExceptSelect, paras)
				.longValue();
		if (totalRow < 1) {
			return new Page<M>(new ArrayList<M>(), pageNumber, pageSize, 0, 0);
		}
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0L) {
			totalPage++;
		}
		StringBuilder sql = new StringBuilder();
		forPaginate(sql, pageNumber, pageSize, select, sqlExceptSelect);
		List<M> list = find(modelClass, sql.toString(), paras);
		return new Page<M>(list, pageNumber, pageSize, totalPage,
				(int) totalRow);
	}

	/**
	 * 分页查询并返回按模型封装的分页结果，支持指定分页时使用的SQL语句（不包含"SELECT COUNT()"），
	 * 为了提高Count查询效率时应采用此方法。
	 * 
	 * @param modelClass
	 *            与数据库表对应的模型class
	 * @param pageNumber
	 *            从1开始的页码
	 * @param pageSize
	 *            每页行数
	 * @param select
	 *            select语句，例如"SELECT *"
	 * @param sqlExceptSelect
	 *            除Select之外的其他语句，例如"FROM user WHERE name=? ORDER BY name"
	 * @param countSqlExceptSelect
	 *            除Select之外的Count语句，相较于sqlExceptSelect参数可以去掉影响性能的Order子句等等，例如
	 *            "FROM user WHERE name=?"
	 * @param paras
	 *            预编译语句的参数，无查询参数时设置为null或空数组
	 * @return
	 */
	public <M> Page<M> paginate(Class<M> modelClass, int pageNumber,
			int pageSize, String select, String sqlExceptSelect,
			String countSqlExceptSelect, Object[] paras) {
		if ((pageNumber < 1) || (pageSize < 1)) {
			throw new RuntimeException(
					"pageNumber and pageSize must be more than 0");
		}
		if (paras == null)
			paras = new Object[0];
		long totalRow = 0L;
		int totalPage = 0;
		totalRow = this.queryNumber(
				"SELECT IFNULL(COUNT(*),0) " + countSqlExceptSelect, paras)
				.longValue();
		if (totalRow < 1) {
			return new Page<M>(new ArrayList<M>(), pageNumber, pageSize, 0, 0);
		}
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0L) {
			totalPage++;
		}
		StringBuilder sql = new StringBuilder();
		showSql(sql.toString());
		forPaginate(sql, pageNumber, pageSize, select, sqlExceptSelect);
		showSql(sql.toString());
		List<M> list = find(modelClass, sql.toString(), paras);
		return new Page<M>(list, pageNumber, pageSize, totalPage,
				(int) totalRow);
	}

	/**
	 * 根据SQL查询并返回按模型封装的列表。注意：SQL语句结果应与模型字段对应
	 * 
	 * @param modelClass
	 *            与数据库表对应的模型class
	 * @param sql
	 *            查询的SQL语句，例如"SELECT * FROM user WHERE name=?"
	 * @param paras
	 *            预编译语句的参数，多个时可直接往参数列表增加，也可以传数组
	 * @return
	 */
	public <M> List<M> find(Class<M> modelClass, String sql, Object... paras) {
		showSql(sql);
		return jt.query(sql, paras, new BeanPropertyRowMapper<M>(modelClass));
	}

	/**
	 * 根据SQL查询并返回按模型封装的一条记录。注意：SQL语句结果应与模型字段对应
	 * 
	 * @param modelClass
	 *            与数据库表对应的模型class
	 * @param sql
	 *            查询的SQL语句，例如"SELECT * FROM user WHERE name=?"
	 * @param paras
	 *            预编译语句的参数，多个时可直接往参数列表增加，也可以传数组
	 * @return
	 */
	public <M> M findFirst(Class<M> modelClass, String sql, Object... paras) {
		List<M> list = find(modelClass, sql, paras);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 根据主键查询并返回按模型封装的一条记录。
	 * 
	 * @param modelClass
	 *            与数据库表对应的模型class
	 * @param id
	 *            主键对象
	 * @return
	 */
	public <M> M findByPK(Class<M> modelClass, Object id) {
		List<M> list = find(modelClass, forModelFindByPK(modelClass), id);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 将模型对象保存到数据库。数据库自动生成的主键会填充到模型对象中。
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public <M> boolean save(M model) throws Exception {
		boolean result = false;
		final StringBuilder sql = new StringBuilder();
		final List<Object> paras = new ArrayList<Object>();
		forModelSave(model, sql, paras);
		showSql(sql.toString());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql.toString(),
						Statement.RETURN_GENERATED_KEYS);
				for (int index = 0; index < paras.size(); index++)
					ps.setObject(index + 1, paras.get(index));
				return ps;
			}
		}, keyHolder);
		if (row > 0) {
			result = true;
			Number num = keyHolder.getKey();
			if (num != null)
				setPK(model, num);
		}
		return result;
	}

	/**
	 * 根据模型对象更新数据库记录，如果某属性为空，会将对应表字段设为null。注意：模型对象必须包含主键值。
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public <M> boolean update(M model) throws Exception {
		Object id = getBeanFieldValue(model, primaryKey);
		if (id == null) {
			throw new RuntimeException(
					"You can't update model without Primary Key.");
		}
		StringBuilder sql = new StringBuilder();
		List<Object> paras = new ArrayList<Object>();
		forModelUpdate(model, sql, paras);
		showSql(sql.toString());
		int row = jt.update(sql.toString(), paras.toArray());
		return row >= 1;
	}

	/**
	 * 根据主键删除记录。
	 * 
	 * @param modelClass
	 *            与数据库表对应的模型class
	 * @param id
	 * @return
	 */
	public <M> boolean deleteByPK(Class<M> modelClass, Object id) {
		if (id == null) {
			throw new IllegalArgumentException("id can not be null");
		}
		String sql = forModelDeleteByPK(modelClass);
		showSql(sql);
		int row = jt.update(sql, id);
		return row >= 1;
	}

	// ---- 与模型关联方法 E ----

	// ---- 与模型无关方法 S ----
	/**
	 * 根据SQL语句查询按照指定对象返回的结果。<br/>
	 * 可以理解为queryForObject为queryForNumber,只是返回一个数值，不能返回一个模型对象
	 * 例如查"SELECT COUNT(id) FROM user"，方法调用为queryForObject(Integer.class,
	 * "SELECT COUNT(id) FROM user")
	 * 
	 * @param clazz
	 *            返回结果封装的Class
	 * @param sql
	 *            查询的SQL语句，例如"SELECT COUNT(id) FROM user WHERE name=?"
	 * @param paras
	 *            预编译语句的参数，多个时可直接往参数列表增加，也可以传数组
	 * @return
	 */
	public <T> T queryForObject(Class<T> clazz, String sql, Object... paras) {
		return jt.queryForObject(sql, paras, clazz);
	}

	/**
	 * 根据SQL语句查询按Map<String, Object>封装的单条记录结果。<br/>
	 * 注意，仅限查询结果是无或1条，多条结果抛异常。
	 * 
	 * @param sql
	 *            查询的SQL语句，例如"SELECT * FROM user WHERE id=?"
	 * @param paras
	 *            预编译语句的参数，多个时可直接往参数列表增加，也可以传数组
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Object... paras) {
		return jt.queryForMap(sql, paras);
	}

	/**
	 * 将查询结果集封装成MAP集合，支持多条数据MAP集合
	 * 
	 * @param voClass
	 *            类
	 * @param sql
	 *            查询语句
	 * @param fieldNameForKey
	 *            MAP集合的KEY名，可以为某一字段名或自行指定
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Map<String, ?> queryForMaps(Class<T> voClass, String sql,
			String fieldNameForKey) throws Exception {
		List<T> list = jt.query(sql, new BeanPropertyRowMapper(voClass));
		Map<String, T> map = new HashMap<String, T>();
		String str = "";
		for (T vo : list) {
			str = this.getBeanFieldValue(vo, fieldNameForKey).toString();
			map.put(str, vo);
		}
		return map;
	}

	/**
	 * 根据SQL语句查询按List<Map<String, Object>>封装的多条记录结果。<br/>
	 * 
	 * @param sql
	 *            查询的SQL语句，例如"SELECT * FROM user"
	 * @param paras
	 *            预编译语句的参数，多个时可直接往参数列表增加，也可以传数组
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql, Object... paras) {
		return jt.queryForList(sql, paras);
	}

	/**
	 * 执行INSERT/UPDATE/DELETE的SQL语句，返回受影响的行数
	 * 
	 * @param sql
	 *            SQL语句，例如"DELETE FROM user WHERE name=?"
	 * @param paras
	 *            预编译语句的参数，多个时可直接往参数列表增加，也可以传数组
	 * @return
	 */
	public int update(String sql, Object... paras) {
		showSql(sql);
		return jt.update(sql, paras);
	}

	/**
	 * 批量执行INSERT/UPDATE/DELETE的SQL语句，返回受影响的行数数组
	 * 
	 * @param sql
	 *            SQL语句数组，例如包含"DELETE FROM user WHERE name='name'"
	 * @return
	 */
	public int[] batchUpdate(String[] sql) {
		for (String str : sql) {
			showSql(str);
		}
		return jt.batchUpdate(sql);
	}

	/**
	 * 批量执行INSERT/UPDATE/DELETE的预编译SQL语句，返回受影响的行数数组，适用SQL语句相同而每条的参数不同的情况。
	 * 
	 * @param sql
	 *            有占位符的SQL语句，例如"DELETE FROM user WHERE name=?"
	 * @param batchParas
	 * @return
	 */
	public int[] batchUpdate(String sql, List<Object[]> batchParas) {
		return jt.batchUpdate(sql, batchParas);
	}

	/**
	 * 调用存储过程
	 * 
	 * @param outClazz
	 *            存储过程出参的数据类型，例如Integer.class
	 * @param callString
	 *            存储过程调用语句，例如"{call proc_abc(?,?)}"
	 * @param paras
	 *            存储过程的传参
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T extends CharSequence, Number> T call(final Class<T> outClazz,
			String callString, final Object... paras) {
		String outClazzName = outClazz.getName();
		if (!"java.lang.Integer".equals(outClazzName)
				&& !"java.lang.Long".equals(outClazzName)
				&& !"java.lang.Double".equals(outClazzName)
				&& !"java.lang.String".equals(outClazzName)) {
			throw new RuntimeException(
					"outClazz参数必须是Integer/Long/Double/String其中之一");
		}
		CallableStatementCallback<T> cb = new CallableStatementCallback<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				for (int index = 0; index < paras.length; index++) {
					cs.setObject(index + 1, paras[index]);
				}
				switch (outClazz.getName()) {
				case "java.lang.Integer": {
					cs.registerOutParameter(paras.length + 1, Types.INTEGER);
				}
				case "java.lang.Long": {
					cs.registerOutParameter(paras.length + 1, Types.BIGINT);
				}
				case "java.lang.Double": {
					cs.registerOutParameter(paras.length + 1, Types.DOUBLE);
				}
				case "java.lang.String": {
					cs.registerOutParameter(paras.length + 1, Types.VARCHAR);
				}
				}
				cs.execute();
				return (T) cs.getObject(paras.length + 1);
			}
		};
		return jt.execute(callString, cb);
	}

	// ---- 与模型无关方法 E ----

	/**
	 * 根据模型返回表名
	 * 
	 * @return
	 */
	private String getTableName(Class<?> modelClass) {
		return modelClass.getSimpleName().toLowerCase();
	}

	/**
	 * 根据SQL查询按Number封装的结果
	 * 
	 * @param sql
	 * @param paras
	 * @return
	 */
	private Number queryNumber(String sql, Object... paras) {
		return jt.queryForObject(sql, Number.class, paras);
	}

	/**
	 * 生成MySQL分页查询SQL语句
	 * 
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param select
	 * @param sqlExceptSelect
	 */
	private void forPaginate(StringBuilder sql, int pageNumber, int pageSize,
			String select, String sqlExceptSelect) {
		sql.append(select).append(" ").append(sqlExceptSelect)
				.append(" LIMIT ").append((pageNumber - 1) * pageSize)
				.append(",").append(pageSize);
	}

	/**
	 * 生成插入的SQL语句
	 * 
	 * @param model
	 * @param sql
	 * @param paras
	 * @throws Exception
	 */
	private <M> void forModelSave(M model, StringBuilder sql, List<Object> paras)
			throws Exception {
		ArrayList<String> fieldList = new ArrayList<String>();
		for (Field field : model.getClass().getDeclaredFields()) {
			String fieldName = field.getName();
			if (validFieldNameAndValue(model, fieldName)) {
				fieldList.add(fieldName);
				paras.add(getBeanFieldValue(model, fieldName));
			}
		}
		String tableName = getTableName(model.getClass());
		sql.append("INSERT INTO ")
				.append(tableName)
				.append(" ")
				.append(fieldList.toString().replace('[', '(')
						.replace(']', ')')).append(" VALUES (");
		for (int index = 0; index < fieldList.size(); index++) {
			if (index > 0)
				sql.append(",?");
			else
				sql.append("?");
		}
		sql.append(")");
	}

	/**
	 * 生成根据主键删除的SQL语句
	 * 
	 * @return
	 */
	private <M> String forModelDeleteByPK(Class<?> modelClass) {
		String tableName = getTableName(modelClass);
		return String.format("DELETE FROM %s WHERE %s = ?", tableName,
				primaryKey);
	}

	/**
	 * 生成根据主键update的SQL语句
	 * 
	 * @param model
	 * @param sql
	 * @param paras
	 * @throws Exception
	 */
	private <M> void forModelUpdate(M model, StringBuilder sql,
			List<Object> paras) throws Exception {
		String tableName = getTableName(model.getClass());
		sql.append("UPDATE ").append(tableName).append(" SET ");
		int count = 0;
		for (Field field : model.getClass().getDeclaredFields()) {
			String fieldName = field.getName();
			if (!fieldName.equals(primaryKey) && validFieldName(fieldName)) {
				paras.add(getBeanFieldValue(model, fieldName));
				if (count > 0)
					sql.append(",");
				sql.append(fieldName).append("=?");
				count++;
			}
		}
		sql.append(" WHERE ").append(primaryKey).append("=?");
		paras.add(getBeanFieldValue(model, primaryKey));
	}

	/**
	 * 生成根据主键查询的SQL语句
	 * 
	 * @return
	 */
	private String forModelFindByPK(Class<?> modelClass) {
		String tableName = getTableName(modelClass);
		return String.format("SELECT * FROM %s WHERE %s=?", tableName,
				primaryKey);
	}

	/**
	 * 验证模型属性名和值是否有效。不能是序列化和以下划线结尾，值不能为null。
	 * 
	 * @param model
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private <M> boolean validFieldNameAndValue(M model, String fieldName)
			throws Exception {
		if (!fieldName.endsWith("_") && !fieldName.equals("serialVersionUID")
				&& null != getBeanFieldValue(model, fieldName))
			return true;
		return false;
	}

	/**
	 * 验证模型属性名是否有效。不能是序列化和以下划线结尾。
	 * 
	 * @param fieldName
	 * @return
	 */
	private boolean validFieldName(String fieldName) {
		if (!fieldName.endsWith("_") && !fieldName.equals("serialVersionUID"))
			return true;
		return false;
	}

	/**
	 * 根据属性名获取值。
	 * 
	 * @param model
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private <M> Object getBeanFieldValue(M model, String fieldName)
			throws Exception {
		Method method = model.getClass().getMethod(
				"get".concat(
						String.valueOf(Character.toUpperCase(fieldName
								.charAt(0)))).concat(fieldName.substring(1)),
				new Class[] {});
		return method.invoke(model, new Object[] {});
	}

	/**
	 * 设置模型主键值
	 * 
	 * @param model
	 * @param value
	 * @throws Exception
	 */
	private <M> void setPK(M model, Number value) throws Exception {
		Method method = null;
		try {
			method = model.getClass().getMethod(
					"set".concat(
							String.valueOf(Character.toUpperCase(primaryKey
									.charAt(0)))).concat(
							primaryKey.substring(1)), Integer.class);
			method.invoke(model, value.intValue());
			return;
		} catch (NoSuchMethodException e) {
		}
		try {
			method = model.getClass().getMethod(
					"set".concat(
							String.valueOf(Character.toUpperCase(primaryKey
									.charAt(0)))).concat(
							primaryKey.substring(1)), Long.class);
			method.invoke(model, value.longValue());
			return;
		} catch (NoSuchMethodException e) {
		}
	}

	/**
	 * 格式化显示SQL语句
	 * 
	 * @param sql
	 */
	private void showSql(String sql) {
		// log.info(">>> SQL:".concat(sql));
	}

}
