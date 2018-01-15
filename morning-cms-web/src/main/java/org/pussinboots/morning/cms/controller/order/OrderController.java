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
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.order.entity.*;
import org.pussinboots.morning.order.pojo.vo.OrderVO;
import org.pussinboots.morning.order.service.*;
import org.pussinboots.morning.product.common.enums.QuestionSortEnum;
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.entity.Comment;
import org.pussinboots.morning.product.pojo.vo.CommentVO;
import org.pussinboots.morning.product.pojo.vo.CommentVOs;
import org.pussinboots.morning.product.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
	@Autowired
	private ICommentService commentService;
	

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
	}
	/**
	 * GET 更新订单信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新订单信息", notes = "更新订单信息")
	/*权限问题暂时还没有解决*/
	/*权限问题暂时还没有解决*/
	/*权限问题暂时还没有解决*/
	//@RequiresPermissions("order:list:edit")
	@GetMapping(value = "/{orderId}/edit")
	public String getUpdatePage(Model model, @PathVariable("orderId") Long orderId) {

		Order order = orderService.selectById(orderId);
		model.addAttribute("order", order);

		return "/modules/order/order_update";
	}

	/**
	 * PUT更新订单信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新订单信息", notes = "根据ID修改")
	//@RequiresPermissions("order:list:edit")
	@PutMapping(value = "/{orderId}")
	@ResponseBody
	public Object update(Order order, @PathVariable("orderId") Long orderId,
						 @RequestParam(value = "payType",defaultValue = "1")Integer payType,
						 @RequestParam(value = "shipmentTime",defaultValue ="1")Integer shipmentTime) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			order.setPayType(payType);
			order.setShipmentTime(shipmentTime);
			Integer count = orderService.updateOrder(order, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "更新订单配送信息", notes = "更新订单配送信息")
	/*权限问题暂时还没有解决*/
	/*权限问题暂时还没有解决*/
	/*权限问题暂时还没有解决*/
	//@RequiresPermissions("order:list:edit")
	@GetMapping(value = "/{orderId}/updateShipment")
	public String getUpdateShipmentPage(Model model, @PathVariable("orderId") Long orderId) {

		OrderShipment orderShipment = orderShipmentService.getByOrderId(orderId);
		model.addAttribute("orderShipment", orderShipment);

		return "/modules/order/order_shipment_update";
	}

	@ApiOperation(value = "更新订单配送信息", notes = "根据订单ID修改")
	//@RequiresPermissions("order:list:edit")
	@PutMapping(value = "/{orderShipmentId}/updateShipment")
	@ResponseBody
	public Object updateOrderShipment(OrderShipment orderShipment, @PathVariable("orderShipmentId") Long orderShipmentId) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			orderShipment.setUpdateTime(new Date());
			Integer count = orderShipmentService.updateById(orderShipment,orderShipmentId);
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * PUT 取消订单
	 * @return
	 */
	@ApiOperation(value = "取消订单", notes = "根据URL传过来的订单编号取消订单")
	@PutMapping(value = "/cancelOrder/{orderId}")
	@ResponseBody
	public Object cancelOrder(@PathVariable("orderId") Long orderId) {
		Order order=orderService.selectById(orderId);
		if(order!=null){

			Integer count = orderService.updateCancelOrder(order.getOrderNumber(),order.getUserId());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		}
		return new CmsResult(CommonReturnCode.FAILED, 0);
	}

	/**
	 * GET 商品评价
	 * @return
	 */
	@ApiOperation(value = "商品评论页面", notes = "商品评价")
	@GetMapping(value = "/reviews")
	public String getComment(Model model) {
		return "/modules/comment/comment_list";
	}

	/**
	 * GET 订单列表,
	 * @return
	 */
	@ApiOperation(value = "商品评论列表", notes = "根据分页信息/搜索内容")
//	@RequiresPermissions("order:list:view")
	@GetMapping(value = "/reviews/list")
	@ResponseBody
	public Object commentList(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {

		BasePageDTO<CommentVOs> basePageDTO = commentService.listCommentPage(pageInfo, search);
		return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
	}

	/**
	 * GET 评论更新页面
	 * @return
	 */
	@ApiOperation(value = "商品评论更新", notes = "商品评论更新")
//	@RequiresPermissions("order:list:view")
	@GetMapping(value = "/comment/{commentId}/edit")
	@ResponseBody
	public String commentUpdatePage(Model model, @PathVariable("commentId") Long commentId) {

		Comment comment = commentService.selectById(commentId);
		model.addAttribute("comment",comment);
		return "modules/comment/comment_update";
	}

	/**
	 * PUT 强制删除评论
	 * @return
	 */
	@ApiOperation(value = "强制删除评论", notes = "强制删除评论")
//	@RequiresPermissions("product:category:edit")
	@PutMapping(value = "/comment/delete/{commentId}")
	@ResponseBody
	public Object delete(@PathVariable("commentId") Long commentId) {
//		List<Category> categoryList = categoryService.selectParentId(categoryId);
//		for (Category ca : categoryList) {
//			categoryService.deleteById(ca.getCategoryId());
//		}
//		boolean b=  categoryService.deleteById(categoryId);
		boolean b = commentService.deleteById(commentId);

		return new CmsResult(CommonReturnCode.SUCCESS, b);
	}
}
