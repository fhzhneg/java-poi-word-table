package cn.edu.cuit.entity;

import java.util.List;

/**
 * 	专业数据封装
 * @author fhzheng
 *
 */
public class Major {

	private String zydm;				// 专业代码
	private String dmzymc;				// 专来名称
	private List<SchoolDb> schools;		// 第二行开始的院校双列数据列表
	
	public String getZydm() {
		return zydm;
	}
	public void setZydm(String zydm) {
		this.zydm = zydm;
	}
	public String getDmzymc() {
		return dmzymc;
	}
	public void setDmzymc(String dmzymc) {
		this.dmzymc = dmzymc;
	}
	public List<SchoolDb> getSchools() {
		return schools;
	}
	public void setSchools(List<SchoolDb> schools) {
		this.schools = schools;
	}
	@Override
	public String toString() {
		return "Major [zydm=" + zydm + ", dmzymc=" + dmzymc + ", schools=" + schools + "]";
	}
	public Major(String zydm, String dmzymc, List<SchoolDb> schools) {
		super();
		this.zydm = zydm;
		this.dmzymc = dmzymc;
		this.schools = schools;
	}
	public Major() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
