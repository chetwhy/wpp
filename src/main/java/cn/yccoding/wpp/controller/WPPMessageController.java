package cn.yccoding.wpp.controller;

import cn.yccoding.wpp.model.wpp.reqmsg.TextMessageReq;
import cn.yccoding.wpp.model.wpp.respmsg.TextMessageResp;
import cn.yccoding.wpp.pay.WPPBackendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;

/**
 * @author : Chet
 * @description : 微信消息处理
 * @date : 2019/11/21
 */
@RestController
@RequestMapping("/api/v1/wpp")
public class WPPMessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    /**
     * 微信公众号url接入确认
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/portal")
    @ResponseBody
    public String validate(String signature,String timestamp,String nonce,String echostr){
        if (!wppBackendUtil.checkSignature(signature, timestamp, nonce)) {
            logger.error("WPPMessageController.validate -- 公众号接入失败");
            return null;
        }
        logger.info("WPPMessageController.validate -- 公众号接入成功,echostr:[{}]",echostr);
        return echostr;
    }

    /**
     * 消息管理
     */
    @PostMapping("portal")
    @ResponseBody
    public Object manageMessage(@RequestBody TextMessageReq reqMsg) {
        TextMessageResp respMsg = new TextMessageResp();
        respMsg.setFromUserName(reqMsg.getToUserName());
        respMsg.setToUserName(reqMsg.getFromUserName());
        respMsg.setCreateTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        respMsg.setMsgType("text");
        respMsg.setContent(reqMsg.getContent());
        return respMsg;
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        //System.out.println(LocalDateTime.now().getLong(ChronoField.INSTANT_SECONDS));
        System.out.println(new Date().getTime());
    }
}
