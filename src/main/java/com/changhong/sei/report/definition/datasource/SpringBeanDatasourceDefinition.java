package com.changhong.sei.report.definition.datasource;

import com.changhong.sei.report.builds.Dataset;
import com.changhong.sei.report.definition.dataset.BeanDatasetDefinition;
import com.changhong.sei.report.definition.dataset.DatasetDefinition;
import com.changhong.sei.report.enums.DatasourceType;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc：spingbean数据源
 * @author：zhaohz
 * @date：2020/6/30 17:52
 */
public class SpringBeanDatasourceDefinition implements DatasourceDefinition {
	private String beanId;
	private String name;
	private List<DatasetDefinition> datasets;
	
	public List<Dataset> getDatasets(ApplicationContext applicationContext, Map<String,Object> parameters){
		Object targetBean=applicationContext.getBean(beanId);
		List<Dataset> list=new ArrayList<Dataset>();
		for(DatasetDefinition dsDef:datasets){
			BeanDatasetDefinition beanDef=(BeanDatasetDefinition)dsDef;
			Dataset ds=beanDef.buildDataset(name,targetBean, parameters);
			list.add(ds);
		}
		return list;
	}
	
	@Override
	public DatasourceType getType() {
		return DatasourceType.spring;
	}

	@Override
	public List<DatasetDefinition> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DatasetDefinition> datasets) {
		this.datasets = datasets;
	}
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	public String getBeanId() {
		return beanId;
	}
}
