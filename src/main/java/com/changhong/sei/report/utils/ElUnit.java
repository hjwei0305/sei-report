package com.changhong.sei.report.utils;

import com.changhong.sei.report.enums.Operator;
import com.changhong.sei.report.exception.ReportComputeException;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/7 10:04
 */
public class ElUnit {
	private Object left;
	private Object right;
	private Operator op;
	private Operator nextOp;
	private ElUnit nextUnit;
	public Object compute(){
		if(right instanceof ElUnit){
			right=((ElUnit)right).compute();
		}
		switch(op){
		case Add:
			return ArithUtils.add(left, right);
		case Complementation:
			return ArithUtils.com(left, right);
		case Divide:
			return ArithUtils.div(left, right);
		case Multiply:
			return ArithUtils.mul(left, right);
		case Subtract:
			return ArithUtils.sub(left, right);
		}
		throw new ReportComputeException("Unknow operator :"+op);
	}
	public Object getLeft() {
		return left;
	}
	public void setLeft(Object left) {
		this.left = left;
	}
	public Object getRight() {
		return right;
	}
	public void setRight(Object right) {
		this.right = right;
	}
	public Operator getOp() {
		return op;
	}
	public void setOp(Operator op) {
		this.op = op;
	}
	public Operator getNextOp() {
		return nextOp;
	}
	public void setNextOp(Operator nextOp) {
		this.nextOp = nextOp;
	}
	public ElUnit getNextUnit() {
		return nextUnit;
	}
	public void setNextUnit(ElUnit nextUnit) {
		this.nextUnit = nextUnit;
	}
}
