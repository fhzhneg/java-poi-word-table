package cn.edu.cuit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.cuit.db.ConnUtilB;
import cn.edu.cuit.entity.Major;
import cn.edu.cuit.entity.School;
import cn.edu.cuit.entity.SchoolDb;

public class MajorDao {
	
	/**
	 * 	直接获取打印时需要的数据结构：专业名称，院校双列数据列表
	 * @param zydm
	 * @return 专业数据结构
	 * @throws Exception
	 */
	public Major findMajor(String zydm) throws Exception {
		
		Major major = findPart1MajorByZydm(zydm);
		major = findPart2MajorByZydm(major);
		
		return major;
	}

	/**
	 * 	一次封装，得到专业名称
	 * @param zydm
	 * @return
	 * @throws Exception
	 */
	public static  Major findPart1MajorByZydm(String zydm) throws Exception {
		
		Major major = new Major();
		
		String sql = "select distinct zydm,dmzymc from a02 where zydm = ?";
		Connection conn = ConnUtilB.getConn();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, zydm);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			major.setZydm(rs.getString("zydm"));
			major.setDmzymc(rs.getString("dmzymc"));
			return major;
		} else {
			return major;
		}
	}
	
	/**
	 * 	二次封装，得到院校名和三个分数，并将数据做成双列
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public  static Major findPart2MajorByZydm(Major major) throws Exception {
		
		Major majorTmp = major;
		List<School> schools = new ArrayList<School>();
		String sql = "select yxdh,yxmc_temp,zyslx,zypjf,zymax from a02 where zydm = ?";
		Connection conn = ConnUtilB.getConn();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, major.getZydm());
		ResultSet rs = ps.executeQuery();
		
		// 得到数据列表-单列状态
		while (rs.next()) {
			School schoolInfo = new School();
			schoolInfo.setYxdh(rs.getString("yxdh"));
			schoolInfo.setYxmc_temp(rs.getString("yxmc_temp"));
			schoolInfo.setZyslx(rs.getString("zyslx"));
			schoolInfo.setZypjf(rs.getString("zypjf"));
			schoolInfo.setZymax(rs.getString("zymax"));
			schools.add(schoolInfo);
		}
		
		// 凑成偶数个元素，元素内部的属性全部置为一个空格【打印时亦不可见】
		if (schools.size()%2 != 0) {
			School schoolInfo = new School(" "," "," "," "," ");
			schools.add(schoolInfo);
		}
		
		// 封状成双列学校列表，此时，列表一定是偶数个元素
		List<SchoolDb> schoolDbs = new ArrayList<SchoolDb>();
		for(int i = 0; i < schools.size(); i += 2) {	// 核心技巧，步长为2，即一次处理原列表中的两个元素
			// 每次取两个
			School first = schools.get(i);
			School second = schools.get(i + 1);
//			School second = null;
//		    if(schools.size() > i + 1){
//		        second = schools.get(i + 1);
//		    }
		    // 封装成SchoolDb
		    SchoolDb schoolDb = new SchoolDb();
		    schoolDb.setSchool1(first);
		    schoolDb.setSchool2(second);
		    schoolDbs.add(schoolDb);
		}
		
		// 封装成majorTmp
		majorTmp.setSchools(schoolDbs);
		
		return majorTmp;
	}
	
}
