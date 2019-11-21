package cn.yccoding.wpp.controller;

import cn.yccoding.wpp.service.ICustomMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
