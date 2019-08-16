//package com.polystone.test.allinpay;
//
//import com.alibaba.fastjson.JSONObject;
//import com.polystone.tools.common.HttpUtil;
//import com.polystone.tools.security.SecurityUtil;
//import org.springframework.util.StringUtils;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
///**
// * tools
// *
// * @author xiaoming
// * @date 2019/8/7
// */
//public class AllinPayCollect {
//    /**
//     * 过滤已处理交易，放到trade.txt里面
//     * @param args
//     * @throws Exception
//     */
//    public static void main3(String[] args) throws Exception {
//        String readPath = "/Users/xiaoming/source/jushi/tools/src/main/java/com/polystone/test/allinpay/20190804.txt";
//        String writePath = "/Users/xiaoming/source/jushi/tools/src/main/java/com/polystone/test/allinpay/trade.txt";
//        String filterPath = "/Users/xiaoming/source/jushi/tools/src/main/java/com/polystone/test/allinpay/filter_order_no.txt";
//
//        Set<String> filter = new HashSet<>();
//        BufferedReader filterReader = new BufferedReader(new FileReader(new File(filterPath)));
//        while(filterReader.ready()) {
//            String data = filterReader.readLine();
//            filter.add(trim(data));
//        }
//        filterReader.close();
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(writePath)));
//        BufferedReader readReader = new BufferedReader(new FileReader(new File(readPath)));
//        while(readReader.ready()) {
//            String data = readReader.readLine();
//            filter.add(trim(data));
//            String[] dataArray = data.split(",");
//            if (filter.contains(dataArray[3])) {
//                continue;
//            }
//            writer.write(data);
//            writer.newLine();
//        }
//        readReader.close();
//        writer.flush();
//        writer.close();
//    }
//
//    /**
//     * 读取trade_all.txt里面的交易，发送请求
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception {
//        String salt = "BnExnJd8UFyGd0bSEyNklPI3aaOQyttf";
//        String path = "/Users/xiaoming/source/jushi/tools/src/main/java/com/polystone/test/allinpay/trade_all.txt";
//        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
//        int lineIndex = 0;
//        while(reader.ready()) {
//            String data = reader.readLine();
//            String[] dataArray = data.split(",");
//            Map dataMap = new HashMap(64);
//            int index = 0;
//            dataMap.put("method", "TradeInformation");
//            dataMap.put("transNum", trim(dataArray[index++]));
//            dataMap.put("keyRsp", trim(dataArray[index++]));
//            dataMap.put("extSeqId", trim(dataArray[index++]));
//            dataMap.put("orderNo", trim(dataArray[index++]));
//            dataMap.put("tradeTime", trim(dataArray[index++]));
//            dataMap.put("stlmDate", trim(dataArray[index++]));
//            dataMap.put("tradeAmt", trim(dataArray[index++]));
//            dataMap.put("tradeType", trim(dataArray[index++]));
//            dataMap.put("trademode", trim(dataArray[index++]));
//            dataMap.put("tradeStatus", trim(dataArray[index++]));
//            dataMap.put("rspCode", trim(dataArray[index++]));
//            dataMap.put("cardType", trim(dataArray[index++]));
//            dataMap.put("mchtCd", trim(dataArray[index++]));
//            dataMap.put("terminalNo", trim(dataArray[index++]));
//            dataMap.put("tradeFirstFlag", trim(dataArray[index++]));
//            dataMap.put("isVipTrade", trim(dataArray[index++]));
//            dataMap.put("feeAmt", trim(dataArray[index++]));
//            dataMap.put("d0FeeAmt", trim(dataArray[index++]));
//            dataMap.put("vipType", trim(dataArray[index++]));
//            dataMap.put("regDate", trim(dataArray[index++]));
//            dataMap.put("tsFlag", trim(dataArray[index++]));
//            dataMap.put("termSn", trim(dataArray[index++]));
//            System.out.println("第" + (++lineIndex) + "条数据:");
//            String jsonData = JSONObject.toJSONString(dataMap);
//            Map<String,String> params = new HashMap<>(2);
//            String checkValue = SecurityUtil.getInstance().getSHA256Str(jsonData + salt);
//            params.put("jsonData", jsonData);
//            params.put("checkValue", checkValue);
//            System.out.println(params);
//            String ret = HttpUtil.doPost("http://remittance.51polystone.com/collect/public/auto/allinpay/trade", params);
//            System.out.println(ret);
//        }
//        reader.close();
//    }
//
//    private static String trim(String str) {
//        return StringUtils.trimTrailingWhitespace(StringUtils.trimLeadingWhitespace(str));
//    }
//}
