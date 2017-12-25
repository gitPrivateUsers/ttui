//package org.pussinboots.morning.cms.controller.product;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.pussinboots.morning.cms.common.result.CmsResult;
//import org.pussinboots.morning.cms.common.security.AuthorizingUser;
//import org.pussinboots.morning.cms.common.util.SingletonLoginUtils;
//import org.pussinboots.morning.common.base.BaseController;
//import org.pussinboots.morning.common.constant.CommonReturnCode;
//import org.pussinboots.morning.product.entity.ProductDetail;
//import org.pussinboots.morning.product.service.IProductDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
///**
// *
//* 类描述：商品详情
//*
// */
//@Controller
//@RequestMapping(value = "/product/detail/info")
//@Api(value = "商品详情", description = "商品详情")
//public class ProductDetailController extends BaseController {
//
//	@Autowired
//	private IProductDetailService productDetailService;
//	/**
//	 * GET 更新商品详情信息
//	 * @return
//	 */
//	@ApiOperation(value = "更新商品详情信息", notes = "更新商品详情信息")
//	@RequiresPermissions("product:list:update")
//	@GetMapping(value = "/{productId}/update")
//	public String getUpdateProductDetailPage(Model model, @PathVariable("productId") Long productId) {
//		// 广告信息
//		ProductDetail productDetail = productDetailService.selectByProductId(productId);
//		model.addAttribute("productDetail", productDetail);
//
//		return "/modules/product/productDetail_update";
//	}
//
//	/**
//	 * PUT 更新商品详情信息
//	 * @return
//	 */
//	@ApiOperation(value = "更新商品详情信息", notes = "根据productId修改")
//	@RequiresPermissions("product:list:update")
//	@PutMapping(value = "/{productDetailId}")
//	@ResponseBody
//	public Object updateProductDetail(ProductDetail productDetail, @PathVariable("productDetailId") Long productDetailId) {
//
//		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//		if (authorizingUser != null) {
//			// 更新用户及广告记录
//			Integer count = productDetailService.updateProductDetail(productDetail, authorizingUser.getUserName(),productDetailId);
//			return new CmsResult(CommonReturnCode.SUCCESS, count);
//		} else {
//			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
//		}
//	}
//
//}
