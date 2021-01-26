package com.changhong.sei.report.expression;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/7 9:42
 */
public class ScriptErrorListener extends BaseErrorListener {
	private List<ErrorInfo> infos=new ArrayList<ErrorInfo>();
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
		infos.add(new ErrorInfo(line,charPositionInLine,msg));
	}
	public List<ErrorInfo> getInfos() {
		return infos;
	}
}
