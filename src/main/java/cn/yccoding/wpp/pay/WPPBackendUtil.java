package cn.yccoding.wpp.pay;

import cn.yccoding.wpp.common.HttpClientUtil;
import cn.yccoding.wpp.config.bean.WCPConfigParamsYML;
import cn.yccoding.wpp.config.bean.WPPConfigParamsYML;
import cn.yccoding.wpp.service.IRedisService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

/**
 * @author : Chet
 * @description : 微信公众平台后端工具类
 * @date : 2019/11/5
 */
@Component
public class WPPBackendUtil {

    public static String accessToken;
    public static long expiresTime;
    @Autowired
    private WCPConfigParamsYML wcpConfigParams;
    @Autowired
    private WPPConfigParamsYML wppConfigParams;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private HttpClientUtil httpClientUtil;

    @Value("${redis.active}")
    private Boolean redisActive;

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
    }

    /**
     * 获取全局的access_token
     *
     * @param code
     *            用户唯一标识
     * @return
     */
    public String getAccessToken(String code) {
        if (!redisActive) {
            return getAccessTokenLocal();
        }
        String key = WPPConst.WECHAT_ACCESSTOKEN_PROFIX + code + WPPConst.WECHAT_ACCESSTOKEN_SUFFIX;
        String accessToken = redisService.get(key);
        if (accessToken != null) {
            return accessToken;
        }
        JSONObject obj = httpClientUtil.doGet(MessageFormat.format(WPPURL.BASE_ACCESS_TOKEN, wcpConfigParams.getAppId(), wcpConfigParams.getAppSecret()), JSONObject.class);
        String token = obj.getString("access_token"); // 凭据
        Long expiresIn = obj.getLong("expires_in"); // 有效期，单位秒
        if (StringUtils.isNotBlank(token)) {
            redisService.set(key, token, (expiresIn - 5 * 60L) * 1000); // 提前五分钟过期
            return token;
        }
        return null;
    }

    // 临时考虑存储在内存，后期使用redis
    public String getAccessTokenLocal() {
        // 当accessToken为null或者失效才重新去获取
        if (WPPBackendUtil.accessToken == null || LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() > expiresTime) {
            // 凭据)
            JSONObject obj = httpClientUtil.doGet(MessageFormat.format(WPPURL.BASE_ACCESS_TOKEN, wcpConfigParams.getAppId(), wcpConfigParams.getAppSecret()), JSONObject.class);
            // 凭据
            WPPBackendUtil.accessToken = obj.getString("access_token");
            // 有效期
            Long expires_in = obj.getLong("expires_in");
            // 设置凭据的失效时间 = 当前时间+有效期
            expiresTime = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() + ((expires_in - 60) * 1000);
        }
        return WPPBackendUtil.accessToken;
    }

    /**
     * 获取JSSDK的Ticket
     */
    public String getJsApiTicket(String accessToken) {
        // 发起请求到指定的接口
        JSONObject jsonObj = httpClientUtil.doGet(WPPURL.BASE_JSAPI_TICKET, JSONObject.class, accessToken);
        String ticket = jsonObj.getString("ticket");
        return ticket;
    }

    /**
     * 微信接入时签名校验
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        // 1 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = {wppConfigParams.getInterfaceToken(), timestamp, nonce};
        Arrays.sort(arr);
        // 2 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuffer sb = new StringBuffer();
        for (String s : arr)
            sb.append(s);
        String mySignature = SecurityUtil.SHA1(sb.toString());
        // 3 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return mySignature.equals(signature);
    }
}
