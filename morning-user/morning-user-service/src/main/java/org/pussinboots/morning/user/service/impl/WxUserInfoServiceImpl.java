package org.pussinboots.morning.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.pussinboots.morning.user.entity.WxUserInfo;
import org.pussinboots.morning.user.mapper.WxUserInfoMapper;
import org.pussinboots.morning.user.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
class WxUserInfoServiceImpl extends ServiceImpl<WxUserInfoMapper, WxUserInfo> implements IWxUserInfoService {

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;

    @Override
    public Integer insertWxUserInfo(WxUserInfo wxUserInfo){
        return  wxUserInfoMapper.insertWxUserInfo(wxUserInfo);

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
        Map<String, Object> map=new HashMap<>();
       WxUserInfo wx= new WxUserInfo();
        wx.setToken(token);
        map.put("token",wx);
        List<WxUserInfo> wxInfo=wxUserInfoMapper.selectByMap(map);
        if(wxInfo!=null&&wxInfo.size()>0){
            return wxInfo.get(0);
        }
        return null;
    }
}
