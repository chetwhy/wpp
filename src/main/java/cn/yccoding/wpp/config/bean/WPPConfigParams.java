package cn.yccoding.wpp.config.bean;

import lombok.Data;

/**
 * @author : Chet
 * @description : 微信公众平台配置的参数
 * @date : 2019/11/1
 */
@Data
public class WPPConfigParams {

    // 公众号id
    private String appId;

    // app密钥
    private String appSecret;

    // 微信接入——接口配置信息
    private String interfaceUrl;

    // 微信接入——接口配置信息
    private String interfaceToken;

    // js安全域名
    private String jsDomain;

    // 网页授权域名
    private String webAuthDomain;

}
