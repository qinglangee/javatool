package com.xmalloc.javatool.math;

import java.text.NumberFormat;

public class NumberTool {

	/**
	 * 计算百分率
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static String percent(double p1, double p2) {
		String str;

		double p3 = p1 / p2;

		NumberFormat nf = NumberFormat.getPercentInstance();

		nf.setMinimumFractionDigits(0);
		str = nf.format(p3);

		return str;
	}

	/**
	 * 解析ID, 只返回非负数, 返回-1则是错误ID
	 * 
	 * @param s
	 * @return
	 */
	public static long parseId(String s) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return -1;
		}
	}
}
