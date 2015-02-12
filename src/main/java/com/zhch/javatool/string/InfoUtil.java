package com.zhch.javatool.string;

public class InfoUtil {
	/** line width */
	public static int LW = 80;
	/** 行的填充符 */
	public static char LC = '*';
	/** 换行符 */
	public static String LS = System.getProperty("line.separator");
	/** 开头打印的信息 */
	public static String headLine;
	/** 结尾打印的信息 */
	public static String footLine;

	static {
		init();
	}

	public static void init() {
		String line = "";
		String blank = "";
		int centerCount = LW > 2 ? LW - 2 : 0;
		for (int i = 0; i < centerCount; i++) {
			line += LC;
			blank += ' ';
		}
		headLine = LC + line + LC + LS + LC + blank + LC + LS;
		footLine = LS + LC + blank + LC + LS + LC + line + LC;
	}

	/**
	 * 返回组合的内容
	 * @param content
	 * @return
	 */
	public static String startInfo(String content) {
		return LS + headLine + fillContent(content);
	}
	public static String endInfo(String content) {
		return LS + fillContent(content) + footLine;
	}

	/**
	 * 两端填充字符
	 * 
	 * @param content
	 * @return
	 */
	public static String fillContent(String content) {
		if (content.length() > LW) {
			return content;
		}
		String result = "";
		int num = (LW - content.length()) / 2;
		for (int i = 0; i < num; i++) {
			result += LC;
		}
		result += content;
		for (int i = 0; i < num; i++) {
			result += LC;
		}
		return result;
	}

}
