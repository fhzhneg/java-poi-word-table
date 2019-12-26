package cn.edu.cuit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnUtilB {

	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static final String driver="com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost:3306/zz"
			+ "?useUnicode=true&characterEncoding=utf-8";	
	private static final String user="root";
	private static final String password="rootcuit";
	private ConnUtilB() {}
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized static Connection getConn()
			throws SQLException{
		Connection conn = threadLocal.get();
		if (conn==null || conn.isClosed()) {
			conn = DriverManager.getConnection(url, user, password);
			threadLocal.set(conn);
		}
		return conn;
	}
	
	public static void closeConn() {
		Connection conn = threadLocal.get();
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			threadLocal.set(null);
		}		
	}	
}
