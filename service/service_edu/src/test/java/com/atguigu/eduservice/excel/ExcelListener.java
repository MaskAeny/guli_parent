package com.atguigu.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ExcelListener extends AnalysisEventListener<DemoData> {

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        // 将读取到内容输出到控制台
        System.out.println(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
