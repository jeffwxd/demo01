package com.example.demo01;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import org.junit.Test;

import java.util.List;

/**
 * 写出Excel单元测试
 * 
 * @author looly
 */
public class BigExcelWriteTest {
	
	@Test
	//@Ignore
	public void writeTest2() {
		List<String> row = CollUtil.newArrayList("姓名", "加班日期", "下班时间", "加班时长", "餐补", "车补次数", "车补", "总计");
		BigExcelWriter overtimeWriter = ExcelUtil.getBigWriter("d:/excel1/single_line.xlsx");
		overtimeWriter.write(row);
		overtimeWriter.close();
	}

	@Test
	//@Ignore
	public void writeTest() {
		List<?> row1 = CollUtil.newArrayList("aaaaa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
		List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
		List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
		List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
		List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

		List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
		for(int i=0; i < 400000; i++) {
			//超大列表写出测试
			rows.add(ObjectUtil.clone(row1));
		}
		
		String filePath = "D:/bigWriteTest.xlsx";
		FileUtil.del(filePath);
		// 通过工具类创建writer
		BigExcelWriter writer = ExcelUtil.getBigWriter(filePath);

//		// 跳过当前行，即第一行，非必须，在此演示用
		writer.passCurrentRow();
//		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(row1.size() - 1, "大数据测试标题");
		// 一次性写出内容，使用默认样式
		writer.write(rows);
//		writer.autoSizeColumn(0, true);
		// 关闭writer，释放内存
		writer.close();
	}

}
