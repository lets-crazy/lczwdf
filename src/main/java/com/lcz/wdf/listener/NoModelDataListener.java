package com.lcz.wdf.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.lcz.wdf.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * TODO
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/19 17:45
 **/
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private final DataService dataService;
    private final String tableName;

    public NoModelDataListener(DataService dataService,String tableName) {
        this.dataService = dataService;
        this.tableName = tableName;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 300;

    List<Map<Integer, String>> results = new ArrayList<>();

    List<List<Object>> datas = new ArrayList<>();

    /**
     * 存储Key
     */
    Map<Integer, String> key = new HashMap<>();
    /**
     * keuList
     */
    List<String> keyList=new ArrayList<>();

    /**
     * 重写invokeHeadMap方法，获取表头，如果有需要获取第一行表头就重写这个方法，不需要则不需要重写
     *
     * @param headMap Excel每行解析的数据为Map<Integer, String>类型，Integer是Excel的列索引,String为Excel的单元格值
     * @param context context能获取一些东西，比如context.readRowHolder().getRowIndex()为Excel的行索引，表头的行索引为0，0之后的都解析成数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());
        Set<Integer> integerSet = headMap.keySet();
        for (Integer integer : integerSet) {
            keyList.add(headMap.get(integer));
        }
        key.putAll(headMap);
    }


    /**
     * 读取数据
     * @param data 传入数据
     * @param context 读取数据
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        results.add(data);
        if (results.size() >= BATCH_COUNT) {
            results.forEach(
                    m -> {
                        List<String> values = new ArrayList<>(m.values());
                        datas.add(values);
                    });
            saveData();
            results.clear();
        }

        data.forEach(
                m -> {
                    List<String> values = new ArrayList<>(m.values());
                    readResult.add(values);
                });

        /*HashMap<String, Object> objectObjectHashMap = new HashMap<>(16);
        Set<Integer> integerSet = data.keySet();
        String id=null;
        for (Integer integer : integerSet) {
            String s = key.get(integer);
            if("id".equals(s)){
                id = data.get(integer);
                if (StringUtils.isNotBlank(id)) {
                    Double f = Double.valueOf(id);
                    Integer a = (int)Math.ceil(f);
                    objectObjectHashMap.put(s,a);
                }else {
                    objectObjectHashMap.put(s,null);
                }
            }else{
                objectObjectHashMap.put(s,data.get(integer));
            }

        }
        this.data.add(objectObjectHashMap);*/

        // 分批次 插入数据库 可以使用 暂时不适用 使用的 可以开启
        //list.add(data);
//        if (list.size() >= BATCH_COUNT) {
//            saveData();
//            list.clear();
//        }
    }

    /**
     * 读取完成后 保存数据
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库 添加 以及更新
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", data.size());
        dataService.addData(data, tableName,keyList);
        log.info("存储数据库成功！");
    }

    public List<Map<Integer, String>> getDatas() {
        return null;
    }

    public void setDatas(List<Map<Integer, String>> datas) {
        //this.list = datas;
    }

}

