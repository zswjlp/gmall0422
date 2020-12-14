package com.atguigu.gmall0422.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0422.bean.*;
import com.atguigu.gmall0422.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MangeConller {
    @Reference
    private ManageService manageService;

    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){

        return manageService.getCatalog1();
    }
    //http://localhost:8082/getCatalog2?catalog1Id=6
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        return manageService.getCataLog2(catalog1Id);
    }
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        return manageService.getCataLog3(catalog2Id);
    }
    //
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){
        return manageService.getAttrInfoList(catalog3Id);
    }
    //保存数据 从前台页面中获取数据！
    //http://localhost:8082/saveAttrInfo
    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveBaseAttrInfo(baseAttrInfo);
        return "ok";
    }
//    @RequestMapping("getAttrValueList")
//    public List<BaseAttrValue> getAttrValueList(String attrId){
//        return manageService.getAttrValueList(attrId);
//    }

    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId){
        //attrId = baseAttrInfo.id平台属性Id
        //根据平台属性Id查询是否有平台属性对象
        BaseAttrInfo baseAttrInfo =manageService.getBaseAttrInfo(attrId);
        return baseAttrInfo.getAttrValueList();
    }
//http://localhost:8088/spuList?catalog3Id=62
    @RequestMapping("spuList")
    public List<SpuInfo> spuList(SpuInfo spuInfo){
        return manageService.getSpuInfoList(spuInfo);
    }

}
