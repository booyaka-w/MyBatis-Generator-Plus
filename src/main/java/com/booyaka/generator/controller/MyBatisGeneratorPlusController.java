package com.booyaka.generator.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaxxer.hikari.HikariDataSource;

import net.sf.json.JSONArray;

@Controller
public class MyBatisGeneratorPlusController {

	private static final String VERSION5 = "5";

	private static final String VERSION8 = "8";

	private static final String CONNECTION_CONF = "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false&transformedBitIsBoolean=true&allowPublicKeyRetrieval=true";

	private static final String QUERY_DATA_BASE_SQL = "SELECT SCHEMA_NAME AS `DataBase` FROM INFORMATION_SCHEMA.SCHEMATA";

	private static final String QUERY_TABLE_SQL = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = ";

	private static final String QUERY_TABLE_DETAILS_SQL = "SELECT * FROM ";

	private static Connection CONNECTION = null;

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@PostMapping("/connectionDataSource")
	public String connectionDataSource(ModelMap modelMap, String host, String userName, String passWord,
			String versions) throws SQLException {
		String url = "jdbc:mysql://" + host + CONNECTION_CONF;
		String driverClassName = "";
		if (VERSION5.equals(versions)) {
			driverClassName = "com.mysql.jdbc.Driver";
		}
		if (VERSION8.equals(versions)) {
			driverClassName = "com.mysql.cj.jdbc.Driver";
		}
		if (CONNECTION == null) {
			CONNECTION = getConnection(url, driverClassName, userName, passWord);
		}
		List<String> dataBaseList = queryDataBaseList(CONNECTION);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("dataBaseList", dataBaseList);
		modelMap.addAllAttributes(attributes);
		return "data-base-list";
	}

	@GetMapping("/tables")
	@ResponseBody
	public String getTables(ModelMap modelMap, String dataBaseName) throws SQLException {
		String sql = QUERY_TABLE_SQL + "'" + dataBaseName + "'";
		ResultSet resultSet = CONNECTION.prepareStatement(sql).executeQuery();
		List<Map<String, String>> tablesList = new ArrayList<>();
		while (resultSet.next()) {
			Map<String, String> map = new HashMap<>();
			map.put("TABLE", resultSet.getString(1));
			map.put("REMARK", resultSet.getString(2));
			tablesList.add(map);
		}
		return JSONArray.fromObject(tablesList).toString();
	}

	@GetMapping("/tableDetails")
	public String GetTableDetails(ModelMap modelMap, String tableName) throws SQLException {
		System.err.println(tableName);
		String sql = QUERY_TABLE_DETAILS_SQL + tableName;
		PreparedStatement stmt = null;
		try {
			stmt = CONNECTION.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				// 获得所有列的数目及实际列数
				int columnCount = data.getColumnCount();
				// 获得指定列的列名
				String columnName = data.getColumnName(i);
				// 获得指定列的列值
				int columnType = data.getColumnType(i);
				// 获得指定列的数据类型名
				String columnTypeName = data.getColumnTypeName(i);
				// 所在的Catalog名字
				String catalogName = data.getCatalogName(i);
				// 对应数据类型的类
				String columnClassName = data.getColumnClassName(i);
				// 在数据库中类型的最大字符个数
				int columnDisplaySize = data.getColumnDisplaySize(i);
				// 默认的列的标题
				String columnLabel = data.getColumnLabel(i);
				// 获得列的模式
				String schemaName = data.getSchemaName(i);
				// 某列类型的精确度(类型的长度)
				int precision = data.getPrecision(i);
				// 小数点后的位数
				int scale = data.getScale(i);
				// 获取某列对应的表名
				String table = data.getTableName(i);
				// 是否自动递增
				boolean isAutoInctement = data.isAutoIncrement(i);
				// 在数据库中是否为货币型
				boolean isCurrency = data.isCurrency(i);
				// 是否为空
				int isNullable = data.isNullable(i);
				// 是否为只读
				boolean isReadOnly = data.isReadOnly(i);
				// 能否出现在where中
				boolean isSearchable = data.isSearchable(i);
				System.out.println(columnCount);
				System.out.println("获得列" + i + "的字段名称:" + columnName);
				System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
				System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
				System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
				System.out.println("获得列" + i + "小数点后的位数:" + scale);
				System.out.println("获得列" + i + "对应数据类型的类:" + columnClassName);
				System.out.println("获得列" + i + "在数据库中类型的最大字符个数:" + columnDisplaySize);
				System.out.println("获得列" + i + "是否为空:" + isNullable);
				System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
				System.out.println("获得列" + i + "能否出现在where中:" + isSearchable);
				System.out.println("获得列" + i + "所在的Catalog名字:" + catalogName);
				System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
				System.out.println("获得列" + i + "的模式:" + schemaName);
				System.out.println("获得列" + i + "对应的表名:" + table);
				System.out.println("获得列" + i + "的类型,返回SqlType中的编号:" + columnType);
				System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return "table-details";
	}

	private List<String> queryDataBaseList(Connection connection) throws SQLException {
		List<String> dataBaseList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(QUERY_DATA_BASE_SQL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				dataBaseList.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			// connection.close();
		}
		return dataBaseList;
	}

	private Connection getConnection(String url, String driverClassName, String userName, String passWord)
			throws SQLException {
		DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).url(url)
				.driverClassName(driverClassName).username(userName).password(passWord).build();
		Connection connection = dataSource.getConnection();
		return connection;
	}
}
