package com.polystone.test.gaia;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.polystone.tools.common.HttpUtil;
import com.polystone.tools.common.MD5Util;
import com.polystone.tools.common.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Z.K
 * @FileName: Gaia
 * @DateTime: 2019/8/21 0021
 * @Version 1.0
 * @Description: 盖亚
 */
public class Gaia {

    private static String customerId = "d9ed53ff53bd4244a1ec1847f6b603d1" ;
    private static String customerSecret = "7d6732f551ed49cba374ee621f5b0843";
    private static String host = "http://devapis.natapp1.cc/blade-gaia";

    private static String normal = "normal";
    private static String abnormal = "abnormal";
    private static String invalid = "invalid";
    private static String payOk = "payOk";
    private static String payFailed = "payFailed";

    /**
     * 盖亚回调
     * 2019-08-27 16:14:16.978 INFO  PayLog Line:27  - #GAIA结果通知#**开始***
     2019-08-27 16:14:16.979 INFO  PayLog Line:27  - #收到消息:#Parameters:{}
     InputStream:{
     "action":"orderStatusChanged",
     "data":{
     "orderIdOuter": "test001",
     "orderStatus": "abnormal",
     "description": "异常"
     },
     "userData": "data"
     }
     导入交易结果==>{"code":1,"msg":"交易重复! key=atf:cache:order:201908271523166260"}

     2019-08-27 16:15:25.535 INFO  PayLog Line:27  - #GAIA结果通知#**开始***
     2019-08-27 16:15:25.536 INFO  PayLog Line:27  - #收到消息:#Parameters:{}
     InputStream: {
     "action":"balanceUnderThreshold",
     "data":{
     "balanceThreshold": 100000,
     "availableBalance": 10000
     }
     }
     2019-08-27 16:15:41.886 INFO  PayLog Line:27  - #GAIA结果通知#**开始***
     2019-08-27 16:15:41.886 INFO  PayLog Line:27  - #收到消息:#Parameters:{}
     InputStream:    {
     "action":"notifyMessage",
     "data":{
     "orderIdOuter": "order_id_1234",
     "type": "sms",
     "title": "title content",
     "content":"msg content",
     "phoneNo": "13913xxxxxx"
     },
     "userData":"customer defined data"
     }

     * @param args
     */
    public static void main(String[] args) {
//        GaiaResult<BigDecimal> ret = getBalance();
//        System.out.println(JSON.toJSONString(ret));
//        System.out.println(ret.getData().compareTo(BigDecimal.ZERO));

//        createOrders();

//        GaiaResult<PageDTO<Map<String,Object>>> ret = getOrderList(invalid,"2019-08-22 00:00:00","2019-08-28 00:00:00",1,111);

        //{"code":0,"msg":"操作成功","success":true,"data":[{"workerIdOuter":"ACT0001","orderIdOuter":"N00004"}]}
//        List<String> orderIdOuterList = new ArrayList<>();
//        orderIdOuterList.add("N00004");
//        GaiaResult ret1 = auditOrders(orderIdOuterList,normal);

        GaiaResult<String> ret2 = downloadOrders("2019-08-22 00:00:00","2019-08-28 00:00:00");
        System.out.println(ret2.getData());
    }

