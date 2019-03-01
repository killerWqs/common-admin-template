package com.killer.demo.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author killerWqs
 * @description
 * @date 2019/02/26 - 21:14
 */

@Data
public class ExcelAnalysisEventListener extends AnalysisEventListener {
    private int count = 0;

    private boolean hasCreated = false;

    private List<List<String>> container = new ArrayList<>();

    private String[] keys;

    @Override
    public void invoke(Object object, AnalysisContext context) {
        ExcelConfig custom = (ExcelConfig) context.getCustom();

        int capacity = custom.getCount();

            if (context.getCurrentRowNum() == 0) {
                keys = new String[((ArrayList<String>) object).size()];
                ((ArrayList<String>) object).toArray(keys);
                if (custom.isCreateTable() && !hasCreated) {
                    DBUtils.createTable(custom.getTableName(), keys, custom.getPrimaryKeys());
                    hasCreated = true;
                }
            } else {
                container.add((ArrayList<String>) object);
                this.count++;
            }

        if (this.count == capacity) {
            DBUtils.insertIgnoreByListBatch(container, keys, custom.getTableName());
            count = 0;
            container.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 把container余下的存入数据库
//        有一个问题 如果insert 批量插入 中间一条插不进去，会全部失败吗？ 使用 insert ignore
        ExcelConfig custom = (ExcelConfig) context.getCustom();
        DBUtils.insertIgnoreByListBatch(container, keys, custom.getTableName());
    }
}
