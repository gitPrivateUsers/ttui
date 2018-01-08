package org.pussinboots.morning.os.controller.wxService;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.util.WXAppletUserInfo;
import org.pussinboots.morning.user.entity.WxUserInfo;
import org.pussinboots.morning.user.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "json", notes = "json")
    @GetMapping(value = "/wxApi.openId")
    public
    @ResponseBody
    Object getSessionKeyAndOropenid(@RequestParam(value = "code", required = true) String code) {
        if (StringUtils.isNotEmpty(code)) {
            return WXAppletUserInfo.getSessionKeyAndOropenid(code);
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
//        Map<String, String> map = WXAppletUserInfo.getSessionKeyAndOropenid(code);
//        System.out.println("map=========="+map);
//        if (StringUtils.isNotEmpty(map.get("openid"))) {
//            System.out.println("openid=========="+map.get("openid"));
//            WxUserInfo wx1 = wxUserInfoService.getWxUserInfo(map.get("openid"));
//            System.out.println("   WxUserInfo wx = wxUserInfoService.getWxUserInfo=========="+wx1);
//            if (wx1 != null) {
//                System.out.println("进来了 if (wx != null) ");
//
//                return new OsResult(UserReturnCode.USER_NOT_EXIST);
//            }
//            System.out.println("================开始set=======================");
                WxUserInfo wx = new WxUserInfo();
                 wx.setCreateTime(new Date());
                 wx.setNickName(nickName);
                 wx.setStatus(StatusEnum.NORMAL.getStatus());
                 wx.setAvatarUrl(avatarUrl);
                 wx.setProvince(province);
                wx.setCity(city);
                wx.setGender(gender);
//                wx.setOpenId(map.get("openid"));
//            System.out.println(wx1+"================结束set======================="+wx1.toString());
             Integer count=  wxUserInfoService.insertWxUserInfo(wx);
            System.out.println("执行结果"+count);
            return new OsResult(CommonReturnCode.SUCCESS, count);
//        }
//        return new OsResult(UserReturnCode.USER_NOT_EXIST);
    }

    /**
     * POST 登录
     *
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "denglu")
    @PostMapping(value = "/wx.login")
    @ResponseBody
    public Object login(@RequestParam("code") String code,@RequestParam("encryptedData") String encryptedData,@RequestParam("iv") String iv) {
        Map<String, String> map = WXAppletUserInfo.getSessionKeyAndOropenid(code);
        Map<String, String> map1=    WXAppletUserInfo.getUserInfo(encryptedData,map.get("sessionKey"),iv);
        WxUserInfo wx1 = wxUserInfoService.getWxUserInfo(map.get("openid"));
        if(wx1!=null)
            map1.put("userId", String.valueOf(wx1.getWxUserId()));
        return map1;
    }

    /**
     * test
     *
     * @return
     */
    @ApiOperation(value = "test", notes = "test")
    @PostMapping(value = "/wx.test")
    @ResponseBody
    public Object login(@RequestParam("id") long id) {

   return wxUserInfoService.getWxUserInfoByWxUserId(id);

    }

}
