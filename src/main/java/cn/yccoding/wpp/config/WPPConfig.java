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
    public WPPConfigParams wcpConfigParams() {
        WPPConfigParams params = new WPPConfigParams();
        params.setAppId(env.getProperty("wcp.APP_ID"));
        params.setAppSecret(env.getProperty("wcp.APPSECRET"));
        params.setRegisterDomain(env.getProperty("wcp.REGISTER_DOMAIN"));
        params.setWebAuthDomain(env.getProperty("wcp.webAuthDomain"));
        return params;
    }
}
