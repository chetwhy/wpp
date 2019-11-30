package cn.yccoding.wpp.service;

/**
 * @author : Chet
 * @description : 自定义微信菜单接口
 * @date : 2019/11/21
 */
public interface ICustomMenuService {
    String createMenu(String menuJson);

    String getMenu();

    String deleteMenu();
}
