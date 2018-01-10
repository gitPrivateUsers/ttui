package org.pussinboots.morning.os.controller.wxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.online.entity.WxUserInfo;
import org.pussinboots.morning.online.service.IWxUserInfoService;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.product.pojo.vo.CartVO;
import org.pussinboots.morning.product.pojo.vo.ShoppingCartVO;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(value = "购物车", description = "购物车")
public class wxApiCartService extends BaseController {
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IWxUserInfoService wxUserInfoService;

    /**
     * 购物车数据结果集合
     *
     * @return
     */
    @ApiOperation(value = "添加购物车商品", notes = "添加购物车商品")
    @PostMapping(value = "/cart.create")
    @ResponseBody
    public OsResult create(
            @RequestParam(value = "productSpecNumber", required = true) Long productSpecNumber,
            @RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            shoppingCartService.insertShoppingCart(productSpecNumber, wx.getOsUserId());
            return new OsResult(CommonReturnCode.SUCCESS, productSpecNumber.toString());
        } else {
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

    /**
     * GET 成功加入购物车
     *
     * @return
     */
    @ApiOperation(value = "成功加入购物车", notes = "成功加入购物车")
    @GetMapping(value = "/add.cart.success")
    @ResponseBody
    public Object view(@RequestParam("productSpecNumber") Long productSpecNumber,
                       @RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            // 用户已登录,查看数据库中购物车列表是否有该商品
            ShoppingCartVO shoppingCartVO = shoppingCartService.getCart(wx.getOsUserId(), productSpecNumber);
            if (shoppingCartVO == null) {
                // 重定向到购物车列表
                return redirectTo("/cart.list");
            }
            return new OsResult(CommonReturnCode.SUCCESS, shoppingCartVO);
        } else {
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

    /**
     * GET 购物车列表
     *
     * @return
     */
    @ApiOperation(value = "购物车列表", notes = "购物车列表")
    @GetMapping(value = "/cart.list")
    @ResponseBody
    public OsResult list(@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            CartVO cartVO = shoppingCartService.list(wx.getOsUserId(), StatusEnum.ALL.getStatus());
            return new OsResult(CommonReturnCode.SUCCESS, cartVO);
        } else {
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }
//
//    /**
//     * GET 导航栏购物车
//     *
//     * @return
//     */
//    @ApiOperation(value = "导航栏购物车", notes = "导航栏购物车")
//    @GetMapping(value = "/cart.topbar")
//    @ResponseBody
//    public OsResult topbarCart() {
//        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//        if (authorizingUser != null) {
//            Map<String, Object> model = new HashMap<String, Object>();
//            CartVO cartVO = shoppingCartService.list(SingletonLoginUtils.getUserId(), StatusEnum.ALL.getStatus());
//            model.put("cartVO", cartVO);
//            return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//        }
//        return new OsResult(CommonReturnCode.FAILED);
//    }

    /**
     * GET 购物车商品数量
     *
     * @return
     */
    @ApiOperation(value = "购物车商品数量", notes = "购物车商品数量")
    @GetMapping(value = "/cart.number")
    @ResponseBody
    public OsResult cartNumber(@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            CartVO cartVO = shoppingCartService.list(wx.getOsUserId(), StatusEnum.ALL.getStatus());
            if (cartVO != null) {
                return new OsResult(CommonReturnCode.SUCCESS, cartVO.getTotalNumber());
            } else {
                return new OsResult(CommonReturnCode.FAILED);
            }
        } else {
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        }

    }

    /**
     * DELETE 删除购物车商品
     *
     * @return
     */
    @ApiOperation(value = "删除购物车商品", notes = "根据URL传过来的商品规格ID删除购物车商品")
    @DeleteMapping(value = "/cart.delete")
    @ResponseBody
    public OsResult delete(@RequestParam("productSpecNumber") Long productSpecNumber, @RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            Integer count = shoppingCartService.delete(productSpecNumber, wx.getOsUserId());
            return new OsResult(CommonReturnCode.SUCCESS, count);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }

    /**
     * PUT 修改购物车商品数量
     *
     * @return
     */
    @ApiOperation(value = "修改购物车商品数量", notes = "根据URL传过来的商品规格ID修改购物车商品数量")
    @PutMapping(value = "/cart.edit")
    @ResponseBody
    public OsResult updateNumber(@RequestParam("productSpecNumber") Long productSpecNumber,
                                 @RequestParam(value = "buyNumber", required = true) Integer buyNumber, @RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            Integer count = shoppingCartService.updateBuyNumber(productSpecNumber, wx.getOsUserId(),
                    buyNumber);
            return new OsResult(CommonReturnCode.SUCCESS, count);
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }

    /**
     * PUT 修改购物车商品选中状态
     *
     * @return
     */
    @ApiOperation(value = "修改购物车商品选中状态", notes = "根据URL传过来的商品规格ID修改购物车商品选中状态")
    @PutMapping(value = "/cart.add.status")
    @ResponseBody
    public OsResult updateStatus(@RequestParam("productSpecNumber") Long productSpecNumber,
                                 @RequestParam(value = "checkStatus", required = true) Integer checkStatus, @RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            if (StatusEnum.CHECKED.getStatus().equals(checkStatus)) {
                Integer count = shoppingCartService.updateStatus(productSpecNumber, wx.getOsUserId(),
                        StatusEnum.UNCHECKED.getStatus());
                return new OsResult(CommonReturnCode.SUCCESS, count);
            } else if (StatusEnum.UNCHECKED.getStatus().equals(checkStatus)) {
                Integer count = shoppingCartService.updateStatus(productSpecNumber, wx.getOsUserId(),
                        StatusEnum.CHECKED.getStatus());
                return new OsResult(CommonReturnCode.SUCCESS, count);
            }
            return new OsResult(CommonReturnCode.BAD_REQUEST.getCode(), CommonReturnCode.BAD_REQUEST.getMessage());
        }
        return new OsResult(CommonReturnCode.UNAUTHORIZED);
    }
}
