package com.changhong.sei.report.chart.option;

/**
 * @desc：底边
 * @author：zhaohz
 * @date：2020/6/30 16:45
 */
public class Padding {
	private int left;
	private int right;
	private int top;
	private int bottom;
	public String toJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("left:"+left+",");
		sb.append("right:"+right+",");
		sb.append("top:"+top+",");
		sb.append("bottom:"+bottom);
		sb.append("}");
		return sb.toString();
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getBottom() {
		return bottom;
	}
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
}
