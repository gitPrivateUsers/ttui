package org.pussinboots.morning.online.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.pussinboots.morning.online.entity.WxUserInfo;
import org.pussinboots.morning.online.mapper.WxUserInfoMapper;
import org.pussinboots.morning.online.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
class WxUserInfoServiceImpl extends ServiceImpl<WxUserInfoMapper, WxUserInfo> implements IWxUserInfoService {

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;

    @Override
    public Integer insertWxUserInfo(WxUserInfo wxUserInfo){

 //新增用户
        return  wxUserInfoMapper.insert(wxUserInfo);

    }

    @Override
    public WxUserInfo getWxUserInfo(String openId) {
           return wxUserInfoMapper.getByOpenId(openId);
    }

    @Override
    public WxUserInfo getWxUserInfoByWxUserId(long wxUserId) {
        return wxUserInfoMapper.getById(wxUserId);
    }

    @Override
    public WxUserInfo getWxUserInfoByToken(String token) {
        List<WxUserInfo> wxInfo=wxUserInfoMapper.selectByToken(token);
        if(wxInfo!=null&&wxInfo.size()>0){
            return wxInfo.get(0);
        }
        return null;
    }
}
