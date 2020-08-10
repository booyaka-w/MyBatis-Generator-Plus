package com.booyaka.generator.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

import com.zaxxer.hikari.HikariDataSource;

public class Utils {

	private static final String QUERY_DATA_BASE_SQL = "SELECT SCHEMA_NAME AS `DataBase` FROM INFORMATION_SCHEMA.SCHEMATA";

	public static Connection getConnection(String url, String driverClassName, String userName, String passWord)
			throws SQLException {
		DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).url(url)
				.driverClassName(driverClassName).username(userName).password(passWord).build();
		Connection connection = dataSource.getConnection();
		return connection;
	}

	public static List<String> getDataBase(Connection connection) throws SQLException {
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
}
