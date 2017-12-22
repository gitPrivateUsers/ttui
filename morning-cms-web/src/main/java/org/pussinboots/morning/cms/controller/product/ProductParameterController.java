package org.pussinboots.morning.cms.controller.product;

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
import org.pussinboots.morning.product.entity.Product;
import org.pussinboots.morning.product.entity.ProductParameter;
import org.pussinboots.morning.product.entity.ProductImage;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
* 类名称：ProductController
* 类描述：商品参数表示层控制器
*
 */
@Controller
@RequestMapping(value = "/product/detail/")
@Api(value = "商品参数管理", description = "商品参数管理")
public class ProductParameterController extends BaseController {


	@Autowired
	private IProductParameterService productParameterService;

	/**
	 * GET 商品管理页面
	 * @return
	 */
	@ApiOperation(value = "商品参数页面", notes = "商品参数页面")
	@RequiresPermissions("product:parameter:view")
	@GetMapping(value = "/view")
	public String getProductPage(Model model) {
		return "/modules/product/product_parameter_list";
	}
	

	@ApiOperation(value = "获取商品参数列表", notes = "根据分页信息/搜索内容/父类目ID获取参数列表")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/list")
	@ResponseBody
	public Object listParameter(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search,
							 @PathVariable("productId") Long productId) {
		BasePageDTO<ProductParameter> basePageDTO = productParameterService.listByPage(pageInfo, search, productId);
		return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
	}

	/*@ApiOperation(value = "创建商品", notes = "创建广告页面")
	@RequiresPermissions("product:detail:create")
	@GetMapping(value = "/create")
	public String getInsertPage(Model model) {
		return "/modules/product/product_create";
	}

	*//**
	 * POST 创建商品
	 * @return
	 *//*
	@ApiOperation(value = "创建商品", notes = "创建商品")
	@RequiresPermissions("product:detail:create")
	@PostMapping(value = "")
	@ResponseBody
	public Object insert(ProductParameter productParameter) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			//Integer count = productService.insertProduct(productParameter, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}*/

	/**
	 * GET 更新商品信息
	 * @return
	 */
	@ApiOperation(value = "修改参数页面", notes = "更新参数页面")
	@RequiresPermissions("product:parameter:edit")
	@GetMapping(value = "/{productParameterId}/edit")
	public String getUpdatePage(Model model, @PathVariable("productParameterId") Long productParameterId) {
		// 类目信息
		ProductParameter productParameter = productParameterService.selectById(productParameterId);
		model.addAttribute("productParameter", productParameter);

		return "/modules/category/product_category_update";
	}

	/**
	 * PUT 更新商品信息
	 * @return
	 */
	@ApiOperation(value = "更新商品信息", notes = "根据ID修改")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/{productParameterId}")
	@ResponseBody
	public Object update(ProductParameter productParameter, @PathVariable("productParameterId") Long productParameterId) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			// 更新商品信息
			Integer count = productParameterService.updateProductParameter(productParameter, authorizingUser.getUserName(),productParameterId);
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}
}
