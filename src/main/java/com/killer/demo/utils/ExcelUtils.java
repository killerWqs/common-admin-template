package com.killer.demo.utils;


import cn.gyrotech.common.utils.DBUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.springframework.util.Assert;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.*;

/**
 *@description
 *@author killerWqs
 *@date 2019/02/26 - 21:14
 */
@Data
public class ExcelUtils {
    /**
     * 使用apache poi
     * 读取文件中的xml并转为map对象,标题需在第一行，数据从第二行开始, 文件头保持一致
     * @param excelPath
     * @return
     */
    public static Map<String, Object> excel2Obj(String ...excelPath) {
        HashMap<String, Object> res = new HashMap<>(2);
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            for (String filePath : excelPath) {
                File excel = new File(filePath);
                if (excel.isFile() && excel.exists()) {
                    String[] split = excel.getName().split("\\.");
                    Workbook wb;
                    //根据文件后缀（xls/xlsx）进行判断
                    if ("xls".equals(split[split.length - 1])) {
                        FileInputStream fis = new FileInputStream(excel);
                        wb = new HSSFWorkbook(fis);
                    } else if ("xlsx".equals(split[split.length - 1])) {
                        // excel文件太大时，会卡死在这一步
                        wb = new XSSFWorkbook(excel);
                    } else {
                        System.out.println("文件类型错误!");
                        return null;
                    }

                    //开始解析
                    Sheet sheet = wb.getSheetAt(0);

                    // 解析表单头
                    Row row1 = sheet.getRow(0);
                    String[] keys = {};
                    for(int i = 0 ; i < row1.getLastCellNum() - 1; i ++) {
                        keys[i] = String.valueOf(row1.getCell(i) != null ? row1.getCell(i) : "");
                    }

                    res.put("keys", keys);

                    //第一行是列名，所以不读
                    int firstRowIndex = sheet.getFirstRowNum() + 1;
                    int lastRowIndex = sheet.getLastRowNum();
                    System.out.format(excel.getName() + "共计：" + (lastRowIndex - firstRowIndex + 1) + "行数据");

                    //遍历行
                    for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                        HashMap<String, String> column = new HashMap<>(keys.length);

                        Row row = sheet.getRow(rIndex);
                        if (row != null) {
                            int firstCellIndex = row.getFirstCellNum();
                            int lastCellIndex = row.getLastCellNum();
                            //遍历列
                            for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                                Cell cell = row.getCell(cIndex);
                                if (cell != null) {
                                    column.put(keys[cIndex], String.valueOf(cell));
                                        // 筛选
                                    screenByCretira(keys, cIndex, list, cell, column);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("找不到指定的文件");
                }
            }

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        res.put("data", list);
        return res;
    }

    // 筛选条件
    public static void screenByCretira(String[] keys, int cIndex, List list, Cell cell, Map column) {
        if(keys[cIndex].equals("SMP_ID") && String.valueOf(cell).charAt(String.valueOf(cell).length() - 2) == 'B') {
            list.add(column);
        }
//        不做筛选
//        list.add(column);
    }

    // 写入到文件
    public static void write2File(String filename, String[] keys, List<Map<String, String>> data) {
        //        写入到文件中
        File outputFile = new File(filename);

        try {
            Workbook workbook = WorkbookFactory.create(outputFile);
            Sheet sheet = workbook.createSheet();


            System.out.println("总共需要添加:" + data.size() + "条数据");
            // 创建表头
            Row row = sheet.createRow(0);

            for (int i = 0; i < keys.length - 1; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(keys[i]);
            }

            for (int i = 1; i < data.size(); i++) {
                Row row1 = sheet.createRow(i);
                Map<String, String> stringStringMap = data.get(i - 1);
                for (int j = 0; j < stringStringMap.size() - 1; j++) {
                    String value = stringStringMap.get(keys[j]);
                    Cell cell = row1.createCell(j);
                    cell.setCellValue(value);
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以下是使用apache poi的sax模式来读取excel文件
     */
    private static LinkedHashMap<String, String> rowContents=new LinkedHashMap<String, String>();

    private static SheetHandler sheetHandler;

    public static LinkedHashMap<String, String> getRowContents() {
        return rowContents;
    }
    public static void setRowContents(LinkedHashMap<String, String> rowContents) {
        rowContents = rowContents;
    }

    public static SheetHandler getSheetHandler() {
        return sheetHandler;
    }
    public static void setSheetHandler(SheetHandler sheetHandler) {
        sheetHandler = sheetHandler;
    }

    public static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "com.sun.org.apache.xerces.internal.parsers.SAXParser"
                );
        setSheetHandler(new SheetHandler(sst));
        ContentHandler handler = (ContentHandler) sheetHandler;
        parser.setContentHandler(handler);
        return parser;
    }

    public static class SheetHandler  extends DefaultHandler {

        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private String cellPosition;
        private LinkedHashMap<String, String> rowContents = new LinkedHashMap<String, String>();

        public LinkedHashMap<String, String> getRowContents() {
            return rowContents;
        }

        public void setRowContents(LinkedHashMap<String, String> rowContents) {
            this.rowContents = rowContents;
        }

        public SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        @Override
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            if (name.equals("c")) {
                //   System.out.print(attributes.getValue("r") + " - ");
                cellPosition = attributes.getValue("r");
                String cellType = attributes.getValue("t");
                if (cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // 清楚缓存内容
            lastContents = "";
        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            if (nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            if (name.equals("v")) {
//            System.out.println("lastContents:"+cellPosition+";"+lastContents);
                //数据读取结束后，将单元格坐标,内容存入map中
                //不保存第一行数据
                if (!(cellPosition.length() == 2) || (cellPosition.length() == 2 && !"1".equals(cellPosition.substring(1)))) {
                    rowContents.put(cellPosition, lastContents);
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }

    // 使用alibaba的easyexcel来读取xml文件
    // easyexcel 必须使用<version>1.1.2-beat1</version>， 否则无法解析xlsx文件

    // 使用alibaba的easyexcel来读取xml文件
    // easyexcel 必须使用<version>1.1.2-beat1</version>， 否则无法解析xlsx文件

    /**
     * 解析excel
     * @param excelPaths
     * @return
     */
    public static void excel2ObjByEE(String[] excelPaths, AnalysisEventListener excelAnalysisEventListener) {

        for (String excelPath : excelPaths) {
            // ..
            try {
                FileInputStream fileInputStream = new FileInputStream(excelPath);

                ExcelReader excelReader = new ExcelReader(fileInputStream, null, excelAnalysisEventListener);

                excelReader.read();

                // read完之后需要把listener 中的
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析excel
     * @param excelPaths
     * @return
     */
    public static void excel2ObjByEE(ExcelConfig excelConfig, String[] excelPaths, ExcelAnalysisEventListener excelAnalysisEventListener) {
        Assert.notNull(excelConfig.getTableName(), "表单名不能为null!");

        for (String excelPath : excelPaths) {
            // ..
            try {
                FileInputStream fileInputStream = new FileInputStream(excelPath);

                ExcelReader excelReader = new ExcelReader(fileInputStream, excelConfig, excelAnalysisEventListener);

                excelReader.read();

                // read完之后需要把listener 中的
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        需要有合并xml文件的功能，以及筛选xml文件的工具
        String[] filePaths = {"C:\\Users\\Administrator\\Documents\\WeChat Files\\wqszj123456\\FileStorage\\File\\2019-02\\20181211.xlsx"};

        ExcelConfig excelConfig = new ExcelConfig();
        excelConfig.setCreateTable(false);
        // 该参数为积累到多少条可以往数据库里插入
        excelConfig.setCount(25);
        excelConfig.setPrimaryKeys(new String[]{"SMP_ID", "CHR", "CHR_START", "TRANSCRIPT_REF"});
        excelConfig.setTableName("blood_data");
        ExcelAnalysisEventListener excelAnalysisEventListener = new ExcelAnalysisEventListener();
        // 数据库名是不能用数字的
        excel2ObjByEE(excelConfig, filePaths, excelAnalysisEventListener);

        DBUtils.close();
//        Map<String, Object> maps = excel2Obj(filePaths);
//        List<Map<String, String>> data = (List<Map<String, String>>)maps.get("data");
//        String[] keys = (String[])maps.get("keys");
////        写入到数据库中 这里还有另一种方法，navicat支持excel导入数据，还有其他很多格式，关键就是要手动
//        DBUtils.createTable(keys, "SMP_ID");
//
//        List<Map<String, String>> tmpData = new ArrayList<>(200);
//        for (int i = 0; i*200 < data.size(); i++) {
//            List<Map<String, String>> maps1;
//            if( i * 200 + 200 > data.size()) {
//                maps1 = tmpData.subList(i * 200, data.size() - 1);
//            } else {
//                maps1 = tmpData.subList(i * 200, i * 200 + 200);
//            }
//            tmpData.addAll(maps1);
//            DBUtils.insert(tmpData, keys);
//            tmpData.clear();
//        }
//
//        // 使用sax完成
//        InputStream sheet2=null;
//        OPCPackage pkg =null;
//
//        for (String filePath : filePaths) {
//            try {
//                pkg = OPCPackage.open(filePath);
//            } catch (InvalidFormatException e) {
//                e.printStackTrace();
//            }
//            XSSFReader r = null;
//            try {
//                r = new XSSFReader(pkg);
//            } catch (IOException | OpenXML4JException e) {
//                e.printStackTrace();
//            }
//            SharedStringsTable sst = null;
//            try {
//                sst = r.getSharedStringsTable();
//            } catch (IOException | InvalidFormatException e) {
//                e.printStackTrace();
//            }
//
//            XMLReader parser = null;
//            try {
//                parser = fetchSheetParser(sst);
//            } catch (SAXException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                sheet2 = r.getSheet("rId1");
//            } catch (IOException | InvalidFormatException e) {
//                e.printStackTrace();
//            }
//            InputSource sheetSource = new InputSource(sheet2);
//            try {
//                parser.parse(sheetSource);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            }
//            setRowContents(sheetHandler.getRowContents());
//        }
    }
}
