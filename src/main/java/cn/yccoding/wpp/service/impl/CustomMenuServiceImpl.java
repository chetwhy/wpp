package cn.yccoding.wpp.service.impl;

import cn.yccoding.wpp.common.HttpClientUtil;
import cn.yccoding.wpp.config.WPPConfigParams;
import cn.yccoding.wpp.pay.WPPBackendUtil;
import cn.yccoding.wpp.pay.WPPURL;
import cn.yccoding.wpp.service.ICustomMenuService;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * @author : Chet
 * @description :
 * @date : 2019/9/29
 */
@Service
public class CustomMenuServiceImpl implements ICustomMenuService {
    @Autowired
    private WPPConfigParams wppConfigParams;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Override
    public String createMenu(String menuJson) {
        String token = wppBackendUtil.getAccessTokenInRedis(wppConfigParams.getAppId());
        String toUrl = MessageFormat.format(WPPURL.MENU_CREATE, token);
        HttpEntity<String> httpEntity = new HttpEntity<>(menuJson);
        String result = restTemplate.postForObject(toUrl, httpEntity, String.class);
        System.out.println(result);
        return result;
    }

    @Override
    public String getMenu() {
        String token = wppBackendUtil.getAccessTokenInRedis(wppConfigParams.getAppId());
        String toUrl = MessageFormat.format(WPPURL.MENU_QUERY, token);
        String result = httpClientUtil.doGet(toUrl);
        return result;
    }

    @Override
    public String deleteMenu() {
        String token = wppBackendUtil.getAccessTokenInRedis(wppConfigParams.getAppId());
        String toUrl = MessageFormat.format(WPPURL.MENU_DELETE, token);
        String result = httpClientUtil.doGet(toUrl);
        return result;
    }
}
