package cn.yccoding.wpp.pay;

/**
 * @author : Chet
 * @description : 微信公众号后台支付常量
 * @date : 2019/11/5
 */
public class WCPBackendConst {

    public enum TradeType {
        JSAPI, NATIVE, APP
    }

    public enum BillType {
        ALL,        // （默认值），返回当日所有订单信息（不含充值退款订单）
        SUCCESS,    // 返回当日成功支付的订单（不含充值退款订单）
        RECHARGE_REFUND // 返回当日充值退款订单
    }

    public enum AccountType {
        Basic,      // 基本账户
        Operation,  // 运营账户
        Fees        // 手续费账户
    }
}
