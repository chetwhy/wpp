package cn.yccoding.wpp.config;

import lombok.Data;

/**
 * @author : Chet
 * @description : 微信支付配置的参数
 * @date : 2019/11/1
 */
@Data
public class WCPConfigParams {

    // 公众号id
    private String appId;

    // app密钥
    private String appSecret;

    // 商户号
    private String muchId;

    // api密钥
    private String apiKey;

    // 公众号注册域名
    private String registerDomain;

    // jsapi支付目录
    private String jsapiPaymentAuthDir;

    // js安全域名
    private String jsDomain;

    // 网页授权域名
    private String webAuthDomain;

    // 证书目录
    private String apiclientCert;

    // 支付回调地址
    private String notifyUrl;

    // 退款回调地址
    private String notifyUrlRefund;

}
