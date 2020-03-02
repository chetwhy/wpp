package cn.yccoding.wpp.config.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : Chet
 * @description : 微信公众平台配置的参数
 * @date : 2019/11/1
 */
@Data
@Component
public class WPPConfigParamsYML {

    // 公众号id
    @Value("${wechat.wpp.APP_ID}")
    private String appId;

    // app密钥
    @Value("${wechat.wpp.APPSECRET}")
    private String appSecret;

    // 微信接入——接口配置信息
    @Value("${wechat.wpp.INTERFACE_URL}")
    private String interfaceUrl;

    // 微信接入——接口配置信息
    @Value("${wechat.wpp.INTERFACE_TOKEN}")
    private String interfaceToken;

    // js安全域名
    @Value("${wechat.wpp.JS_DOMAIN}")
    private String jsDomain;

    // 网页授权域名
    @Value("${wechat.wpp.WEB_AUTH_DOMAIN}")
    private String webAuthDomain;

}
