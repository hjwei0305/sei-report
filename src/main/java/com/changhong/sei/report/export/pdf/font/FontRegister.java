package com.changhong.sei.report.export.pdf.font;

/**
 * @desc：字体注册接口
 * @author：zhaohz
 * @date：2020/6/30 9:58
 */
public interface FontRegister {
	/**
	 * @return 返回自定义的字体名称
	 */
	String getFontName();
	/**
	 * 返回字体所在位置，需要注意的是字体文件需要放置到classpath下，这里返回的值就是该字体文件所在classpath下位置即可
	 * @return 返回字体所在位置
	 */
	String getFontPath();
}
