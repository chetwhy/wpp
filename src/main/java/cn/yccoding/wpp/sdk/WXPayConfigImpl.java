package cn.yccoding.wpp.sdk;

import cn.yccoding.wpp.config.bean.WCPConfigParams;

import java.io.*;

/**
 * @author : Chet
 * @description : WXPayConfig继承类
 * @date : 2019/11/1
 */
public class WXPayConfigImpl implements WXPayConfig {

    private WCPConfigParams wcpConfigParams;

    private byte[] certData;

    public WXPayConfigImpl(WCPConfigParams wcpConfigParams) throws IOException {
        this.wcpConfigParams = wcpConfigParams;
        String certPath = wcpConfigParams.getApiclientCert();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int)file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public WXPayConfigImpl() {}

    public void setWcpConfigParams(WCPConfigParams wcpConfigParams) {
        this.wcpConfigParams = wcpConfigParams;
    }

    @Override
    public String getAppID() {
        return wcpConfigParams.getAppId();
    }

    @Override
    public String getMchID() {
        return wcpConfigParams.getMuchId();
    }

    @Override
    public String getKey() {
        return wcpConfigParams.getApiKey();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }
}
