package cn.yccoding.wpp.config;

import lombok.Data;

/**
 * @author : Chet
 * @description : 微信支付配置的参数
 * @date : 2019/11/1
 */
@Data
public class WCPConfigParams {

    private String appId;

    private String appSecret;

    private String muchId;

    private String apiKey;

    private String registerDomain;

    private String jsapiPaymentAuthDir;

    private String jsDomain;

    private String webAuthDomain;

    private String apiclientCert;

    private String notifyUrl;

    private String notifyUrlRefund;

}
