package cn.edu.cuit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cuit.db.ConnUtilB;

public class ZydmDao {

/**
 * 	直接获取所有专业代码
 * @return 专业代码串列表
 * @throws SQLException
 */
public List<String> findAllZydms() throws SQLException {
		
		List<String> zydms = new ArrayList<String>();
		String sql = "select distinct zydm from a02";
		Connection conn = ConnUtilB.getConn();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			String zydm = rs.getString("zydm");
			zydms.add(zydm);
		} 
		return zydms;
	}
}
