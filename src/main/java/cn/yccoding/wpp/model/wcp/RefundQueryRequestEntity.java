package cn.yccoding.wpp.model.wcp;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author : Chet
 * @description : 查询退款请求的实体类
 * @date : 2019/10/29
 */
@Data
public class RefundQueryRequestEntity {

    /**
     * 公众账号ID
     */
    private String appid;

    /**
     * 商户号
     */
    @JSONField(name = "mch_id")
    private String mchId;

    /**
     * 随机字符串
     */
    @JSONField(name = "nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;

    /**
     * 签名类型
     */
    @JSONField(name = "sign_type")
    private String signType;

    // 四选一，微信订单号查询的优先级是： refund_id > out_refund_no > transaction_id > out_trade_no
    /**
     * 微信订单号
     */
    @JSONField(name = "transaction_id")
    private String transactionId;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    @JSONField(name = "out_refund_no")
    private String outRefundNo;

    /**
     * 微信退款单号
     */
    @JSONField(name = "refund_id")
    private String refundId;

    /**
     * 偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录
     */
    private String offset;

}