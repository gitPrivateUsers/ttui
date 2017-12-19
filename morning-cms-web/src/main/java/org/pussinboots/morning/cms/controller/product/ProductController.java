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
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.entity.Product;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
* 类名称：ProductController
* 类描述：商品管理表示层控制器
*
 */
@Controller
@RequestMapping(value = "/product/detail")
@Api(value = "商品管理", description = "商品管理")
public class ProductController extends BaseController {

	@Autowired
	private IProductService productService;
	@Autowired
	private IProductDetailService productDetailService;
	@Autowired
	private IProductImageService productImageService;
	@Autowired
	private IProductAttributeService productAttributeService;
	@Autowired
	private IProductParameterService productParameterService;
	@Autowired
	private IProductCategoryService productCategoryService;
	@Autowired
	private IProductRecommendService productRecommendService;
	@Autowired
	private IProductSpecificationService productSpecificationService;

	/**
	 * GET 商品管理页面
	 * @return
	 */
	@ApiOperation(value = "商品管理页面", notes = "商品管理页面")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/view")
	public String getProductPage(Model model) {
		return "/modules/product/product_list";
	}
	
	/**
	 * GET 商品列表,
	 * @return
	 */
	@ApiOperation(value = "获取商品列表", notes = "根据分页信息/搜索内容")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/list")
	@ResponseBody
	public Object listProduct(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
		BasePageDTO<Product> basePageDTO = productService.listByPage(pageInfo, search);
		return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
	}



}
