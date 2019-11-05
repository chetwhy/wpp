package cn.yccoding.wpp.config;

import cn.yccoding.wpp.sdk.WXPay;
import cn.yccoding.wpp.sdk.WXPayConfigExt;
import cn.yccoding.wpp.sdk.WXPayConfigYMLExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author : Chet
 * @description : 微信支付配置
 * @date : 2019/11/1
 */
@Configuration
@PropertySource("classpath:config/wechat-pay-ups-chet.properties")
public class WCPConfig {

    @Autowired
    private Environment env;

    @Autowired
    private WCPConfigParamsYML wcpConfigParamsYML;

    @Bean
    public WCPConfigParams wcpConfigParams() {
        WCPConfigParams params = new WCPConfigParams();
        params.setAppId(env.getProperty("wcp.APP_ID"));
        params.setAppSecret(env.getProperty("wcp.APPSECRET"));
        params.setMuchId(env.getProperty("wcp.MCH_ID"));
        params.setApiKey(env.getProperty("wcp.API_KEY"));
        params.setRegisterDomain(env.getProperty("wcp.REGISTER_DOMAIN"));
        params.setJsapiPaymentAuthDir(env.getProperty("wcp.JSAPI_PAYMENT_AUTH_DIR"));
        params.setJsDomain(env.getProperty("wcp.JS_DOMAIN"));
        params.setWebAuthDomain(env.getProperty("wcp.webAuthDomain"));
        params.setApiclientCert(env.getProperty("wcp.APICLIENT_CERT"));
        params.setNotifyUrl(env.getProperty("wcp.NOTIFY_URL"));
        params.setNotifyUrlRefund(env.getProperty("wcp.NOTIFY_URL_REFUND"));
        return params;
    }

    @Bean
    @DependsOn(value = "wcpConfigParams")
    public WXPayConfigExt wxPayConfigExt() {
        WXPayConfigExt wxPayConfigExt = new WXPayConfigExt();
        wxPayConfigExt.setWcpConfigParams(wcpConfigParams());
        return wxPayConfigExt;
    }

    @Bean
    public WXPayConfigYMLExt wxPayConfigYMLExt() {
        WXPayConfigYMLExt wxPayConfigYMLExt = new WXPayConfigYMLExt();
        wxPayConfigYMLExt.setWcpConfigParamsYML(wcpConfigParamsYML);
        return wxPayConfigYMLExt;
    }

    @Bean(name = "wxPayDefault")
    @DependsOn(value = "wxPayConfigYMLExt")
    public WXPay wxPayDefault() throws Exception {
        WXPay wxPay = new WXPay(wxPayConfigExt());
        return wxPay;
    }
}
