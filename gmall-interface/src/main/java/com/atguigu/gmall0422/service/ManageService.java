package com.atguigu.gmall0422.service;

import com.atguigu.gmall0422.bean.*;

import java.util.List;

public interface ManageService {
    //查询所有一级分类属性
    List<BaseCatalog1>  getCatalog1();
    //查询所有二级分类ID
    List<BaseCatalog2> getCataLog2(String catalog1Id);
    //查询三级分类ID
    List<BaseCatalog3> getCataLog3(String catalog2Id);
    //平台属性
    List<BaseAttrInfo> getAttrInfoList(String catalog3Id);
    //保存平台属性-平台属性值
    //括号中的参数，实际就是从页面传递过来的数据！《传递对象接收前台传来数据》
    //自己制作vo,attrName,List--attrValueName;--直接使用BaseAttrInfo
    void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo);
    //根据平台属性id {attrId=baseAttrInfo.Id}
    List<BaseAttrValue> getAttrValueList(String attrId);
    //根据平台属性id获取平台属性对象
    BaseAttrInfo getBaseAttrInfo(String attrId);
    //根据三级分类id查询spuInfo列表
    List<SpuInfo> getSpuInfoList(String catalog3Id);
    //根据suInfo的属性查询spuInfo列表
    List<SpuInfo> getSpuInfoList(SpuInfo spuInfo);
    // 查询基本销售属性表
    List<BaseSaleAttr> getBaseSaleAttrList();
    //保存spu
    void saveSpuInfo(SpuInfo spuInfo);
}
