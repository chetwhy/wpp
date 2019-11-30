package cn.yccoding.wpp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author : Chet
 * @description : 微信公众平台配置类
 * @date : 2019/11/21
 */
@Configuration
@PropertySource("classpath:config/wechat-platform-chet-test.properties")
public class WPPConfig {

    @Autowired
    private Environment env;

    @Bean
    public WPPConfigParams wppConfigParams() {
        WPPConfigParams params = new WPPConfigParams();
        params.setAppId(env.getProperty("wpp.APP_ID"));
        params.setAppSecret(env.getProperty("wpp.APPSECRET"));
        params.setInterfaceUrl(env.getProperty("wpp.INTERFACE_URL"));
        params.setInterfaceToken(env.getProperty("wpp.INTERFACE_TOKEN"));
        params.setJsDomain(env.getProperty("wpp.JS_DOMAIN"));
        params.setWebAuthDomain(env.getProperty("wpp.WEB_AUTH_DOMAIN"));
        return params;
    }
}
