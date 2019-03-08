package com.kh.messenger.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;


//import javax.sql.DataSource;

public class DataBaseUtil {
	
	
	private static BasicDataSource dataSource = new BasicDataSource();
	private static DataBaseUtil instance = new DataBaseUtil();
	
	
	private static final String driverName = "oracle.jdbc.driver.OracleDriver";
	private static final String serverIP = "192.168.0.131";
	private static final String UserName = "messenger";
	private static final String UserPass = "messenger";
	

	private DataBaseUtil() {
		// jdbc접속 설정을 dataSource 객체에 해준다.
		dataSource.setDriverClassName(driverName);
		System.out.println("오라클 드라이버 로딩 성공!!");
		
		
		// db연결
		dataSource.setUrl("jdbc:oracle:thin:@" + serverIP + ":1521:xe" );
		dataSource.setUsername(UserName);
		dataSource.setPassword(UserPass);
		System.out.println("데이터베이스 연결 성공!!");
		
		
		// Connection Pool
		dataSource.setInitialSize(5); //최초 생성할 Connection수 : default 0
		dataSource.setMaxTotal(10);   //최대 생성할 Connection수 : default 8
		
		dataSource.setMaxWaitMillis(3000);  // Connection반납을 기다리는 최대시간 : default 무제한		
		dataSource.setMaxIdle(10);			// 최대 대여 가능한 Connection 수 : default 8, 음수값을 설정하면 무제한
		dataSource.setMinIdle(5); 			// 최소 대여 가능한 Connection 수 : default 0 
		
	}
	
	// DB instance 얻기
	public static DataBaseUtil getInstance() {
		return instance;
	}
	
	// DB connection 얻기
	public  Connection getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		return conn;
	}
	
	// close: 자원반납
	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		rs = null; pstmt = null; conn = null;
	}
	
	//SQL오류 Log확인
	public void pirintSQLExcption(SQLException e, String errLoc) {
		System.out.println("ErrLoc : " + errLoc);
		while(e != null) {
			System.out.println("SQLState : " + e.getSQLState());
			System.out.println("Error Code : " + e.getErrorCode());
			System.out.println("Message : " + e.getMessage());
			
			Throwable t = e.getCause();
			while (t !=null){
				System.out.println("Cause : " + t);
				t = t.getCause();
			}
			e = e.getNextException();
			
		}
		
	}
	
	
}
