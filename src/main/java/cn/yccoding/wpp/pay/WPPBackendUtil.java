package cn.yccoding.wpp.pay;

import cn.yccoding.wpp.config.WCPConfigParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author : Chet
 * @description : 微信公众平台后端工具类
 * @date : 2019/11/5
 */
@Component
public class WPPBackendUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WCPConfigParams wcpConfigParams;

    public static String accessToken;
    public static long expiresTime;

    // 临时考虑存储在内存，后期使用redis
    public String getAccessToken() {
        //当accessToken为null或者失效才重新去获取
        if(accessToken==null||new Date().getTime()>expiresTime) {
            JSONObject obj = restTemplate.getForObject(MessageFormat.format(WPPURL.BASE_ACCESS_TOKEN, wcpConfigParams.getAppId(), wcpConfigParams.getAppSecret()), JSONObject.class);
            //凭据
            accessToken = obj.getString("access_token");
            //有效期
            Long expires_in = obj.getLong("expires_in");
            //设置凭据的失效时间 = 当前时间+有效期
            expiresTime = new Date().getTime() + ((expires_in - 60) * 1000);
        }
        return accessToken;
    }


    /**
     * 获取JSSDK的Ticket
     */
    public String getJsApiTicket(String accessToken){
        //发起请求到指定的接口
        String obj = restTemplate.getForObject(WPPURL.BASE_JSAPI_TICKET, String.class, accessToken);
        JSONObject jsonObj = JSON.parseObject(obj);
        String ticket = jsonObj.getString("ticket");
        return ticket;
    }
}
