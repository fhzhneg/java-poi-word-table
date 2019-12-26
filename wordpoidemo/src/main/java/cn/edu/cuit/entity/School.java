package cn.edu.cuit.entity;

/**
 * 	基础院校数据封装
 * 	所有数据封装都符合javaBean的一致性要求，详见文档内的说明
 * @author fhzheng
 *
 */
public class School {

	private String yxdh;		// 院校代号
	private String yxmc_temp;	// 院校名称
	private String zyslx;		// 实录线
	private String zypjf;		// 平均分
	private String zymax;		// 最高分
	
	/**
	 * 	以下为标准javabean方法，即
	 * 1 访问器getter/setter
	 * 2 无参和全参构造器，即构造函数
	 * 3 对象串行化输出，方便调试查看
	 * @return
	 */
	
	public String getYxdh() {
		return yxdh;
	}
	public void setYxdh(String yxdh) {
		this.yxdh = yxdh;
	}
	public String getYxmc_temp() {
		return yxmc_temp;
	}
	public void setYxmc_temp(String yxmc_temp) {
		this.yxmc_temp = yxmc_temp;
	}
	public String getZyslx() {
		return zyslx;
	}
	public void setZyslx(String zyslx) {
		this.zyslx = zyslx;
	}
	public String getZypjf() {
		return zypjf;
	}
	public void setZypjf(String zypjf) {
		this.zypjf = zypjf;
	}
	public String getZymax() {
		return zymax;
	}
	public void setZymax(String zymax) {
		this.zymax = zymax;
	}
	public School(String yxdh, String yxmc_temp, String zyslx, String zypjf, String zymax) {
		super();
		this.yxdh = yxdh;
		this.yxmc_temp = yxmc_temp;
		this.zyslx = zyslx;
		this.zypjf = zypjf;
		this.zymax = zymax;
	}
	public School() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "School [yxdh=" + yxdh + ", yxmc_temp=" + yxmc_temp + ", zyslx=" + zyslx + ", zypjf=" + zypjf
				+ ", zymax=" + zymax + "]";
	}
	
	
}
