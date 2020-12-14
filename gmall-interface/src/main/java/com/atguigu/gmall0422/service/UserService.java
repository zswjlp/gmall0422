package com.atguigu.gmall0422.service;




import com.atguigu.gmall0422.bean.UserAddress;
import com.atguigu.gmall0422.bean.UserInfo;

import java.util.List;

public interface UserService {
    //查询所有数据
    List<UserInfo> findAll();
    //如何判断返回 loginName nickName id
    List<UserInfo> findByUserInfo(UserInfo userInfo);
    //loginName 模糊查询
    List<UserInfo> findByLoginName(String loginName);
    //添加用户信息
    void addUser(UserInfo userInfo);
    //根据 主键修改
    void updUser(UserInfo userInfo);
    //删除
    void delUser(UserInfo userInfo);
    //根据userid查询用户地址列表
    //select * from userAddress where userId = ?
    List<UserAddress> findUserAddressByUserId(String userId);
}
