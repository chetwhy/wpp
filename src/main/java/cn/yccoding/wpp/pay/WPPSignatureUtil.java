package cn.yccoding.wpp.pay;

import cn.yccoding.wpp.sdk.WXPayConstants;
import cn.yccoding.wpp.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author : Chet
 * @description : 微信公众平台签名工具类
 * @date : 2019/11/5
 */
@Component
public class WPPSignatureUtil {

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    /**
     * 签名校验
     *
     * @param nonceStr
     * @param url
     * @param prepayId
     * @param key
     * @return
     * @throws Exception
     */
    public Map<String, Object> permissionValidate(String AppID, String nonceStr, String url, String prepayId,
                                                  String key,String jsapiTicket) throws Exception {
        // jssdk权限验证参数
        TreeMap<Object, Object> param = new TreeMap<>();
        Map<String, Object> data = new HashMap<>();
        param.put("appId", AppID);
        String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
        param.put("timestamp", timestamp);// 全小写
        param.put("nonceStr", nonceStr);
        param.put("signature", getSignature(timestamp, nonceStr, url,jsapiTicket));
        data.put("configMap", param);

        // 微信支付权限验证参数
        Map<String, String> payMap = new HashMap<>();
        payMap.put("appId", AppID);
        payMap.put("timeStamp", timestamp);// 驼峰
        payMap.put("nonceStr", nonceStr);
        payMap.put("package", "prepay_id=" + prepayId);
        payMap.put("signType", WXPayConstants.SignType.MD5.toString());
        payMap.put("paySign", WXPayUtil.generateSignature(payMap, key));
        payMap.put("packageStr", "prepay_id=" + prepayId);
        data.put("payMap", payMap);

        return data;
    }

    /**
     * 计算jssdk-config的签名
     * @param timestamp
     * @param noncestr
     * @param url
     * @return
     */
    public String getSignature(String timestamp,String noncestr,String url,String jsapiTicket){
        //对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String,Object> map = new TreeMap<>();
        map.put("jsapi_ticket",jsapiTicket);
        map.put("timestamp",timestamp);
        map.put("noncestr",noncestr);
        map.put("url",url);
        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1
        StringBuilder sb = new StringBuilder();
        Set<String> set = map.keySet();
        for (String key : set) {
            sb.append(key+"="+map.get(key)).append("&");
        }
        //去掉最后一个&符号
        String temp = sb.substring(0,sb.length()-1);
        //使用sha1加密
        return SecurityUtil.SHA1(temp);
    }
}
