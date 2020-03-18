package com.changhong.sei.report.model;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.aggregate.Aggregate;
import com.bstek.ureport.expression.model.expr.dataset.DatasetExpression;
import com.bstek.ureport.model.Cell;

import java.util.List;

public class PageSelectAggregate extends Aggregate{
    @Override
    public List<BindData> aggregate(DatasetExpression datasetExpression, Cell cell, Context context) {

        return null;
    }
}
