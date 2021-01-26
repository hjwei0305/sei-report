package com.changhong.sei.report.model;


import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.builds.aggregate.Aggregate;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;

import java.util.List;

public class PageSelectAggregate extends Aggregate {
    @Override
    public List<BindData> aggregate(DatasetExpression datasetExpression, Cell cell, Context context) {

        return null;
    }
}
