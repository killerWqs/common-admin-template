package com.killer.demo.utils;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author killerWqs
 * @description
 * @date 2019/02/26 - 21:14
 */
@Data
public class DBUtils {
    private static DruidDataSource druidDataSource = new DruidDataSource();

    private static Statement statement;

    private static Connection connection;

//    private static final String TABLE_NAME = "table_from_excel";

    static {
        druidDataSource.setUrl("jdbc:mysql://10.0.21.127:3306/blood_datas?useSSL=false");
        try {
            // 创建数据库
            connection = druidDataSource.getConnection("sa", "abc,123.ABC");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于创建excel表头对应的表
     *
     * @param keys
     */
    public static void createTable(String tableName, String[] keys, String[] primaryKeys) {
        StringBuilder createSql
                = new StringBuilder("create table " + tableName + " (");
        int index = 0;
        for (String key : keys) {
//            if(index == keys.length - 1) {
//                if(key.equals(primaryKey)) {
//                    createSql.append("PRIMARY KEY(" + key + "),");
//                } else{
//                    createSql.append(key + " VARCHAR(100),");
//                }
//            } else {
//                if(key.equals(primaryKey)) {
//                    createSql.append(key + " VARCHAR(100)");
//                } else{
//                    createSql.append(key + " VARCHAR(100)");
//                }
//            }
            // 根据表头创建的表的key格式为 columnName VARCHAR(50);
            String[] s = key.split(" ");
            keys[index] = s[0];
            createSql.append("`" + s[0] + "` " + s[1] + ",");
            index++;
        }

        String join = StringUtils.join(primaryKeys, ",");
        createSql.append("primary key(" + join + "));");

        try {
            statement.execute(createSql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 使用存储map的list作为数据插入， map的可以为表头
     * @param tmpData
     * @param keys
     * @param tableName
     */
    public static void insertIntoByMapBatch(List<Map<String, String>> tmpData, String[] keys, String tableName) {
        StringBuilder insertSqlBatch = new StringBuilder("insert into " + tableName + " (");
        int index = 0;
        for (String key : keys) {
            if (index == keys.length - 1) {
                insertSqlBatch.append(key + ") values ");
            } else {
                insertSqlBatch.append(key + ", ");
            }
            index++;
        }

        int index1 = 0;
        for (Map<String, String> tmpDatum : tmpData) {
            if (index1 == tmpDatum.size() - 1) {
                insertSqlBatch.append("" + tmpDatum.get(keys[index1]) + ");");
            } else {
                insertSqlBatch.append("(" + tmpDatum.get(keys[index1]) + ", ");
            }

            index1++;
        }

        try {
            statement.execute(insertSqlBatch.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 使用存储string的list作为数据插入
     * @param tmpData
     * @param keys
     * @param tableName
     */
    public static void insertIgnoreByListBatch(List<List<String>> tmpData, String[] keys, String tableName) {
        StringBuilder insertSqlBatch = new StringBuilder("insert ignore " + tableName + " (");
        int index = 0;
        for (String key : keys) {
            if (index == keys.length - 1) {
                insertSqlBatch.append("`" + key + "`) values ");
            } else {
                insertSqlBatch.append("`" + key + "`, ");
            }
            index++;
        }

        int index1 = 0;
        for (List<String> tmpDatum : tmpData) {
            int index2 = 0;
            for (String datum : tmpDatum) {
                if (index2 == tmpDatum.size() - 1) {
                    if(index1 == tmpData.size() - 1) {
                        insertSqlBatch.append("'" + datum + "');");
                    } else {
                        insertSqlBatch.append("'" + datum + "'),");
                    }
                } else if (index2 == 0) {
                    insertSqlBatch.append("('" + datum + "', ");
                } else {
                    insertSqlBatch.append("'" + datum + "', ");
                }
                index2++;
            }

            index1++;
        }

        try {
            statement.execute(insertSqlBatch.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoByListBatch(List<List<String>> tmpData, String[] keys, String tableName) {
        StringBuilder insertSqlBatch = new StringBuilder("insert into " + tableName + " (");
        int index = 0;
        for (String key : keys) {
            if (index == keys.length - 1) {
                insertSqlBatch.append("`" + key + "`) values ");
            } else {
                insertSqlBatch.append("`" + key + "`, ");
            }
            index++;
        }

        int index1 = 0;
        for (List<String> tmpDatum : tmpData) {
            int index2 = 0;
            for (String datum : tmpDatum) {
                if (index2 == tmpDatum.size() - 1) {
                    if(index1 == tmpData.size() - 1) {
                        insertSqlBatch.append("'" + datum + "');");
                    } else {
                        insertSqlBatch.append("'" + datum + "'),");
                    }
                } else if (index2 == 0) {
                    insertSqlBatch.append("('" + datum + "', ");
                } else {
                    insertSqlBatch.append("'" + datum + "', ");
                }
                index2++;
            }

            index1++;
        }

        try {
            statement.execute(insertSqlBatch.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
