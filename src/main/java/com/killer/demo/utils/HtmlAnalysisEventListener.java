package com.killer.demo.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wqs
 * @description 只能针对于某个功能
 * @date 2019/3/1 0001-下午 16:27
 */
public class HtmlAnalysisEventListener extends AnalysisEventListener {
    private List<String> keys = new ArrayList<>();

    private int count = 0;

    private List<Map<String, String>> container = new ArrayList<>();

    private static final String PREFIX = "http://www.nrc.ac.cn:9090";

    // 匹配data : [ 以[结尾， 页面上只有一个匹配
    private static final Pattern START_POSITION = Pattern.compile("data : \\[$");

    private static final Pattern END_POSITION = Pattern.compile("\\],");

//    private static final Pattern HERP_TARGET_KEY = Pattern.compile("yc_TCM_Herb_Name_Pinyin");

    private static final Pattern HERP_TARGET_ = Pattern.compile("href=\"(.*)\"");

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        ExcelConfig custom = (ExcelConfig)analysisContext.getCustom();
        HashMap<String, String> data = new HashMap<>();
        try {
            URL url = new URL(PREFIX + String.valueOf(o));
            URLConnection urlConnection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream(), "utf8"));

            StringBuilder stringBuilder = new StringBuilder("[");
            String line;
            boolean find = false;
            while ((line = reader.readLine()) != null) {
                if (!find) {
                    Matcher m = START_POSITION.matcher(line);
                    if (m.find()) {
                        // 如果找到则开始存储
                        find = true;
                    }
                } else {
                    // 拼接数据
                    Matcher m = END_POSITION.matcher(line);
                    if (!m.find()) {
                        // 需要处理数据
                        stringBuilder.append(line);
                    } else {
                        stringBuilder.append("]");
                        break;
                    }
                }
            }

            if (!find) {
                System.out.println("该页面没有找到数据！");
            }

            // 从获得的数据中获取子页面链接，要不要转为excel文件
            // 读取数据
            JSONArray result = JSONArray.parseArray(stringBuilder.toString());

            for (int index = 0; index < result.size(); index++) {
                JSONObject jsonObject = result.getJSONObject(index);
//                这并不是json，只是普通的js对象
//                {"ID": "<div id='L771' >Herb Name in Chinese</div>","Item Name": "<div style='max-height: 200px;overflow: auto'>阿魏</div>",},
//                {"ID": "<div id='L772' >Herb Name in Pinyin</div>","Item Name": "<div style='max-height: 200px;overflow: auto'>A WEI</div>",},
//                {"ID": "<div id='L773' >Herb Name in Ladin</div>","Item Name": "<div style='max-height: 200px;overflow: auto'><i>Ferulae Resina</i></div>",},
//                {"ID": "<div id='L774' >Type</div>","Item Name": "<div style='max-height: 200px;overflow: auto'>Plant medicine</div>",},
//                {"ID": "<div id='L775' >Description in Chinese</div>","Item Name": "<div style='max-height: 200px;overflow: auto'>伞形科</div>",},
//                {"ID": "<div id='L776' >Description in English</div>","Item Name": "<div style='max-height: 200px;overflow: auto'>Apiaceae</div>",},

                // 插入数据库
                String id = jsonObject.getString("ID");
                Document document = Jsoup.parse(id);
                Element body = document.body();
                Element child = body.child(0);
                String text = child.text();

                keys.add(text);

                String itemName = jsonObject.getString("Item Name");
                Document document1 = Jsoup.parse(itemName);
                Element body1 = document.body();
                Element child1 = body.child(0);
                String text1 = child1.text();

                data.put(text, text1);
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if(count == container.size()) {
            DBUtils.insertIntoByMapBatch(container, (String[])keys.toArray(), custom.getTableName());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public static void main(String[] args) {
        String[] filePath = {};
        ExcelUtils.excel2ObjByEE(filePath, new HtmlAnalysisEventListener());

        Document parse = Jsoup.parse("<div id='L771' >Herb Name in Chinese</div>");
    }
}
