package org.pussinboots.morning.os.controller.wxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.online.entity.WxUserInfo;
import org.pussinboots.morning.order.common.enums.OrderStatusEnum;
import org.pussinboots.morning.order.entity.Order;
import org.pussinboots.morning.order.entity.OrderProduct;
import org.pussinboots.morning.order.entity.OrderShipment;
import org.pussinboots.morning.order.pojo.vo.OrderShoppingCartVO;
import org.pussinboots.morning.order.service.IOrderProductService;
import org.pussinboots.morning.order.service.IOrderService;
import org.pussinboots.morning.order.service.IOrderShipmentService;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.util.SingletonLoginUtils;
import org.pussinboots.morning.product.pojo.vo.CartVO;
import org.pussinboots.morning.product.pojo.vo.ShoppingCartVO;
import org.pussinboots.morning.product.service.*;
import org.pussinboots.morning.user.entity.Address;
import org.pussinboots.morning.user.service.IAddressService;
import org.pussinboots.morning.online.service.IWxUserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "商品订单", description = "商品订单")
public class wxApiOrderService extends BaseController {

    @Autowired
    private IAddressService addressService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderProductService orderProductService;
    @Autowired
    private IOrderShipmentService orderShipmentService;
    @Autowired
    private IWxUserInfoService wxUserInfoService;


    /**
     * GET 填写订单信息
     *
     * @return
     */
    @ApiOperation(value = "填写订单信息", notes = "填写订单信息")
    @GetMapping(value = "/order.info")
    @ResponseBody
    public OsResult checkout(@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = getUserInfoByToken(token);
        if (wx == null)
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        Map<String, Object> model = new HashMap<String, Object>();
        // 收获地址
        List<Address> addresses = addressService.listAddress(wx.getOsUserId());
        model.put("addresses", addresses);
        // 购物车选中商品
        CartVO cartVO = shoppingCartService.list(wx.getOsUserId(), StatusEnum.CHECKED.getStatus());
        model.put("cartVO", cartVO);

        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));

    }

    /**
     * GET 确认订单
     *
     * @return
     */
    @ApiOperation(value = "确认订单", notes = "确认订单")
    @GetMapping(value = "/order.confirm")
    @ResponseBody
    public OsResult confirmShow(@RequestParam("orderNumber") Long orderNumber,@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = getUserInfoByToken(token);
        if (wx == null)
            return new OsResult(CommonReturnCode.UNAUTHORIZED);

        Order order = orderService.getByOrderNumber(orderNumber, wx.getOsUserId(),
                OrderStatusEnum.SUBMIT_ORDERS.getStatus());

        if (order != null) {

            Map<String, Object> model = new HashMap<String, Object>();
            List<OrderProduct> orderProducts = orderProductService.listByOrderId(order.getOrderId());

            OrderShipment orderShipment = orderShipmentService.getByOrderId(order.getOrderId());

            model.put("order", order); // 订单信息
            model.put("orderProducts", orderProducts);// 订单详情表
            model.put("orderShipment", orderShipment);// 订单配送表
            return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
        }
        return new OsResult(CommonReturnCode.FAILED);
    }

    /**
     * POST 提交订单
     *
     * @return
     */
    @ApiOperation(value = "提交订单", notes = "提交订单")
    @PostMapping(value = "/order.commit")
    @ResponseBody
    public Object confirm(Order order, @RequestParam(value = "addressId", required = true) Long addressId,@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = getUserInfoByToken(token);
        if (wx == null)
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        // 收货地址
        Address address = addressService.getAddress(addressId, wx.getOsUserId());
        if (address != null) {
            OrderShipment orderShipment = new OrderShipment();
            BeanUtils.copyProperties(address, orderShipment);

            // 购物车选中商品
            CartVO cartVO = shoppingCartService.list(wx.getOsUserId(), StatusEnum.CHECKED.getStatus());
            if (!cartVO.getShoppingCartVOs().isEmpty()) {
                order.setBuyNumber(cartVO.getTotalNumber());// 订单总数量
                order.setOrderAmount(cartVO.getTotalPrice());// 订单总价格
                order.setOrderScore(cartVO.getTotalScore());// 订单总积分

                // 遍历购物车选中商品列表
                List<OrderShoppingCartVO> orderShoppingCartVOs = new ArrayList<OrderShoppingCartVO>();
                for (ShoppingCartVO vo : cartVO.getShoppingCartVOs()) {
                    OrderShoppingCartVO orderShoppingCartVO = new OrderShoppingCartVO();
                    BeanUtils.copyProperties(vo, orderShoppingCartVO);
                    orderShoppingCartVOs.add(orderShoppingCartVO);
                }
                Long orderNumber = orderService.insertOrder(order, orderShipment, orderShoppingCartVOs,
                        wx.getOsUserId());

                if (orderNumber != null) {
                    shoppingCartService.deleteCheckStatus(wx.getOsUserId());
                    return new OsResult(CommonReturnCode.SUCCESS, orderNumber.toString());
                    // TODO Long
                    // 17位传送末尾精读损失,例14944366378872495前台接收变成14944366378872494,解决方法toString,原因未知
                } else {
                    return new OsResult(CommonReturnCode.UNKNOWN_ERROR.getCode(),
                            CommonReturnCode.UNKNOWN_ERROR.getMessage());
                }
            } else {
                return new OsResult(CommonReturnCode.BAD_REQUEST.getCode(), "请选择想要购买的商品!");
            }
        } else {
            return new OsResult(CommonReturnCode.BAD_REQUEST.getCode(), "请选择正确的收货地址!");
        }
    }

    /**
     * PUT 取消订单
     *
     * @return
     */
    @ApiOperation(value = "取消订单", notes = "根据URL传过来的订单编号取消订单")
    @PutMapping(value = "/order.cancel")
    @ResponseBody
    public Object cancelOrder(@RequestParam(value = "orderNumber", required = true) Long orderNumber,@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = getUserInfoByToken(token);
        if (wx == null)
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        Integer count = orderService.updateCancelOrder(orderNumber, wx.getOsUserId());
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }

    /**
     * PUT 修改送货时间
     *
     * @return
     */
    @ApiOperation(value = "修改送货时间", notes = "根据URL传过来的订单编号修改送货时间")
    @PutMapping(value = "/order.update.time")
    @ResponseBody
    public Object timeOrder(@RequestParam(value = "orderNumber", required = true) Long orderNumber,
                            @RequestParam(value = "shipmentTime", required = true) Integer shipmentTime,@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = getUserInfoByToken(token);
        if (wx == null)
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        Integer count = orderService.updateShipmentTime(orderNumber, shipmentTime, wx.getOsUserId());
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }

    /**
     * PUT 修改收货地址
     *
     * @return
     */
    @ApiOperation(value = "修改收货地址", notes = "根据URL传过来的收货地址信息修改收货地址")
    @PutMapping(value = "/order.shipment")
    @ResponseBody
    public Object orderShipment(OrderShipment orderShipment,@RequestParam(value = "token", required = true) String token) {
        WxUserInfo wx = getUserInfoByToken(token);
        if (wx == null)
            return new OsResult(CommonReturnCode.UNAUTHORIZED);
        Integer count = orderShipmentService.update(orderShipment, wx.getOsUserId());
        return new OsResult(CommonReturnCode.SUCCESS, count);
    }

    private WxUserInfo getUserInfoByToken(String token) {
        WxUserInfo wx = wxUserInfoService.getWxUserInfoByToken(token);
        if (wx != null) {
            return wx;
        }
        return null;
    }

}
