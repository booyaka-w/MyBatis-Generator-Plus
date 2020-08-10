package com.booyaka.generator.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

import com.booyaka.generator.utils.Utils;

public class ConnectionController {

	private static final String VERSION5 = "5";

	private static final String VERSION8 = "8";

	private static final String CONNECTION_CONF = "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false&transformedBitIsBoolean=true&allowPublicKeyRetrieval=true";

	private static Connection CONNECTION = null;

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
			CONNECTION = Utils.getConnection(url, driverClassName, userName, passWord);
		}
		List<String> dataBaseList = Utils.getDataBase(CONNECTION);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("dataBaseList", dataBaseList);
		modelMap.addAllAttributes(attributes);
		return "data-base-list";
	}
}
