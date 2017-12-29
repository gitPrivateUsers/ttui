package org.pussinboots.morning.os.controller.wxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.util.SingletonLoginUtils;
import org.pussinboots.morning.user.entity.Address;
import org.pussinboots.morning.user.entity.Favorite;
import org.pussinboots.morning.user.pojo.vo.UserVO;
import org.pussinboots.morning.user.service.IAddressService;
import org.pussinboots.morning.user.service.IFavoriteService;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private IUserService userService;
    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private IAddressService addressService;


    /**
     * 个人中心数据结果集合
     * <p>
     * GET 我的个人中心
     *
     * @return
     */
    @ApiOperation(value = "我的个人中心", notes = "我的个人中心")
    @GetMapping(value = "/portal.my")
    @ResponseBody
    public OsResult portal() {

        Map<String, Object> model = new HashMap<String, Object>();
        if (SingletonLoginUtils.getUserId() == null) {
            //未登录
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        }
        UserVO userVO = userService.getById(SingletonLoginUtils.getUserId());
        model.put("userVO", userVO);

        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
    }

    /**
     * GET 喜欢的商品
     *
     * @return
     */
    @ApiOperation(value = "喜欢的商品", notes = "喜欢的商品")
    @GetMapping(value = "/favorite.product")
    @ResponseBody
    public OsResult favorite(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {

        PageInfo pageInfo = new PageInfo(limit, page);
        Map<String, Object> model = new HashMap<String, Object>();
        BasePageDTO<Favorite> basePageDTO = favoriteService.listByUserId(SingletonLoginUtils.getUserId(), pageInfo);

        model.put("favorites", basePageDTO.getList());
        model.put("pageInfo", basePageDTO.getPageInfo());

        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
    }

    /**
     * DELETE 喜欢的商品
     *
     * @return
     */
    @ApiOperation(value = "删除喜欢的商品", notes = "根据url传过来的商品编号删除喜欢的商品")
    @DeleteMapping(value = "/favorite.product.del")
    @ResponseBody
    public OsResult favoriteDelete(@RequestParam("productNumber") Long productNumber) {
        Integer count = favoriteService.deleteByProductNumber(SingletonLoginUtils.getUserId(), productNumber);
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }

    /**
     * GET 收货地址
     *
     * @return
     */
    @ApiOperation(value = "收货地址列表", notes = "收货地址列表")
    @GetMapping(value = "/address.list")
    @ResponseBody
    public OsResult address(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "11") Integer limit) {

        Map<String, Object> model = new HashMap<String, Object>();
        PageInfo pageInfo = new PageInfo(limit, page);
        BasePageDTO<Address> basePageDTO = addressService.listByUserId(SingletonLoginUtils.getUserId(), pageInfo);
        model.put("addresses", basePageDTO.getList());
        model.put("pageInfo", basePageDTO.getPageInfo());

        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
    }

    /**
     * POST 创建收货地址
     *
     * @return
     */
    @ApiOperation(value = "创建收货地址", notes = "根据url传过来的收货地址信息创建收货地址")
    @PostMapping(value = "/address.create")
    @ResponseBody
    public OsResult addressCreate(Address address) {
        Integer count = addressService.insertAddress(SingletonLoginUtils.getUserId(), address);
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }

    /**
     * PUT 更新收货地址
     *
     * @return
     */
    @ApiOperation(value = "更新收货地址", notes = "根据url传过来的收获地址ID和收货地址信息更新收货地址")
    @PutMapping(value = "/address.edit")
    @ResponseBody
    public OsResult addressUpdate(Address address, @RequestParam("addressId") Long addressId) {
        address.setAddressId(addressId);
        Integer count = addressService.updateAddress(SingletonLoginUtils.getUserId(), address);
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }

    /**
     * DELETE 收货地址
     *
     * @return
     */
    @ApiOperation(value = "删除收货地址", notes = "根据url传过来的地址ID删除收货地址")
    @DeleteMapping(value = "/address.del")
    @ResponseBody
    public OsResult addressDelete(@RequestParam("addressId") Long addressId) {
        Integer count = addressService.deleteByAddressId(SingletonLoginUtils.getUserId(), addressId);
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }


}
