package com.changhong.sei.report.builds.compute;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.value.Source;
import com.changhong.sei.report.definition.value.ZxingValue;
import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Image;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @desc：条码值计算
 * @author：zhaohz
 * @date：2020/6/28 17:31
 */
public class ZxingValueCompute implements ValueCompute {
	private static final int BLACK = 0xff000000;  
    private static final int WHITE = 0xFFFFFFFF;
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		List<BindData> list=new ArrayList<BindData>();
		ZxingValue value=(ZxingValue)cell.getValue();
		String format=value.getFormat();
		BarcodeFormat barcodeForamt= BarcodeFormat.QR_CODE;
		if(StringUtils.isNotBlank(format)){
			barcodeForamt= BarcodeFormat.valueOf(format);
		}
		int w=value.getWidth();
		int h=value.getHeight();
		Source source=value.getSource();
		if(source.equals(Source.text)){
			String data=value.getValue();
			Image image=buildImage(barcodeForamt,data,w,h);
			list.add(new BindData(image));
		}else{
			Expression expression=value.getExpression();
			ExpressionData<?> data=expression.execute(cell,cell, context);
			if(data instanceof BindDataListExpressionData){
				BindDataListExpressionData listData=(BindDataListExpressionData)data;
				List<BindData> bindDataList=listData.getData();
				for(BindData bindData:bindDataList){
					Object obj=bindData.getValue();
					if(obj==null)obj="";
					Image image=buildImage(barcodeForamt,obj.toString(),w,h);
					list.add(new BindData(image));
				}
			}else if(data instanceof ObjectExpressionData){
				ObjectExpressionData exprData=(ObjectExpressionData)data;
				Object obj=exprData.getData();
				if(obj==null){
					obj="";
				}else if(obj instanceof String){
					String text=obj.toString();
					if(text.startsWith("\"") && text.endsWith("\"")){
						text=text.substring(1,text.length()-1);
					}
					obj=text;
				}
				Image image=buildImage(barcodeForamt,obj.toString(),w,h);
				list.add(new BindData(image));
			}else if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData listExprData=(ObjectListExpressionData)data;
				List<?> listData=listExprData.getData();
				for(Object obj:listData){
					if(obj==null){
						obj="";
					}else if(obj instanceof String){
						String text=obj.toString();
						if(text.startsWith("\"") && text.endsWith("\"")){
							text=text.substring(1,text.length()-1);
						}
						obj=text;
					}
					Image image=buildImage(barcodeForamt,obj.toString(),w,h);
					list.add(new BindData(image));
				}
			}
		}
		return list;
	}
	
	private Image buildImage(BarcodeFormat format, String data, int w, int h){
        try{
        	Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN,0);
            if(format.equals(BarcodeFormat.QR_CODE)){
            	hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            }
            BitMatrix matrix = new MultiFormatWriter().encode(data,format, w, h,hints);
            int width = matrix.getWidth();  
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < width; x++) {
            	for (int y = 0; y < height; y++) {
            		image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            	}
            }
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				ImageIO.write(image, "png", outputStream);
				byte[] bytes = outputStream.toByteArray();
				String base64Data = Base64Utils.encodeToString(bytes);
				return new Image(base64Data, w, h);
			}catch (Exception e){
            	throw e;
			}
        }catch(Exception ex){
        	throw new ReportComputeException(ex);
        }
	}

	@Override
	public ValueType type() {
		return ValueType.zxing;
	}
}
