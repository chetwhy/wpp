package cn.yccoding.wpp.pay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WPPBackendUtilTest {

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    @Test
    void getAccessToken() {
        System.out.println(wppBackendUtil.getAccessToken());
    }

    @Test
    void getTicket() {
        System.out.println(wppBackendUtil.getJsApiTicket(wppBackendUtil.getAccessToken()));
    }
}