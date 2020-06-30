package com.changhong.sei.report.builds.compute;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.value.ImageValue;
import com.changhong.sei.report.definition.value.Source;
import com.changhong.sei.report.enums.ImageType;
import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Image;
import com.changhong.sei.report.utils.ImageUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：图片值计算
 * @author：zhaohz
 * @date：2020/6/28 17:28
 */
public class ImageValueCompute implements ValueCompute {
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		ImageValue value=(ImageValue)cell.getValue();
		int width=value.getWidth(),height=value.getHeight();
		Source source=value.getSource();
		List<BindData> list=new ArrayList<BindData>();
		if(source.equals(Source.text)){
			String base64Data= ImageUtils.getImageBase64Data(ImageType.image, value.getValue(),width,height);
			list.add(new BindData(new Image(base64Data,value.getValue(),-1,-1)));
		}else{
			Expression expression=value.getExpression();
			ExpressionData<?> data=expression.execute(cell,cell, context);
			Object obj=data.getData();
			if(obj instanceof List){
				List<?> listData=(List<?>)obj;
				for(Object o:listData){
					if(o==null){
						continue;
					}
					String path=null;
					if(o instanceof BindData){
						BindData bindData=(BindData)o;
						Object valueData=bindData.getValue();
						if(valueData!=null){
							path=valueData.toString();
						}
					}else{
						path=o.toString();
					}
					if(StringUtils.isBlank(path)){
						continue;
					}
					String base64Data= ImageUtils.getImageBase64Data(ImageType.image, path,width,height);
					list.add(new BindData(new Image(base64Data,path,-1,-1)));
				}
			}else if(obj instanceof BindData){
				BindData bindData=(BindData)obj;
				String path=null;
				Object valueData=bindData.getValue();
				if(valueData!=null){
					path=valueData.toString();
				}
				if(StringUtils.isNotBlank(path)){
					String base64Data= ImageUtils.getImageBase64Data(ImageType.image, path,width,height);
					list.add(new BindData(new Image(base64Data,path,-1,-1)));
				}
			}else if(obj instanceof String){
				String text=obj.toString();
				if(text.startsWith("\"") && text.endsWith("\"")){
					text=text.substring(1,text.length()-1);
				}
				String base64Data= ImageUtils.getImageBase64Data(ImageType.image, text,width,height);
				list.add(new BindData(new Image(base64Data,text,-1,-1)));
			}else{
				if(obj!=null && StringUtils.isNotBlank(obj.toString())){
					String base64Data= ImageUtils.getImageBase64Data(ImageType.image, obj.toString(),width,height);
					list.add(new BindData(new Image(base64Data,obj.toString(),-1,-1)));
				}
			}
		}
		return list;
	}
	@Override
	public ValueType type() {
		return ValueType.image;
	}
}
