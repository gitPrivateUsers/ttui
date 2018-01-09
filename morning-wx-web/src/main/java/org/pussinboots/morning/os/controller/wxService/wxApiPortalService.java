package org.pussinboots.morning.os.controller.wxService;

import com.alibaba.dubbo.common.json.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
import org.pussinboots.morning.user.service.IFavoriteService;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "微信小程序api", description = "微信小程序api")
public class wxApiPortalService extends BaseController {
    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private IAddressService addressService;
    @Autowired
    private IWxUserInfoService wxUserInfoService;

    @Autowired
    private IUserService userService;


    /**
     * 个人中心数据结果集合
     * <p>
     * GET 我的个人中心
     *
     * @return
     */
//    @ApiOperation(value = "我的个人中心", notes = "我的个人中心")
//    @GetMapping(value = "/portal.my")
//    @ResponseBody
//    public OsResult portal() {
//
//        Map<String, Object> model = new HashMap<String, Object>();
//        if (SingletonLoginUtils.getUserId() == null) {
//            //未登录
//            return new OsResult(CommonReturnCode.UNAUTHORIZED);
//        }
//        UserVO userVO = userService.getById(SingletonLoginUtils.getUserId());
//        model.put("userVO", userVO);
//
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }

//    /**
//     * GET 喜欢的商品
//     *
//     * @return
//     */
//    @ApiOperation(value = "喜欢的商品", notes = "喜欢的商品")
//    @GetMapping(value = "/favorite.product")
//    @ResponseBody
//    public OsResult favorite(
//            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
//            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {
//
//        PageInfo pageInfo = new PageInfo(limit, page);
//        Map<String, Object> model = new HashMap<String, Object>();
//        BasePageDTO<Favorite> basePageDTO = favoriteService.listByUserId(SingletonLoginUtils.getUserId(), pageInfo);
//
//        model.put("favorites", basePageDTO.getList());
//        model.put("pageInfo", basePageDTO.getPageInfo());
//
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }

    /**
     * DELETE 喜欢的商品
     *
     * @return
     */
//    @ApiOperation(value = "删除喜欢的商品", notes = "根据url传过来的商品编号删除喜欢的商品")
//    @DeleteMapping(value = "/favorite.product.del")
//    @ResponseBody
//    public OsResult favoriteDelete(@RequestParam("productNumber") Long productNumber) {
//        Integer count = favoriteService.deleteByProductNumber(SingletonLoginUtils.getUserId(), productNumber);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }

    /**
     * GET 收货地址
     *
     * @return
     */
    @ApiOperation(value = "收货地址列表", notes = "收货地址列表")
    @GetMapping(value = "/address.list")
    @ResponseBody
    public OsResult address( @RequestParam("token") String token) {
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
                List<Address> addresses=addressService.selectList(new EntityWrapper<Address>(address));
                List<AddressInfo> addressInfos=new ArrayList<>();
                for(Address ad:addresses){
                    AddressInfo add=new AddressInfo();
                    add.setAd(ad);
                    addressInfos.add(add);
                }
                return new OsResult(CommonReturnCode.SUCCESS, addressInfos);
            }else
                return new OsResult(CommonReturnCode.FAILED, "该用户被冻结！");
        }
        return new OsResult(CommonReturnCode.FAILED, "登录超时");
    }
    /**
     * GET 收货地址
     *
     * @return
     */
    @ApiOperation(value = "收货地址默认的", notes = "收货地址默认的")
    @GetMapping(value = "/address.default")
    @ResponseBody
    public OsResult addressDefault( @RequestParam("token") String token) {
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
                List<Address> addresses=addressService.selectList(new EntityWrapper<Address>(address));
                if(addresses!=null&&addresses.size()>0){
                    boolean flag=false;
                    for(Address ad:addresses){
                        if(Objects.equals(ad.getUserTag(), "true")){
                            AddressInfo addressInfo=new AddressInfo();
                            addressInfo.setAd(ad);
                            addressInfo.setToken(token);
                            try {
                              String ads=  JSON.json(addressInfo);
                                System.out.println("数据：============="+ads);
                                return new OsResult(CommonReturnCode.SUCCESS, ads);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                           flag=true;
                        }
                    }
                    //添加第一条为默认地址
                    if(flag){
                        AddressInfo addressInfo=new AddressInfo();
                        addressInfo.setAd(addresses.get(0));
                        addressInfo.setToken(token);
                        return new OsResult(CommonReturnCode.SUCCESS, addressInfo);
                    }
                }
            }else
                return new OsResult(CommonReturnCode.FAILED, "该用户被冻结！");
        }
        return new OsResult(CommonReturnCode.FAILED, "登录超时");
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
    @ApiOperation(value = "创建收货地址", notes = "根据url传过来的收货地址信息创建收货地址")
    @PostMapping(value = "/address.create")
    @ResponseBody
    public OsResult addressCreate(@RequestParam("address") String address
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
            , @RequestParam("mobile") String mobile //手机号
    ) {

        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        /**
         * 根据wxUserInfo的userId取出用户数据
         */
        if (wx != null && wx.getOsUserId() != 0) {
            User user = userService.selectById(wx.getOsUserId());
              // 验证该用户是否被冻结
            if (user != null && !Objects.equals(user.getStatus(), StatusEnum.FREEZE.getStatus())) {
                //创建用户地址
                Address ad = new Address();
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
                ad.setUserId(user.getUserId());
                Integer count = addressService.insertAddress(ad);
                return new OsResult(CommonReturnCode.SUCCESS, count);
            } else
                return new OsResult(CommonReturnCode.FAILED, "该用户被冻结！");
        }
        return new OsResult(CommonReturnCode.FAILED, "登录超时");
    }

    /**
     * PUT 更新收货地址
     *
     * @return
     */
//    @ApiOperation(value = "更新收货地址", notes = "根据url传过来的收获地址ID和收货地址信息更新收货地址")
//    @PutMapping(value = "/address.edit")
//    @ResponseBody
//    public OsResult addressUpdate(Address address, @RequestParam("addressId") Long addressId) {
//        address.setAddressId(addressId);
//        Integer count = addressService.updateAddress(SingletonLoginUtils.getUserId(), address);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }

    /**
     * DELETE 收货地址
     *
     * @return
     */
//    @ApiOperation(value = "删除收货地址", notes = "根据url传过来的地址ID删除收货地址")
//    @DeleteMapping(value = "/address.del")
//    @ResponseBody
//    public OsResult addressDelete(@RequestParam("addressId") Long addressId) {
//        Integer count = addressService.deleteByAddressId(SingletonLoginUtils.getUserId(), addressId);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }


}
