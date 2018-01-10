package org.pussinboots.morning.os.controller.wxService;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.online.entity.WxUserInfo;
import org.pussinboots.morning.online.service.IWxUserInfoService;
import org.pussinboots.morning.os.common.dto.AddressInfo;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.user.entity.Address;
import org.pussinboots.morning.user.entity.User;
import org.pussinboots.morning.user.service.IAddressService;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Api(value = "个人中心", description = "个人中心")
public class wxApiPortalService extends BaseController {
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IWxUserInfoService wxUserInfoService;
    @Autowired
    private IUserService userService;
    /**
     * GET 收货地址列表
     *
     * @return
     */

    @ApiOperation(value = "小程序用户收货地址列表", notes = "小程序用户收货地址列表")
    @GetMapping(value = "/wx.addressList")
    @ResponseBody
    public Object addressTest(@RequestParam("token") String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null && wx.getOsUserId() != 0) {
            User user = userService.selectById(wx.getOsUserId());
            List<Address> ads = addressService.listAddress(user.getUserId());
            List<AddressInfo> addressInfos = new ArrayList<>();
            for (Address ad : ads) {
                AddressInfo add = new AddressInfo();
                add.setAd(ad);
                addressInfos.add(add);
            }
            return new OsResult(CommonReturnCode.SUCCESS, ads);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }
    /**
     * GET 收货地址
     *
     * @return
     */
    @ApiOperation(value = "收货地址默认的", notes = "收货地址默认的")
    @GetMapping(value = "/address.default")
    @ResponseBody
    public OsResult addressDefault(@RequestParam("token") String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        /**
         * 根据wxUserInfo的userId取出用户数据
         */
        if (wx != null && wx.getOsUserId() != 0) {
            User user = userService.selectById(wx.getOsUserId());
            // 验证该用户是否被冻结
            if (user != null && !Objects.equals(user.getStatus(), StatusEnum.FREEZE.getStatus())) {
                Address address = new Address();
                address.setUserId(user.getUserId());
                List<Address> addresses = addressService.selectList(new EntityWrapper<Address>(address));
                if (addresses != null && addresses.size() > 0) {
                    for (Address ad : addresses) {
                        if (Objects.equals(ad.getUserTag(), "true")) {
                            AddressInfo addressInfo = new AddressInfo();
                            addressInfo.setAd(ad);
                            addressInfo.setToken(token);
                            return new OsResult(CommonReturnCode.SUCCESS, addressInfo);
                        } else {

                            //添加第一条为默认地址
                            AddressInfo addressInfo = new AddressInfo();
                            addressInfo.setAd(addresses.get(0));
                            addressInfo.setToken(token);
                            return new OsResult(CommonReturnCode.SUCCESS, addressInfo);
                        }
                    }
                }
                return new OsResult(CommonReturnCode.NOT_ADDRESS, CommonReturnCode.NOT_ADDRESS);
            } else
                return new OsResult(CommonReturnCode.THE_USER_IS_FROZEN, CommonReturnCode.THE_USER_IS_FROZEN);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }

    /**
     * GET 根据地址ID查找收货地址
     *
     * @return
     */
    @ApiOperation(value = "根据地址ID查找收货地址", notes = "根据地址ID查找收货地址")
    @GetMapping(value = "/address.detail")
    @ResponseBody
    public OsResult getAddressDetail(@RequestParam(value = "token", required = true) String token, @RequestParam(value = "id", required = true) Long id) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        /**
         * 根据wxUserInfo的userId取出用户数据
         */
        if (wx != null && wx.getOsUserId() != 0) {
            User user = userService.selectById(wx.getOsUserId());
            // 验证该用户是否被冻结
            if (user != null && !Objects.equals(user.getStatus(), StatusEnum.FREEZE.getStatus())) {
                Address ads = addressService.selectById(id);
                return new OsResult(CommonReturnCode.SUCCESS, ads);
            }
            return new OsResult(CommonReturnCode.THE_USER_IS_FROZEN, CommonReturnCode.THE_USER_IS_FROZEN);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }

    /**
     * POST 创建收货地址
     * address //详细地址
     * provinceId//省
     * cityId//市
     * districtId //区
     * *linkMan //联系人
     * token
     * isDefault
     * code //邮政编码
     * mobile //手机号
     */
    @ApiOperation(value = "创建/修改收货地址", notes = "根据url传的ID判断创建or修改")
    @PostMapping(value = "/address.create")
    @ResponseBody
    public OsResult addressCreate(@RequestParam("address") String address //详细地址
            , @RequestParam("provinceId") Integer provinceId//省
            , @RequestParam("provinceName") String provinceName//省
            , @RequestParam("cityId") Integer cityId//市
            , @RequestParam("cityName") String cityName//市
            , @RequestParam("districtId") Integer districtId //区
            , @RequestParam("districtName") String districtName //区
            , @RequestParam("linkMan") String linkMan //联系人
            , @RequestParam("id") Long id
            , @RequestParam("token") String token
            , @RequestParam("isDefault") String isDefault
            , @RequestParam("code") String code //邮政编码
            , @RequestParam("mobile") String mobile) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        //  根据wxUserInfo的userId取出用户数据
        if (wx != null && wx.getOsUserId() != 0) {
            User user = userService.selectById(wx.getOsUserId());
            // 验证该用户是否被冻结
            if (user != null && !Objects.equals(user.getStatus(), StatusEnum.FREEZE.getStatus())) {
                if (id == 0) {
                    //创建用户地址
                    Address ad = new Address();
                    ad.setUserId(user.getUserId());
                    List<Address> addresses = addressService.selectList(new EntityWrapper<Address>(ad));
                    if(addresses==null){
                        ad.setUserTag("true");//添加默认值
                    }
                    ad.setProvinceId(provinceId);
                    ad.setProvinceName(provinceName);
                    ad.setCityId(cityId);
                    ad.setCityName(cityName);
                    ad.setDistrictId(districtId);
                    ad.setDistrictName(districtName);
                    ad.setUserAdress(address);
                    ad.setUserName(linkMan);
                    ad.setUserPhone(mobile);
                    ad.setUserZipcode(Integer.valueOf(code));
                    ad.setCreateTime(new Date());
                    Integer count = addressService.insertAddress(ad);
                    return new OsResult(CommonReturnCode.SUCCESS, count);
                } else {
                    Address ads = addressService.selectById(id);
                    if (ads == null)
                    return new OsResult(CommonReturnCode.FAIL_UPDATE, CommonReturnCode.FAIL_UPDATE);
                    ads.setProvinceId(provinceId);
                    ads.setProvinceName(provinceName);
                    ads.setCityId(cityId);
                    ads.setCityName(cityName);
                    ads.setDistrictId(districtId);
                    ads.setDistrictName(districtName);
                    ads.setUserAdress(address);
                    ads.setUserName(linkMan);
                    ads.setUserPhone(mobile);
                    ads.setUserZipcode(Integer.valueOf(code));
                    ads.setUpdateTime(new Date());
                    ads.setUserId(user.getUserId());
                    boolean count = addressService.updateById(ads);
                    return new OsResult(CommonReturnCode.SUCCESS, count);
                }
            } else
                return new OsResult(CommonReturnCode.THE_USER_IS_FROZEN, CommonReturnCode.THE_USER_IS_FROZEN);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }
    /**
     * DELETE 收货地址
     *
     * @return
     */
    @ApiOperation(value = "删除收货地址", notes = "根据url传过来的地址ID删除收货地址")
    @DeleteMapping(value = "/address.delete")
    @ResponseBody
    public OsResult addressDelete(@RequestParam(value = "token", required = true) String token, @RequestParam(value = "id", required = true) Long id) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        // 根据wxUserInfo的userId取出用户数据
        if (wx != null && wx.getOsUserId() != 0) {
            User user = userService.selectById(wx.getOsUserId());
            // 验证该用户是否被冻结
            if (user != null && !Objects.equals(user.getStatus(), StatusEnum.FREEZE.getStatus())) {
                Integer count = addressService.deleteByAddressId(user.getUserId(), id);
                return new OsResult(CommonReturnCode.SUCCESS, count);
            }
            return new OsResult(CommonReturnCode.THE_USER_IS_FROZEN, CommonReturnCode.THE_USER_IS_FROZEN);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }


}
