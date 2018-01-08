package org.pussinboots.morning.os.controller.wxService;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.qiniu.util.Json;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.util.WXAppletUserInfo;
import org.pussinboots.morning.online.entity.WxUserInfo;
import org.pussinboots.morning.online.service.IWxUserInfoService;
import org.pussinboots.morning.user.common.constant.UserReturnCode;
import org.pussinboots.morning.user.entity.User;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "微信小程序api", description = "微信小程序api")
public class wxApiUserLoginService extends BaseController {
    @Autowired
    private IWxUserInfoService wxUserInfoService;
    @Autowired
    private IUserService userService;


    @ApiOperation(value = "json", notes = "json")
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
                    Map<String, String> map1 = WXAppletUserInfo.getAccessToken();
                    map.put("token", map1.get("access_token"));
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

    @ApiOperation(value = "json", notes = "json")
    @GetMapping(value = "/wxApi._tk")
    public
    @ResponseBody
    Object getAccessToken() {
        return WXAppletUserInfo.getAccessToken();
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
        System.out.println("map==========" + map);
        //   验证是否未注册
        if (StringUtils.isNotEmpty(map.get("openid"))) {
            System.out.println("openid==========" + map.get("openid"));
            WxUserInfo wx1 = wxUserInfoService.getWxUserInfo(map.get("openid"));
            System.out.println("   WxUserInfo wx = wxUserInfoService.getWxUserInfo==========" + wx1);
            if (wx1 != null) {
                System.out.println("进来了 if (wx != null) ");

                return new OsResult(123456, "该账号已存在！");
            }
            System.out.println("================开始set=======================");
            WxUserInfo wx = new WxUserInfo();
            wx.setCreateTime(new Date());
//        wx.setNickName(nickName);
            //TODO 微信nickname 特殊符号插入报错 不存表（无法保证实时性）
//            System.out.println(nickName);
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
            System.out.println("开始验证" + wx.getOpenId() + "=====null===" + userService.selectByOpenId(wx.getOpenId()));

            if (userService.selectByOpenId(wx.getOpenId()) == null) {
                System.out.println("进入判断 serService.selectByOpenId(wx.getOpenId()) < 1====== ");
                boolean b = userService.insert(user);
                System.out.println("执行创建user表结果" + b);
                Long userId = userService.selectByOpenId(wx.getOpenId());
                System.out.println("创建成功拿到userId" + userId);
                wx.setOsUserId(userId);

                Integer count = wxUserInfoService.insertWxUserInfo(wx);
                System.out.println("执行结果" + count);
                return new OsResult(CommonReturnCode.SUCCESS, count);
            }
            return new OsResult(22222222, "出错了！");
        }
        return new OsResult(123456, "该账号已存在！");
    }


    /**
     * test
     *
     * @return
     */
    @ApiOperation(value = "test", notes = "test")
    @PostMapping(value = "/wx.test")
    @ResponseBody
    public Object login(@RequestParam("id") String id) {

        return userService.selectByOpenId(id);

    }

    @ApiOperation(value = "用户token", notes = "token")
    @PostMapping(value = "/wxUser.token")
    @ResponseBody
    public Object getOrderList(@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        return new OsResult(CommonReturnCode.SUCCESS, wx);

    }
}
