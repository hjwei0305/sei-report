package com.changhong.sei.report.export.pdf.font;

import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.exception.ReportException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;

/**
 * @desc：字体构造器
 * @author：zhaohz
 * @date：2020/6/30 9:57
 */
public class FontBuilder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	private static final Map<String, BaseFont> fontMap=new HashMap<String, BaseFont>();
	public static final Map<String,String> fontPathMap=new HashMap<String,String>();
	private static List<String> systemFontNameList=new ArrayList<String>();
	public static Font getFont(String fontName, int fontSize, boolean fontBold, boolean fontItalic, boolean underLine){
		BaseFont baseFont=fontMap.get(fontName);
		Font font=null;
		if(baseFont!=null){
			font=new Font(baseFont);
		}else{
			font= FontFactory.getFont(fontName);
		}
		font.setSize(fontSize);
		int fontStyle= Font.NORMAL;
		if(fontBold && fontItalic && underLine){
			fontStyle= Font.BOLD| Font.ITALIC| Font.UNDERLINE;
		}else if(fontBold){
			if(fontItalic){
				fontStyle= Font.BOLD| Font.ITALIC;
			}else if(underLine){
				fontStyle= Font.BOLD| Font.UNDERLINE;
			}else{
				fontStyle= Font.BOLD;
			}
		}else if(fontItalic){
			if(underLine){
				fontStyle= Font.ITALIC| Font.UNDERLINE;
			}else if(fontBold){
				fontStyle= Font.ITALIC| Font.BOLD;
			}else{
				fontStyle= Font.ITALIC;
			}
		}else if(underLine){
			fontStyle= Font.UNDERLINE;
		}
		font.setStyle(fontStyle);
		return font;
	}
	
	public static java.awt.Font getAwtFont(String fontName,int fontStyle,float size){
		if(systemFontNameList.contains(fontName)){
			return new java.awt.Font(fontName,fontStyle,new Float(size).intValue());
		}
		String fontPath=fontPathMap.get(fontName);
		if(fontPath==null){
			fontName="宋体";
			fontPath=fontPathMap.get(fontName);
			if(fontPath==null){
				return null;				
			}
		}
		try (InputStream inputStream=applicationContext.getResource(fontPath).getInputStream()) {
			java.awt.Font font=java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
			return font.deriveFont(fontStyle,size);
		} catch (Exception e) {
			throw new ReportException(e);
		}
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		FontBuilder.applicationContext=applicationContext;
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames=environment.getAvailableFontFamilyNames();
		for(String name:fontNames){
			systemFontNameList.add(name);
		}
		Collection<FontRegister> fontRegisters=applicationContext.getBeansOfType(FontRegister.class).values();
		for(FontRegister fontReg:fontRegisters){
			String fontName=fontReg.getFontName();
			String fontPath=fontReg.getFontPath();
			if(StringUtils.isEmpty(fontPath) || StringUtils.isEmpty(fontName)){
				continue;
			}
			try {
				BaseFont baseFont=getIdentityFont(fontName,fontPath,applicationContext);
				if(baseFont==null){
					throw new ReportComputeException("Font " + fontPath + " does not exist");
				}
				fontMap.put(fontName, baseFont);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ReportComputeException(e);
			}
		}
	}
	
	private BaseFont getIdentityFont(String fontFamily, String fontPath, ApplicationContext applicationContext) throws DocumentException,IOException {
		if(!fontPath.startsWith(ApplicationContext.CLASSPATH_URL_PREFIX)){
			fontPath= ApplicationContext.CLASSPATH_URL_PREFIX+fontPath;
		}
		String fontName = fontPath;
		int lastSlashPos=fontPath.lastIndexOf("/");
		if(lastSlashPos!=-1){
			fontName = fontPath.substring(lastSlashPos+1,fontPath.length());			
		}
		if (fontName.toLowerCase().endsWith(".ttc")) {
			fontName = fontName + ",0";
		}
		InputStream inputStream=null;
		try{
			fontPathMap.put(fontFamily, fontPath);
			inputStream=applicationContext.getResource(fontPath).getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			BaseFont baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED,true,bytes,null);
			baseFont.setSubset(true);
			return baseFont;			
		}finally{
			if(inputStream!=null)inputStream.close();
		}
	}
}
