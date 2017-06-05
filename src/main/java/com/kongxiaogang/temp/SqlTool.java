package com.kongxiaogang.temp;

public class SqlTool {
	public static void main(String[] args) {
		String sql = "balance_id, accountant_id, amount, change_time, busis_status, change_source, source_id, after_change_amount";
		String pre = "bcr";
		System.out.println(sqlToString(sql, pre));
		System.out.println(stringToTarget(sql));
	}
	private static String stringToTarget(String sql) {
		String returnString = "";
		String temp [] = sql.split(",");
		for (int i = 0; i < temp.length; i++) {
			String ziDuan = temp[i];
			ziDuan = zhuanHuan(ziDuan)+",";
			returnString += ziDuan;
		}
		return returnString;
	}
	public static String  sqlToString(String sql, String pre) {
		String returnString = "";
		String temp [] = sql.split(",");
		for (int i = 0; i < temp.length; i++) {
			String ziDuan = temp[i];
			ziDuan = pre+"."+ziDuan.trim()+" as "+ zhuanHuan(ziDuan)+", ";
			returnString += ziDuan;
		}
		return returnString;
	}
	private static String zhuanHuan(String ziDuan) {
		int index = 0;
		do {
			index = ziDuan.indexOf("_");
			String temp = ziDuan.substring(index+1,index+2);
			ziDuan = ziDuan.replace("_"+temp, temp.toUpperCase());
		} while(index>0);
		return ziDuan;
	}
}
