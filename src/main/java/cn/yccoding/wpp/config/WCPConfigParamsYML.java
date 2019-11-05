package cn.yccoding.wpp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author : Chet
 * @description : 微信配置信息工具类
 * @date : 2019/10/11
 */
@Component
@Data
public class WCPConfigParamsYML {

    @Value("${wechat.wcp.APP_ID}")
    private String appId;

    @Value("${wechat.wcp.APPSECRET}")
    private String appSecret;

    @Value("${wechat.wcp.MCH_ID}")
    private String muchId;

    @Value("${wechat.wcp.API_KEY}")
    private String apiKey;

    @Value("${wechat.wcp.REGISTER_DOMAIN}")
    private String registerDomain;

    @Value("${wechat.wcp.JSAPI_PAYMENT_AUTH_DIR}")
    private String jsapiPaymentAuthDir;

    @Value("${wechat.wcp.JS_DOMAIN}")
    private String jsDomain;

    @Value("${wechat.wcp.WEB_AUTH_DOMAIN}")
    private String webAuthDomain;

    @Value("${wechat.wcp.APICLIENT_CERT}")
    private String apiclientCert;

    @Value("${wechat.wcp.NOTIFY_URL}")
    private String notifyUrl;

    @Value("${wechat.wcp.NOTIFY_URL_REFUND}")
    private String notifyUrlRefund;
}
