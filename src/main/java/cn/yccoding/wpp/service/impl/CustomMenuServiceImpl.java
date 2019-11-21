package cn.yccoding.wpp.service.impl;

import cn.yccoding.wpp.config.WPPConfigParams;
import cn.yccoding.wpp.pay.WPPBackendUtil;
import cn.yccoding.wpp.pay.WPPURL;
import cn.yccoding.wpp.service.ICustomMenuService;
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

    @Override
    public String createMenu(String menuJson) {
        String token = wppBackendUtil.getAccessTokenInRedis("menu");

        String toUrl = MessageFormat.format(WPPURL.MENU_CREATE, token);
        HttpEntity<String> httpEntity = new HttpEntity<>(menuJson);
        restTemplate.postForObject(toUrl, httpEntity, String.class, )
        String result =
            HttpUtil.post(WxGZHConstants.CREATE_MENU_URL.replace("ACCESS_TOKEN", wxConfigUtil.getAccessToken()), menuJson);
        wxConfigUtil.getLogger().info("创建自定义菜单结果：{}", result);
        return result;
    }

    @Override
    public String getMenu() {
        String result = HttpUtil.get(WxGZHConstants.GET_MENU_URL.replace("ACCESS_TOKEN", wxConfigUtil.getAccessToken()));
        wxConfigUtil.getLogger().info("查询菜单结果：{}", result);
        return result;
    }

    @Override
    public String deleteMenu() {
        String result = HttpUtil.get(WxGZHConstants.DELETE_MENU_URL.replace("ACCESS_TOKEN", wxConfigUtil.getAccessToken()));
        wxConfigUtil.getLogger().info("删除菜单结果：{}", result);
        return result;
    }
}
