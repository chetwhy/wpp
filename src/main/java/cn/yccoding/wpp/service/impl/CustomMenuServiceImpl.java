package cn.yccoding.wpp.service.impl;

import cn.yccoding.wpp.common.HttpClientUtil;
import cn.yccoding.wpp.config.bean.WPPConfigParamsYML;
import cn.yccoding.wpp.pay.WPPBackendUtil;
import cn.yccoding.wpp.pay.WPPURL;
import cn.yccoding.wpp.service.ICustomMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author : Chet
 * @description :
 * @date : 2019/9/29
 */
@Service
public class CustomMenuServiceImpl implements ICustomMenuService {
    @Autowired
    private WPPConfigParamsYML wppConfigParams;

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Override
    public String createMenu(String menuJson) {
        String token = wppBackendUtil.getAccessToken(wppConfigParams.getAppId());
        String toUrl = MessageFormat.format(WPPURL.MENU_CREATE, token);
        return httpClientUtil.doPost(toUrl, menuJson);
    }

    @Override
    public String getMenu() {
        String token = wppBackendUtil.getAccessToken(wppConfigParams.getAppId());
        String toUrl = MessageFormat.format(WPPURL.MENU_QUERY, token);
        return httpClientUtil.doGet(toUrl);
    }

    @Override
    public String deleteMenu() {
        String token = wppBackendUtil.getAccessToken(wppConfigParams.getAppId());
        String toUrl = MessageFormat.format(WPPURL.MENU_DELETE, token);
        return httpClientUtil.doGet(toUrl);
    }
}
