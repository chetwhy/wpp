package cn.yccoding.wpp.pay;

import cn.yccoding.wpp.config.WCPConfigParams;
import cn.yccoding.wpp.service.IRedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author : Chet
 * @description : 微信公众平台后端工具类
 * @date : 2019/11/5
 */
@Component
public class WPPBackendUtil {

    @Autowired
    private RestTemplate restTemplateSingle;

    @Autowired
    private WCPConfigParams wcpConfigParams;

    public static String accessToken;
    public static long expiresTime;

    @Autowired
    private IRedisService redisService;

    /**
     * 获取全局的access_token
     * @param code 用户唯一标识
     * @return
     */
    public String getAccessTokenInRedis(String code) {
        String key = WPPConst.WECHAT_ACCESSTOKEN_PROFIX + code + WPPConst.WECHAT_ACCESSTOKEN_SUFFIX;
        String accessToken = redisService.get(key);
        if (accessToken != null) {
            return accessToken;
        }

        JSONObject obj = restTemplateSingle.getForObject(MessageFormat.format(WPPURL.BASE_ACCESS_TOKEN, wcpConfigParams.getAppId(), wcpConfigParams.getAppSecret()), JSONObject.class);
        String token = obj.getString("access_token"); //凭据
        Long expiresIn = obj.getLong("expires_in"); //有效期，单位秒
        if (StringUtils.isNotBlank(token)) {
            redisService.set(key, token, (expiresIn-5*60L)*1000); // 提前五分钟过期
            return code;
        }
        return null;
    }

    // 临时考虑存储在内存，后期使用redis
    public String getAccessToken() {
        //当accessToken为null或者失效才重新去获取
        if(WPPBackendUtil.accessToken ==null||new Date().getTime()>expiresTime) {
            JSONObject obj = restTemplateSingle.getForObject(MessageFormat.format(WPPURL.BASE_ACCESS_TOKEN, wcpConfigParams.getAppId(), wcpConfigParams.getAppSecret()), JSONObject.class);
            //凭据
            WPPBackendUtil.accessToken = obj.getString("access_token");
            //有效期
            Long expires_in = obj.getLong("expires_in");
            //设置凭据的失效时间 = 当前时间+有效期
            expiresTime = new Date().getTime() + ((expires_in - 60) * 1000);
        }
        return WPPBackendUtil.accessToken;
    }


    /**
     * 获取JSSDK的Ticket
     */
    public String getJsApiTicket(String accessToken){
        //发起请求到指定的接口
        String obj = restTemplateSingle.getForObject(WPPURL.BASE_JSAPI_TICKET, String.class, accessToken);
        JSONObject jsonObj = JSON.parseObject(obj);
        String ticket = jsonObj.getString("ticket");
        return ticket;
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
        System.out.println(new Date().getTime());
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(milliSecond);

    }
}
