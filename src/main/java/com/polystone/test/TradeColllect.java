package com.polystone.test;

import com.alibaba.fastjson.JSON;
import com.polystone.tools.common.HttpUtil;
import com.polystone.tools.common.MD5Util;
import com.polystone.tools.common.StringUtil;
import com.polystone.tools.security.SecurityUtil;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * TODO 〈一句话功能简述〉
 *
 * @author Administrator
 * @version V1.0, 2018/10/9 0009
 * @copyright
 */
public class TradeColllect {

    public static void main(String[] args) {
        String salt = "BnExnJd8UFyGd0bSEyNklPI3aaOQyttf";
        //termSn
        String jsonData = "{\"orderNo\":\"201906191523115347\",\"tradeTime\":\"20190617100453\",\"tradeAmt\":\"100\",\"stlmDate\":\"20190510\",\"tradeType\":\"0\",\"trademode\":\"2\",\"tradeStatus\":\"1\",\"rspCode\":\"应答码\",\"cardType\":\"1\",\"mchtCd\":\"ACT190414100007\",\"termSn\":\"11910110549504305\",\"terminalNo\":\"1100100101\",\"tradeFirstFlag\":\"2\",\"method\":\"TradeInformation\",\"feeAmt\":\"3\",\"isVipTrade\":\"2\",\"tsFlag\":\"2\",\"d0FeeAmt\":\"0.00\",\"vipType\":\"03\",\"regDate\":\"20190510\"}";
        String checkValue = SecurityUtil.getInstance().getSHA256Str(jsonData+salt);

        Map<String,String> params = new HashMap<>();
        params.put("jsonData", jsonData);
        params.put("checkValue", checkValue);
        System.out.println(params);
        String ret = HttpUtil.doPost("http://remittance.51polystone.com/collect/public/auto/allinpay/trade", params);
        System.out.println(ret);
    }


//    public static void main(String[] args) throws Exception {
//        System.out.println("开始了");
//        int i = 0 ;
//        String path = "D:\\new.txt";
//        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
//        while(reader.ready()) {
//            String jsonData = reader.readLine();
//            if(jsonData==null || jsonData.trim().length()==0)continue;
//
//            String checkValue = SecurityUtil.getInstance().getSHA256Str(jsonData+"YUHTRPSUCSHJNN78");
//            Map<String,String> params = new HashMap<>();
//            params.put("jsonData", jsonData);
//            params.put("checkValue",checkValue);
//            System.out.println(params);
//            String ret = HttpUtil.doPost("http://remittance.51polystone.com/collect/public/auto/freedomsdb/trade", params);
//            System.out.println(ret);
//            i++;
//        }
//        System.out.println("结束了,"+i);
//    }


//    public static void main(String[] args) throws Exception{
//        System.out.println("开始了");
//        String[] keys = new String[]{"certificateSonMail","transSeq","hpMerCode","hpMerLv","inviteType"
//                ,"transType","transTime","transAmt","transStatus"};
//
//        int num = 0 ;
//        String path = "D:\\noncardTrade.txt";
//        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
//        while(reader.ready()) {
//            String line = reader.readLine();
//            line = line.trim().replace(" ","");
//            line = line.replace("\t","");
//            //4.组装数据包
//            String[] val = line.split(",");
//            Map<String,Object> info = new HashMap<>();
//            for(int i = 0 ; i < keys.length ; i++){
//                if("空值".equals(val[i])){
//                    info.put(keys[i],null);
//                }else{
//                    info.put(keys[i],val[i]);
//                }
//            }
//            info.put("from","xwt");
//            info.put("method", "TradeInfo");
//
//            String jsonData = JSON.toJSONString(info);
//            jsonData = SecurityUtil.getInstance().encryptAES(jsonData,"ganjuerenshengdaodalegaochao!");
//
//            Map<String,String> params = new HashMap<>();
//            params.put("jsonData", jsonData);
//            params.put("sign", MD5Util.MD5Encode(jsonData+"haohaiyo!","UTF-8"));
//            System.out.println(params);
//            String ret = HttpUtil.doPost("http://remittance.51polystone.com/collect/public/xwt/test", params);
//            System.out.println(ret);
//            num++;
//        }
//        System.out.println("结束了,"+num);
//    }



//    public static void main(String[] args) {
//        try {
//            FileInputStream fileInputStream = new FileInputStream(new File("D:/JSGJSH_190121.csv"));
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream,"GBK");
//            updateMerchant(new BufferedReader(inputStreamReader, 4 * 1024 * 1024));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private static void updateMerchant(BufferedReader input) throws IOException {
        String[] keys = new String[] {"certificateSonMail","hpMerCode","merName","merStatus","merMobile","hpUserName","merCreateTime","productCode"};
        int num = 0;
        int batchNo = 0;
        String line = null;
        List<NoncardMerchant> list = new ArrayList<>(200);
        while((line = input.readLine()) != null){
            if(StringUtil.isTrimEmpty(line)) {
                continue;
            }
            if(++num==1){//取第一行字段名称
                continue;
            }
            line = line.trim().replace(" ","");
            line = line.replace("\t","");
            //5.组装数据包
            String[] val = line.split(",");
            Map<String,String> info = new HashMap<>();
            for(int i = 0 ; i < keys.length ; i++){
                if(StringUtil.isTrimEmpty(val[i]) || "空值".equals(val[i])) {
                    info.put(keys[i],null);
                }else {
                    info.put(keys[i],val[i]);
                }
            }
            list.add(NoncardMerchant.build(info));
            if(list.size()==200) {
                //6.入库
                saveMerchant(list,++batchNo);
                list = new ArrayList<>(200);
            }
        }
        //6.入库
        saveMerchant(list,++batchNo);
    }

    private static int a = 0;

    private static void saveMerchant(List<NoncardMerchant> list,int i){
        System.out.println(i);
        for(NoncardMerchant nm:list){
            System.out.println((++a+":")+nm.toString());

        }
    }


//    public static void main(String[] args) {
//        String line = "1243628837@qq.com,XWT@XW181224094930892,,红狼网络,审核通过,20181224095727\t,XWT\t,\t,";
//        line = line.trim().replace(" ","");
//        line = line.replace("\t","");
//        //5.组装数据包
//        String[] vals = line.split(",");
//        for(String val : vals){
//            System.out.println(val);
//
//        }
//        System.out.println(vals.length);
//    }
}
