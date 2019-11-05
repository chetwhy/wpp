package cn.yccoding.wpp.pay;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import cn.yccoding.wpp.common.Utility;
import cn.yccoding.wpp.model.wcp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.yccoding.wpp.config.WCPConfigParams;
import cn.yccoding.wpp.sdk.WXPay;
import cn.yccoding.wpp.sdk.WXPayConfigImpl;
import cn.yccoding.wpp.sdk.WXPayConstants;
import cn.yccoding.wpp.sdk.WXPayUtil;

/**
 * @author : Chet
 * @description : 微信支付后台实现的工具类
 * @date : 2019/11/1
 */
@Component
public class WCPBackendUtil {

    @Autowired
    @Qualifier("wxPayDefault")
    private WXPay wxPayDefault;

    @Autowired
    private WCPConfigParams wcpConfigParams;

    @Autowired
    private WPPSignatureUtil wppSignatureUtil;

    @Autowired
    private WPPBackendUtil wppBackendUtil;

    /**
     * 统一下单接口，输入指定参数，只关心必要参数
     * @param openid        用户在公众号的唯一识别号
     * @param tradeType     交易类型
     * @param price         价格
     * @param productDesc   商品描述
     * @param terminalIP    终端ip
     * @param requestUrl    请求来源的url
     * @return  返回js校验参数的的map
     */
    public Map<String, Object> unifiedorder(String openid, String tradeType, String price, String productDesc,
        String terminalIP, String requestUrl) {
        try {
            UnifiedOrderRequestEntity requestEntity = new UnifiedOrderRequestEntity();
            requestEntity.setBody(productDesc);
            requestEntity.setOutTradeNo(generateRandomOrderNo());
            requestEntity.setTotalFee(Utility.Yuan2Fen(Double.parseDouble(price)).toString());
            requestEntity.setSpbillCreateIp(terminalIP);
            requestEntity.setOpenid(openid);
            requestEntity.setTradeType(tradeType);
            String nonceStr = WXPayUtil.generateNonceStr();
            requestEntity.setNonceStr(nonceStr);
            requestEntity.setNotifyUrl(wcpConfigParams.getNotifyUrl());

            // 利用sdk统一下单,已自动调用wxpay.fillRequestData(data);
            Map<String, String> respMap = wxPayDefault.unifiedOrder(beanToMap(requestEntity));

            // 统一下单接口调用成功
            if (respMap.get("return_code").equals(WXPayConstants.SUCCESS)
                && respMap.get("result_code").equals((WXPayConstants.SUCCESS))) {
                String prepayId = respMap.get("prepay_id");
                return wppSignatureUtil.permissionValidate(wcpConfigParams.getAppId(), nonceStr, requestUrl, prepayId,
                    wcpConfigParams.getApiKey(),wppBackendUtil.getJsApiTicket(wppBackendUtil.getAccessToken()));
            } else if (!respMap.get("return_code").equals(WXPayConstants.SUCCESS)) {
                Map<String, Object> map = new HashMap<>();
                for (String key : respMap.keySet()) {
                    map.put(key, respMap.get(key));
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通用微信支付的调用方法，参数灵活
     * @param requestEntity UnifiedOrderRequestEntity统一下单的实体类
     * @param requestUrl    请求来源的url
     * @return
     */
    public Map<String, Object> unifiedorder(UnifiedOrderRequestEntity requestEntity, String requestUrl) {
        try {
            String nonceStr = requestEntity.getNonceStr();
            Map<String, String> respMap = wxPayDefault.unifiedOrder(beanToMap(requestEntity));

            // 统一下单接口调用成功
            if (respMap.get("return_code").equals(WXPayConstants.SUCCESS)
                && respMap.get("result_code").equals((WXPayConstants.SUCCESS))) {
                String prepayId = respMap.get("prepay_id");
                return wppSignatureUtil.permissionValidate(wcpConfigParams.getAppId(), nonceStr, requestUrl, prepayId,
                        wcpConfigParams.getApiKey(),wppBackendUtil.getJsApiTicket(wppBackendUtil.getAccessToken()));
            } else if (!respMap.get("return_code").equals(WXPayConstants.SUCCESS)) {
                Map<String, Object> map = new HashMap<>();
                for (String key : respMap.keySet()) {
                    map.put(key, respMap.get(key));
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace(); // 返回包含错误提示的map
        }
        return null;
    }

    public Map<String, String> orderquery(String outTradeNo) {
        try {
            OrderQueryRequestEntity requestEntity = new OrderQueryRequestEntity();
            requestEntity.setOutTradeNo(outTradeNo);
            requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
            Map<String, String> map = orderquery(requestEntity);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> orderquery(OrderQueryRequestEntity requestEntity) {
        try {
            return wxPayDefault.orderQuery(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> closeorder(String outTradeNo) {
        try {
            CloseOrderRequestEntity requestEntity = new CloseOrderRequestEntity();
            requestEntity.setOutTradeNo(outTradeNo);
            requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
            return closeorder(requestEntity);
        } catch (Exception e) {
            WXPayUtil.getLogger().error("调用微信接口closeorder失败 ：{}", e.getMessage());
        }
        return null;
    }

    public Map<String, String> closeorder(CloseOrderRequestEntity requestEntity) {
        try {
            return wxPayDefault.closeOrder(beanToMap(requestEntity));
        } catch (Exception e) {
            WXPayUtil.getLogger().error("调用微信接口closeorder失败 ：{}", e.getMessage());
        }
        return null;
    }

    public Map<String, String> refund(String outTradeNo, String outRefundNo, String totalFee, String refundFee) {
        try {
            RefundRequestEntity requestEntity = new RefundRequestEntity();
            requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
            requestEntity.setOutRefundNo(outRefundNo);
            requestEntity.setOutTradeNo(outTradeNo);
            requestEntity.setRefundFee(refundFee);
            requestEntity.setTotalFee(totalFee);

            return refund(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> refund(RefundRequestEntity requestEntity) {
        try {
            return wxPayDefault.refund(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> refundquery(String outTradeNo) {
        try {
            RefundQueryRequestEntity requestEntity = new RefundQueryRequestEntity();
            requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
            requestEntity.setOutTradeNo(outTradeNo);
            return refundquery(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> refundquery(RefundQueryRequestEntity requestEntity) {
        try {
            return wxPayDefault.refund(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadbill(String billDate, String billType) {
        try {
            DownloadBillRequestEntity requestEntity = new DownloadBillRequestEntity();
            requestEntity.setBillDate(billDate);
            requestEntity.setBillType(billType);
            requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
            return downloadbill(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadbill(DownloadBillRequestEntity requestEntity) {
        try {
            return wxPayDefault.downloadBill(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadfundflow(String billDate, String accountType) {
        try {
            DownloadFundFlowRequestEntity requestEntity = new DownloadFundFlowRequestEntity();
            requestEntity.setBillDate(billDate);
            requestEntity.setAccountType(accountType);
            return downloadfundflow(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadfundflow(DownloadFundFlowRequestEntity requestEntity) {
        try {
            return wxPayDefault.downloadfundflow(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> payitilreport(String interfaceUrl, String executeTime, String returnCode,
        String returnMsg, String resultCode, String userIp) {
        try {
            ReportRequestEntity requestEntity = new ReportRequestEntity();

            requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
            requestEntity.setInterfaceUrl(interfaceUrl);
            requestEntity.setExecuteTime(executeTime);
            requestEntity.setReturnCode(returnCode);
            requestEntity.setReturnMsg(returnMsg);
            requestEntity.setResultCode(resultCode);
            requestEntity.setUserIp(userIp);

            return payitilreport(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> payitilreport(ReportRequestEntity requestEntity) {
        try {
            return wxPayDefault.report(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> batchquerycomment(String beginTime, String endTime, String offset) {
        try {
            BatchQueryCommentRequestEntity requestEntity = new BatchQueryCommentRequestEntity();
            requestEntity.setBeginTime(beginTime);
            requestEntity.setEndTime(endTime);
            requestEntity.setOffset("0");

            return batchquerycomment(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> batchquerycomment(BatchQueryCommentRequestEntity requestEntity) {
        try {
            return wxPayDefault.batchQueryComment(beanToMap(requestEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实体类转化为Map
     *
     * @param obj
     * @return
     */
    private Map<String, String> beanToMap(Object obj) {
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj));
        Map<String, String> resultMap = new HashMap<>();
        for (String key : map.keySet()) {
            resultMap.put(key, (String)map.get(key));
        }
        return resultMap;
    }

    /**
     * 随机生成订单号
     *
     * @return
     */
    public String generateRandomOrderNo() {
        int number = (int)((Math.random() * 9) * 1000);// 随机数
        String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return nowStr + number;
    }
}
