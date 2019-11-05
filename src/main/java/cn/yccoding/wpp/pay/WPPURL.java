package cn.yccoding.wpp.pay;

/**
 * @author : Chet
 * @description : 微信公众平台的url
 * @date : 2019/11/5
 */
public class WPPURL {
    // 获取基本accessToken的接口
    public static final String BASE_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    // 页面使用jssdk的凭据
    public static final String BASE_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

}
