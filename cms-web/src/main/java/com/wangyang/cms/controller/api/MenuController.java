package com.wangyang.cms.controller.api;

import com.wangyang.common.utils.TemplateUtil;
import com.wangyang.data.service.IComponentsService;
import com.wangyang.data.service.IHtmlService;
import com.wangyang.data.service.IMenuService;
import com.wangyang.model.pojo.entity.Components;
import com.wangyang.model.pojo.entity.Menu;
import com.wangyang.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @Autowired
    IComponentsService convertHtmlAndSave;

    @Autowired
    IHtmlService htmlService;


    @PostMapping
    public Menu add(@RequestBody  Menu menu){
        Menu saveMenu = menuService.add(menu);
       htmlService.generateMenuListHtml();
        return saveMenu;
    }

    @GetMapping
    public List<Menu> list(){
        return menuService.list();
    }

    @PostMapping("/update/{id}")
    public Menu update(@RequestBody  Menu menu,@PathVariable("id") Integer id){
        Menu updateMenu = menuService.update(id, menu);
        htmlService.generateMenuListHtml();
        return updateMenu;
    }

    @RequestMapping("/delete/{id}")
    public BaseResponse delete(@PathVariable("id") Integer id){
        menuService.delete(id);
        htmlService.generateMenuListHtml();
        return BaseResponse.ok("Delete id "+id+" menu success!!");
    }
    @GetMapping("/find/{id}")
    public Menu findById(@PathVariable("id") Integer id){
        return menuService.findById(id);
    }
}