    public static void createOrders(){
        List<GaiaOrderPO> orderList = new ArrayList<>();
        GaiaOrderPO order = new GaiaOrderPO();
        order.setOrderIdOuter("N00007");
        order.setAmount(new BigDecimal("0.01"));
        order.setMemo("提现测试N00001");
        order.setWorkerIdOuter("ACT0001");
        order.setWorkerName("张三");
        order.setWorkerPhoneNo("15200000001");
        order.setWorkerEmailAddr("123@163.com");//收款方电子邮件地址，如果没有此项，值为：""
        order.setWorkerPersonId("152104199204250914");    //收款方身份证号码，用于二次确认
        order.setWorkerBankName("工商银行");
        order.setWorkerBankCardNo("6228481268248914675");
        order.setUserDataOuter("{\"accountNo\":\"aaabbbccc\"}");
        orderList.add(order);

        GaiaOrderPO order2 = new GaiaOrderPO();
        order2.setOrderIdOuter("N00008");
        order2.setAmount(new BigDecimal("0.01"));
        order2.setMemo("提现测试N00001");
        order2.setWorkerIdOuter("ACT0001");
        order2.setWorkerName("张三");
        order2.setWorkerPhoneNo("15200000001");
        order2.setWorkerEmailAddr("");//收款方电子邮件地址，如果没有此项，值为：""
        order2.setWorkerPersonId("1521041992042509141");    //收款方身份证号码，用于二次确认
        order2.setWorkerBankName("工商银行");
        order2.setWorkerBankCardNo("62284812682489146751");
        order2.setUserDataOuter("{\"accountNo\":\"aaabbbccc\"}");

        orderList.add(order2);


        GaiaResult<List<Map<String,String>>> ret = addOrders(orderList);
        System.out.println("---------------------------------");
        if(ret.getCode()==1){
            System.out.println(ret.getMessage());
        }else{
            for(Map<String,String> r:ret.getData()){
                System.out.println(r);
            }
        }
    }




    /**
     * 获取余额
     * {"code":0,"success":true,"data":{"balanceThreshold":0.00,"availableBalance":""},"msg":"操作成功"}
     * @return
     */
    private static GaiaResult<BigDecimal> getBalance(){
        String url = "/v1/getBalance";
        String body = "";
        GaiaResult<JSONObject> ret = parseRet(HttpUtil.doPost(host+url,headers(body),body));
        if(ret.getCode()!=0)return new GaiaResult(ret.getCode(),ret.getMessage(),null);
        return new GaiaResult(0,ret.getMessage(),ret.getData().getBigDecimal("availableBalance"));
    }

    /**
     * 新增订单
     * @return
     */
    private static GaiaResult<List<Map<String,String>>> addOrders(List<GaiaOrderPO> orderList){
        String url = "/v1/addOrders";
        Map<String,Object> data = new HashMap<>();
        data.put("orderList",orderList);
        String body = JSON.toJSONString(data);
        GaiaResult<JSONObject> ret = parseRet(HttpUtil.doPost(host+url,headers(body),body));
        if(ret.getCode()!=0)return new GaiaResult(ret.getCode(),ret.getMessage(),null);
        List<Map<String,String>> dlist = new ArrayList<>();
        JSONObject obj = ret.getData();
        JSONArray list = obj.getJSONArray("orderList");
        if(list!=null && list.size()>0){
            for(int i = 0 ; i < list.size() ; i++){
                JSONObject row = list.getJSONObject(i);
                if(row!=null){
                    Map<String,String> map = new HashMap<>();
                    map.put("orderIdOuter",row.getString("orderIdOuter"));
                    map.put("workerIdOuter",row.getString("workerIdOuter"));
                    dlist.add(map);
                }
            }
        }
        return new GaiaResult(0,ret.getMessage(),dlist);
    }

