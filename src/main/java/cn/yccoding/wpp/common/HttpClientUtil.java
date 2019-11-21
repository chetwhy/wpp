package cn.yccoding.wpp.common;

import cn.yccoding.wpp.pay.WPPURL;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

/**
 * @author : Chet
 * @description : http请求工具类
 * @date : 2019/11/21
 */
@Component
public class HttpClientUtil {

    @Autowired
    private RestTemplate restTemplate;

    public String doPost(String toUrl, String requestJson) {
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson);
        return restTemplate.postForObject(toUrl, httpEntity, String.class);
    }

    public String doGet(String toUrl) {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate.getForObject(toUrl, String.class);
    }
}
