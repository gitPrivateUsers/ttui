package org.pussinboots.morning.os.controller.wxService;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.util.WXAppletUserInfo;
import org.pussinboots.morning.online.entity.WxUserInfo;
import org.pussinboots.morning.online.service.IWxUserInfoService;
import org.pussinboots.morning.user.entity.Address;
import org.pussinboots.morning.user.entity.User;
import org.pussinboots.morning.user.service.IAddressService;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "用户信息", description = "用户信息")
public class wxApiUserLoginService extends BaseController {
    @Autowired
    private IWxUserInfoService wxUserInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAddressService addressService;


    @ApiOperation(value = "登录接口", notes = "根据code获取openId验证用户")
    @GetMapping(value = "/wxApi.login")
    public
    @ResponseBody
    Object getSessionKeyAndOropenid(@RequestParam(value = "code", required = true) String code) {
        if (StringUtils.isNotEmpty(code)) {
            Map<String, String> map = WXAppletUserInfo.getSessionKeyAndOropenid(code);

            if (StringUtils.isNotEmpty(map.get("openid"))) {
                //   验证是否未注册
                WxUserInfo wx1 = wxUserInfoService.getWxUserInfo(map.get("openid"));
                if (wx1 != null) {
                    wx1.setUpdateTime(new Date());
                    wx1.setToken( WXAppletUserInfo.getAccessToken().get("access_token")+"");
                    wxUserInfoService.updateById(wx1);
                    map.put("token", wx1.getToken());
                    map.put("uid", String.valueOf(wx1.getOsUserId()));
                    return new OsResult(CommonReturnCode.SUCCESS, map);
                } else   {
                    //去注册
                    map.put("code", "10000");
                    return new OsResult(CommonReturnCode.SUCCESS, map);
                }
            }
        }
        return new OsResult(CommonReturnCode.BAD_REQUEST, CommonReturnCode.BAD_REQUEST);
    }

    @ApiOperation(value = "根据token验证用户信息", notes = "token")
    @PostMapping(value = "/wxUser.token")
    @ResponseBody
    public Object getOrderList(@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if(wx!=null){
            return new OsResult(CommonReturnCode.SUCCESS, wx);
        }else
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }

    @ApiOperation(value = "获取用户token", notes = "获取用户token")
    @GetMapping(value = "/wxApi._tk")
    public
    @ResponseBody
    Object getAccessToken(@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if(wx!=null){
            wx.setUpdateTime(new Date());
            Map<String, String> tokenMap = WXAppletUserInfo.getAccessToken();
            wx.setToken(tokenMap.get("access_token")+"");
            wxUserInfoService.updateById(wx);
            return tokenMap;
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }

    /**
     * POST 注册
     *
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "验证用户输入的注册信息")
    @PostMapping(value = "/wx.register")
    @ResponseBody
    public Object wxregister(@RequestParam(value = "code", required = true) String code,
                             @RequestParam(value = "nickName", required = false) String nickName,
                             @RequestParam(value = "avatarUrl", required = false) String avatarUrl,
                             @RequestParam(value = "province", required = false) String province,
                             @RequestParam(value = "city", required = false) String city,
                             @RequestParam(value = "gender", required = false) int gender) {
//
        Map<String, String> tokenMap = WXAppletUserInfo.getAccessToken();
        String token = tokenMap.get("access_token");
        Map<String, String> map = WXAppletUserInfo.getSessionKeyAndOropenid(code);
        //   验证是否未注册
        if (StringUtils.isNotEmpty(map.get("openid"))) {
            WxUserInfo wx1 = wxUserInfoService.getWxUserInfo(map.get("openid"));
            if (wx1 != null) {
                return new OsResult(CommonReturnCode.ACCOUNT_EXISTENCE, CommonReturnCode.ACCOUNT_EXISTENCE);
            }
            WxUserInfo wx = new WxUserInfo();
            wx.setCreateTime(new Date());
            //TODO 微信nickname 特殊符号插入报错 不存表（无法保证实时性）
//        wx.setNickName(nickName);
            wx.setNickName("testNickName");
            wx.setStatus(StatusEnum.NORMAL.getStatus());
            wx.setAvatarUrl(avatarUrl);
            wx.setProvince(province);
            wx.setCity(city);
            wx.setGender(gender);
            wx.setOpenId(map.get("openid") + "");
            wx.setSessionKey(map.get("session_key") + "");
            wx.setToken(token + "");
            User user = new User();
            user.setPicImg(avatarUrl);
            user.setSex(gender);
            user.setRealName("WX");
            user.setCreateBy(wx.getOpenId());
            //根据openId保证用户唯一
            if (userService.selectByOpenId(wx.getOpenId()) == null) {
                boolean b = userService.insert(user);
                Long userId = userService.selectByOpenId(wx.getOpenId());
                wx.setOsUserId(userId);
                Integer count = wxUserInfoService.insertWxUserInfo(wx);
                return new OsResult(CommonReturnCode.SUCCESS, count);
            }
        }
        return new OsResult(CommonReturnCode.ACCOUNT_EXISTENCE, CommonReturnCode.ACCOUNT_EXISTENCE);
    }




    /**
     * test
     *
     * @return
     */
    @ApiOperation(value = "test", notes = "test")
    @GetMapping(value = "/wx.test")
    @ResponseBody
    public Object loginTest(@RequestParam("id") Long id) {

        List<Address> ads=addressService.listAddress(id);
        return new OsResult(CommonReturnCode.SUCCESS, ads);

    }
}