    /**
     * 获取订单列表
     * status :  abnormal,invalid,payOk,payFailed
     * beginTime : YYYY-MM-DD HH:mm:ss
     * endTime : YYYY-MM-DD HH:mm:ss
     * @return
     */
    private static GaiaResult<PageDTO<Map<String,Object>>> getOrderList(String status,String beginTime,String endTime,int pageIndex,int pageSize){
        String url = "/v1/getCustomerOrderList";
        Map<String,Object> data = new HashMap<>();
        data.put("pageIndex",pageIndex<=0 ? 1 : pageIndex);//页数index，从1开始
        data.put("pageSize",pageSize<=0 || pageSize>200 ? 10 : pageSize);//每页个数，取值范围：> 0 && <= 200
        data.put("orderStatus",status);//订单状态，可取值：abnormal -> 异常订单（金额超过预警）；invalid -> 无效订单（经客户确认无效的订单）
        Map<String,Object> condition = new HashMap<>();
        Map<String,Object> createdAt = new HashMap<>();
        createdAt.put("begin",beginTime);//查询生成开始时间 YYYY-MM-DD HH:mm:ss
        createdAt.put("end",endTime);//查询生成结束时间 YYYY-MM-DD HH:mm:ss
        condition.put("createdAt",createdAt);
        data.put("conditions",condition);//查询条件，如果查询条件为空(即：[])，则获取所有用工人员
        String body = JSON.toJSONString(data);
        GaiaResult<JSONObject> ret = parseRet(HttpUtil.doPost(host+url,headers(body),body));
        if(ret.getCode()!=0)return new GaiaResult(ret.getCode(),ret.getMessage(),null);
        JSONObject obj = ret.getData();
        PageDTO<Map<String,Object>> page = new PageDTO<>();
        page.setPageSize(obj.getInteger("pageSize"));
        page.setPageNum(obj.getInteger("pageIndex"));
        page.setTotal(obj.getInteger("totalOrders"));
        List<Map<String,Object>> dlist = new ArrayList<>();
        JSONArray list = obj.getJSONArray("orderList");
        if(list!=null && list.size()>0){
            for(int i = 0 ; i < list.size() ; i++){
                JSONObject row = list.getJSONObject(i);
                if(row!=null){
                    Map<String,Object> map = new HashMap<>();
                    map.put("orderIdOuter",row.getString("orderIdOuter"));
                    map.put("workerIdOuter",row.getString("workerIdOuter"));
                    dlist.add(map);
                }
            }
        }
        page.setList(dlist);
        return new GaiaResult(0,ret.getMessage(),page);
    }

    /**
     * 审核待审核订单
     * orderIdOuterList 客户自定义订单号
     * @return
     */
    private static GaiaResult auditOrders(List<String> orderIdOuterList,String status){
        String url = "/v1/auditOrdersByCustomer";
        Map<String,Object> data = new HashMap<>();
        data.put("orderIdOuterList",orderIdOuterList);//客户自定义订单Id列表，不可以为空
        data.put("auditResult",status);//审核结果，可选值：正常 normal，异常 abnormal
        String body = JSON.toJSONString(data);
        return parseRet(HttpUtil.doPost(host+url,headers(body),body));
    }

    /**
     * 导出订单报告
     * @return
     */
    private static GaiaResult<String> downloadOrders(String beginTime,String endTime){
        String url = "/v1/downloadOrderReport";
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> createdAt = new HashMap<>();
        createdAt.put("begin",beginTime);//查询生成开始时间 YYYY-MM-DD HH:mm:ss
        createdAt.put("end",endTime);//查询生成结束时间 YYYY-MM-DD HH:mm:ss
        data.put("createdAt",createdAt);//
        String body = JSON.toJSONString(data);
        GaiaResult<JSONObject> ret = parseRet(HttpUtil.doPost(host+url,headers(body),body));
        if(ret.getCode()!=0)return new GaiaResult(ret.getCode(),ret.getMessage(),null);
        return new GaiaResult(0,ret.getMessage(),ret.getData().getString("orderReportUrl"));
    }

    private static Map<String,String> headers(String body){
        String time = (System.currentTimeMillis()/1000)+"";
        String nonce = getNonce();
        String digest = MD5Util.MD5Encode(customerId + body + time + nonce + customerSecret + "GAIAWORKS","UTF-8").toLowerCase().substring(8, 24);
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("customerId",customerId);
        headers.put("timestamp",time);
        headers.put("nonce",nonce);
        headers.put("digest",digest);
        return headers;
    }

    private static GaiaResult<JSONObject> parseRet(String ret){
        System.out.println(ret);
        if(StringUtil.isTrimEmpty(ret))return new GaiaResult(1,"请求失败",null);
        JSONObject retObj = JSON.parseObject(ret);
        if(retObj.getIntValue("code")!=0){
            return new GaiaResult(1,retObj.getString("message"),null);
        }
        return new GaiaResult(0,retObj.getString("message"),retObj.getJSONObject("data"));
    }

    private static String getNonce(){
        return RandomStringUtils.randomNumeric(6);
    }


}
