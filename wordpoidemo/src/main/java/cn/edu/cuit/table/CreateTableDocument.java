package cn.edu.cuit.table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.alibaba.fastjson.JSON;

import cn.edu.cuit.dao.MajorDao;
import cn.edu.cuit.dao.ZydmDao;
import cn.edu.cuit.entity.Major;
import cn.edu.cuit.entity.SchoolDb;

public class CreateTableDocument {

	/**
	 * 	基础测试方法
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		create25Table();
		createData01();
	}

	/**
	 * 	单个表操作基础实验方法
	 * 	生成二行五列的表进行操作
	 * 	注意，操作完成以后，生成相应的文档，用wps打开效果不可取，一定要用word打开
	 * 	直接运行，会生成两个文件在本项目的目录下
	 * @throws Exception
	 */
	public static void create25Table() throws Exception {
		
		// 创建文档
		XWPFDocument doc = new XWPFDocument();
		// 创建文档中的表格，注意，行记录是从0开始索引
		XWPFTable table = doc.createTable(2, 5);
		
		/**
		 * 	处理第一行
		 */
		// 使用第0行,提取第0行所有列
		List<XWPFTableCell> head = table.getRow(0).getTableCells();
		// 写第1个单元格的数据
		head.get(0).setText("一行一列表头");
		// 将第一列到第四列合并，即第二格到第五格
		for (int i = 1; i <= 4; i++) {
			// 对单元格合并时，要标记单元格是否为起点，或者是否为继续合并
			if (i == 1) {
				head.get(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);// 起点
			} else {
				head.get(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);// 继续合并
			}
		}
		head.get(1).setText("一行二列表头==合并后的第二列");
		
		/**
		 * 	处理第二行
		 */
		// 使用第1行，有5个单元格
		List<XWPFTableCell> line1 = table.getRow(1).getTableCells();
		for (int i = 0; i < 5; i++) {
			line1.get(i).setText("新标题" + i);
		}

		// 生成文件
		FileOutputStream out = new FileOutputStream(new File("create_table_01.docx"));
		doc.write(out);
		out.close();
		System.out.println("create complex table in file complete.");

	}

	/**
	 * 	先写一个专业代码的测试生成，硬编码完成
	 * 	再写所有专业代码的数据表格生成，循环完成
	 * @throws Exception
	 */
	public static void createData01() throws Exception {

		/**
		 * 	单个专业数据生成表
		 */
		String zydm = "0015";
		MajorDao majorDao = new MajorDao();
		// 从库表中获取数据
		Major major = majorDao.findMajor(zydm);
//		System.out.println(JSON.toJSONString(major, true));
//		System.out.println(major); 
		// 创建文档
		XWPFDocument doc = new XWPFDocument();
		// 创建文档中的表格
		XWPFTable table = doc.createTable();
		
		/**
		 * 	首行，即专业名称行处理
		 */
		// 创建行
		XWPFTableRow tableZymcRow = table.getRow(0);
		// 写入列，专业名称行，只有两列
		tableZymcRow.getCell(0).setText("专业名称");
//		tableZymcRow.addNewTableCell().setText(major.getDmzymc());
		tableZymcRow.addNewTableCell();	// 添加三列
		tableZymcRow.addNewTableCell();
		tableZymcRow.addNewTableCell();
		// 合并行上的单元格
		mergeCellsHorizontal(table, tableZymcRow, 1, 3);
		tableZymcRow.getCell(1).setText(major.getDmzymc());
		
		/**
		 * 	院校数据行处理
		 */
		// 循环创建行，并写入列，院校行，四列
		for (SchoolDb schoolDb : major.getSchools()) {
			XWPFTableRow tableSchoolRow = table.createRow();
			tableSchoolRow.getCell(0).setText(schoolDb.getSchool1().getYxmc_temp());
			tableSchoolRow.getCell(1).setText(schoolDb.getSchool1().getZyslx() + "/" + schoolDb.getSchool1().getZypjf()
					+ "/" + schoolDb.getSchool1().getZymax());
			if (" ".equals(schoolDb.getSchool2().getYxmc_temp())) {
				tableSchoolRow.getCell(2).setText("");
				tableSchoolRow.getCell(3).setText("");
			} else {
				tableSchoolRow.getCell(2).setText(schoolDb.getSchool2().getYxmc_temp());
				tableSchoolRow.getCell(3).setText(schoolDb.getSchool2().getZyslx() + "/" + schoolDb.getSchool2().getZypjf()
						+ "/" + schoolDb.getSchool2().getZymax());
			}
		}
		
		/**
		 * 	调用方法来循环处理其他专业代码
		 */
		// 获取所有专业代码，然后直接填充表格
		ZydmDao zydmDao = new ZydmDao();		
		List<String> zydms = zydmDao.findAllZydms();
		// 表格行生成与填写
		for(String zydmTmp:zydms) {
			zydmCreateTable(table,zydmTmp);
		}
		
		
		// 生成文件
		FileOutputStream out = new FileOutputStream(new File("create_table_02.docx"));
		doc.write(out);
		out.close();
		System.out.println("create file complete.");

	}

	/**
	 * 	用专业代码获取相应的院校数据，填充表格
	 * @param table 表对象
	 * @param zydm 专业代码
	 * @throws Exception
	 */
	private static void zydmCreateTable(XWPFTable table,String zydm) throws Exception {
		
		MajorDao majorDao;
		Major major;
		XWPFTableRow tableZymcRow;
		majorDao = new MajorDao();
		major = majorDao.findMajor(zydm);
		
		// 生成首行
		tableZymcRow = table.createRow();
		tableZymcRow.getCell(0).setText("专业名称");
		// 合并行上的单元格
		mergeCellsHorizontal(table, tableZymcRow, 1, 3);
		tableZymcRow.getCell(1).setText(major.getDmzymc());
		// 循环创建行，并写入列，院校行，四列
		for (SchoolDb schoolDb : major.getSchools()) {
			// 循环生成行
			XWPFTableRow tableSchoolRow = table.createRow();
			// 填写行内单元格里的数据
			tableSchoolRow.getCell(0).setText(schoolDb.getSchool1().getYxmc_temp());
			tableSchoolRow.getCell(1).setText(schoolDb.getSchool1().getZyslx() + "/" + schoolDb.getSchool1().getZypjf()
					+ "/" + schoolDb.getSchool1().getZymax());
			// 最后一个元素为空格，则直接填空串
			if (" ".equals(schoolDb.getSchool2().getYxmc_temp())) {
				tableSchoolRow.getCell(2).setText("");
				tableSchoolRow.getCell(3).setText("");
			} else {
				tableSchoolRow.getCell(2).setText(schoolDb.getSchool2().getYxmc_temp());
				tableSchoolRow.getCell(3).setText(schoolDb.getSchool2().getZyslx() + "/" + schoolDb.getSchool2().getZypjf()
						+ "/" + schoolDb.getSchool2().getZymax());
			}
		}
	}

	// word跨列合并单元格
	/**
	 * @param table 表对象
	 * @param row 列对象
	 * @param fromCell 合并单元格的起点
	 * @param toCell 合并单元格的终点
	 */
	public static void mergeCellsHorizontal(XWPFTable table, XWPFTableRow row, int fromCell, int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = row.getCell(cellIndex);
			if (cellIndex == fromCell) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

	// word跨行并单元格,此工具方法在本实验中未用到
	/**
	 * @param table
	 * @param col
	 * @param fromRow
	 * @param toRow
	 */
	public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			if (rowIndex == fromRow) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			}
		}
	}

}
