package cn.yccoding.wpp.controller;

import cn.yccoding.wpp.service.ICustomMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Chet
 * @description : 微信公众号菜单配置控制器
 * @date : 2019/11/21
 */
@RestController
@RequestMapping("/api/v1/wpp")
public class WPPMenuController {

    @Autowired
    private ICustomMenuService customMenuService;


    @GetMapping("/menus")
    public String getMenu() {
        return customMenuService.getMenu();
    }

    @PostMapping("/menus")
    public String createMenu(@RequestBody String menuJson) {
        return customMenuService.createMenu(menuJson);
    }

    @GetMapping("/menus/delete")
    public String deleteMenu() {
        return customMenuService.deleteMenu();
    }
}
