package com.killer.demo.utils;

import lombok.Data;

@Data
public class ExcelConfig {
    // 是否需要创建数据表
    private boolean createTable;

    // 创建表的名字
    private String tableName;

    // 创建数据库的主键
    private String[] primaryKeys;

    // 一次性插入数据库的数量
    private int count;
}
