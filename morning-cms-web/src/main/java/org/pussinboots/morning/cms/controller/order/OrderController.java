package org.pussinboots.morning.cms.controller.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.pussinboots.morning.cms.common.result.CmsPageResult;
import org.pussinboots.morning.cms.common.result.CmsResult;
import org.pussinboots.morning.cms.common.security.AuthorizingUser;
import org.pussinboots.morning.cms.common.util.SingletonLoginUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.order.entity.*;
import org.pussinboots.morning.order.pojo.vo.OrderVO;
import org.pussinboots.morning.order.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
* 类名称：OrderController
* 类描述：订单管理表示层控制器
*
 */
@Controller
@RequestMapping(value = "/system/order")
@Api(value = "订单管理", description = "订单管理")
public class OrderController extends BaseController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderProductService orderProductService;
	@Autowired
	private IOrderShipmentService orderShipmentService;
	@Autowired
	private IOrderStatusService orderStatusService;
	

	/**
	 * GET 订单管理页面
	 * @return
	 */
	@ApiOperation(value = "订单管理页面", notes = "订单管理页面")
	@RequiresPermissions("order:list:view")
	@GetMapping(value = "/view")
	public String getorderPage(Model model) {
		return "/modules/order/order_list"; /* 订单列表返回路径 */
	}
	
	/**
	 * GET 订单列表,
	 * @return
	 */
	@ApiOperation(value = "获取订单列表", notes = "根据分页信息/搜索内容")
	@RequiresPermissions("order:list:view")
	@GetMapping(value = "/list")
	@ResponseBody
	public Object listorder(Long userId,PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
		BasePageDTO<OrderVO> basePageDTO = orderService.list(userId, pageInfo, search, search);
		return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
		/*return null;*/
	}
	/**
	 * GET 更新订单信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新订单信息", notes = "更新订单信息")
	@RequiresPermissions("order:list:edit")
	@GetMapping(value = "/{orderId}/edit")
	public String getUpdatePage(Model model, @PathVariable("orderId") Long orderId) {

		Order order = orderService.selectById(orderId);
		model.addAttribute("order", order);

		return "/modules/order/order_edit";
	}

	/**
	 * PUT 更新订单信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新订单信息", notes = "根据ID修改")
	@RequiresPermissions("order:list:edit")
	@PutMapping(value = "/{orderId}")
	@ResponseBody
	public Object update(Order order, @PathVariable("orderId") Long orderId) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			Integer count = orderService.updateOrder(order, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}


}
