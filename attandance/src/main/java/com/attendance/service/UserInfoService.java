package com.attendance.service;
import com.attendance.dao.UserInfoMapper;
import com.attendance.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public UserInfo login(String loginID,String password){
        return userInfoMapper.login(loginID,password);
    }
    public UserInfo find(Integer userID){
        return userInfoMapper.findByID(userID);
    }
    public UserInfo findByLoginID(String loginID){
        return userInfoMapper.findByLoginID(loginID);
    }
    public List<UserInfo> find(){
        return userInfoMapper.find();
    }
    public List<UserInfo> find(Date beginDate,Date endDate){
        return userInfoMapper.findByDate(beginDate,endDate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int add(UserInfo userInfo){
        return  userInfoMapper.add(userInfo);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int modify(UserInfo userInfo){
        return  userInfoMapper.modify(userInfo);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int remove(Integer id){
        return  userInfoMapper.remove(id);
    }

}
