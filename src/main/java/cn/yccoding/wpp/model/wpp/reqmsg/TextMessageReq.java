package cn.yccoding.wpp.model.wpp.reqmsg;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author : Chet
 * @description : 文本消息
 * @date : 2019/11/22
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextMessageReq {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private String createTime;

    @XmlElement(name = "MsgType")
    private String msgType;

    @XmlElement(name = "Content")
    private String content;

    @XmlElement(name = "MsgId")
    private String msgId;

}
