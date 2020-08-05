package com.booyaka.generator.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
