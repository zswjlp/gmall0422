package com.atguigu.gmall0422.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall0422.bean.*;
import com.atguigu.gmall0422.manage.mapper.*;
import com.atguigu.gmall0422.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;


    @Override
    public List<BaseCatalog1> getCatalog1() {

        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCataLog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCataLog3(String catalog2Id) {
//  方式一：BaseCatalog3 baseCatalog3 = new BaseCatalog3();
//        baseCatalog3.setCatalog2Id(catalog2Id);
//        return baseCatalog3Mapper.select(baseCatalog3);
       //方式二：
        Example example = new Example(BaseCatalog3.class);
        example.createCriteria().andEqualTo("catalog2Id",catalog2Id);
        return baseCatalog3Mapper.selectByExample(example);
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return baseAttrInfoMapper.select(baseAttrInfo);
    }

    @Override
    @Transactional
    public void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo) {
        //修改保存 根据Id判断
//        if(id){
//            update
//        }else{ insert}
        if(baseAttrInfo.getId()!=null &&baseAttrInfo.getId().length()>0){
            //baseAttrInfo
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else {
            //baseAttrInfo
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }
        //制作异常验证事务
//        int i = 1/0;
        //修改：可以将baseAttrValue先删除后新增
        BaseAttrValue baseAttrValueDel = new BaseAttrValue();
        baseAttrValueDel.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValueDel);

        //baseAttrValue
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if(attrValueList!=null && attrValueList.size()>0){
            //循环遍历
            for(BaseAttrValue baseAttrValue : attrValueList){
                //baseAttrInfo.getId()要想获取到id，则在baseAttrInfo实体类中必须添加获取主键自增的注解
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(baseAttrValue);
            }
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }

    @Override
    public BaseAttrInfo getBaseAttrInfo(String attrId) {
        //获取平台属性对象
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //将平台属性值集合放入平台属性对象中
        baseAttrInfo.setAttrValueList(getAttrValueList(attrId));
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {
        return null;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        //supInfo
        spuInfoMapper.insertSelective(spuInfo);
        //spuImage
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        //判断条件不能呼唤，如果呼唤，可能会发生空指针异常
        if(spuImageList!=null&&spuImageList.size()>0){
            for(SpuImage spuImage : spuImageList){
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }
        //spuSaleAttr
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList!=null&&spuSaleAttrList.size()>0){
            for(SpuSaleAttr spuSaleAttr : spuSaleAttrList){
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);
                //spuSaleAttrValue
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if(spuSaleAttrValueList!=null&&spuSaleAttrValueList.size()>0){
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }

            }
        }

    }
}
