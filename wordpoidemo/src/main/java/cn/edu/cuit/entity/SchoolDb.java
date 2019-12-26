package cn.edu.cuit.entity;

import java.util.List;

/**
 * 	成一行两列的院校数据封装
 * @author fhzheng
 *
 */
public class SchoolDb {

	private School school1 ;	// 左列院校
	private School school2 ;	// 右列院校
	public School getSchool1() {
		return school1;
	}
	public void setSchool1(School school1) {
		this.school1 = school1;
	}
	public School getSchool2() {
		return school2;
	}
	public void setSchool2(School school2) {
		this.school2 = school2;
	}
	public SchoolDb(School school1, School school2) {
		super();
		this.school1 = school1;
		this.school2 = school2;
	}
	public SchoolDb() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SchoolDb [school1=" + school1 + ", school2=" + school2 + "]";
	}
	
}
