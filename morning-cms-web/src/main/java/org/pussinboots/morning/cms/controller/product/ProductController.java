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
import org.pussinboots.morning.product.entity.*;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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
	 *
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
	 *
	 * @return
	 */
	@ApiOperation(value = "获取商品列表", notes = "根据分页信息/搜索内容")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/")
	@ResponseBody
	public Object listProduct(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
		BasePageDTO<Product> basePageDTO = productService.listByPage(pageInfo, search);
		return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
	}


	/**
	 * GET 商品图片页面
	 *
	 * @return
	 */
	@ApiOperation(value = "商品图片页面", notes = "商品图片页面")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/{productId}/list")
	public String getProductImagePage(Model model, @PathVariable("productId") Long productId) {

		model.addAttribute("productId", productId);
		return "/modules/product/product_image_list";
	}

	/**
	 * GET 商品图片页面,
	 *
	 * @return
	 */
	@ApiOperation(value = "获取商品图片页面列表", notes = "根据分页信息/搜索内容")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/{productId}/lists")
	@ResponseBody
	public Object listProductImage(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search, @PathVariable("productId") Long productId) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();

		if (authorizingUser != null) {
			BasePageDTO<ProductImage> basePageDTO = productImageService.listByPage(pageInfo, search, productId);
			return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * GET 分类管理页面
	 *
	 * @return
	 */
	@ApiOperation(value = "更新分类页面", notes = "更新分类页面")
	@RequiresPermissions("product:detail:edit")
	@GetMapping(value = "/{productId}/categoryUpdate")
	public String getCategoryPage(Model model, @PathVariable("productId") Long productId) {
		ProductCategory productCategory = productCategoryService.selectByProductId(productId);
		if (productCategory != null) {
			model.addAttribute("productCategory", productCategory);
		} else {

			AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
			ProductCategory pc = new ProductCategory();
			pc.setProductId(productId);
			pc.setCreateTime(new Date());
			if (authorizingUser != null)
				pc.setCreateBy(authorizingUser.getRealName());
			model.addAttribute("productCategory", pc);
		}
		return "/modules/product/product_category_update";
	}


	/**
	 * PUT 更新商品详情信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新商品详情信息", notes = "根据productId修改")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/categoryUpdate")
	@ResponseBody
	public Object updateProductCategory(ProductCategory productCategory) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			Integer count = productCategoryService.updateProductCategory(productCategory, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * GET 修改商品图片详情信息
	 *
	 * @return
	 */
	@ApiOperation(value = "修改商品图片详情信息", notes = "修改商品图片详情信息")
	@RequiresPermissions("product:detail:edit")
	@GetMapping(value = "/{picImgId}/updateImg")
	public String getUpdateProductImagelPage(Model model, @PathVariable("picImgId") Long picImgId) {

		ProductImage productImage = productImageService.selectById(picImgId);
		model.addAttribute("productImage", productImage);

		return "/modules/product/product_image_update";
	}

	/**
	 * PUT 修改商品图片详情信息
	 *
	 * @return
	 */
	@ApiOperation(value = "修改商品图片详情信息", notes = "根据productId修改")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/{picImgId}/updateImg")
	@ResponseBody
	public Object updateProductImage(ProductImage productImage, @PathVariable("picImgId") Long picImgId) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {

			Integer count = productImageService.updateProductImage(productImage, authorizingUser.getUserName(), picImgId);
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * DELETE 商品图片
	 *
	 * @return
	 */
	@ApiOperation(value = "删除商品图片", notes = "根据url传过来的商品图片编号删除商品图片")
	@DeleteMapping(value = "/delete/{picImgId}")
	@ResponseBody
	public Object favoriteDelete(@PathVariable("picImgId") Long picImgId) {
		Integer count = productImageService.deleteByPicImgId(picImgId);
		return new CmsResult(CommonReturnCode.SUCCESS, count);
	}

//	@ApiOperation(value = "商品参数页面", notes = "商品参数页面")
//	@RequiresPermissions("product:list:view")
//	@GetMapping(value = "/{productId}/categoryList")
//	public String getProducCategoryPage(Model model, @PathVariable("productId") Long productId) {
//
//		model.addAttribute("productId", productId);
//		return "/modules/product/product_Category_list";
//	}

	/**
	 * GET 创建product
	 *
	 * @return
	 */
	@ApiOperation(value = "创建商品", notes = "创建广告页面")
	@RequiresPermissions("product:detail:create")
	@GetMapping(value = "/create")
	public String getInsertPage(Model model) {
		return "/modules/product/product_create";
	}

	/**
	 * POST 创建商品
	 *
	 * @return
	 */
	@ApiOperation(value = "创建商品", notes = "创建商品")
	@RequiresPermissions("product:detail:create")
	@PostMapping(value = "")
	@ResponseBody
	public Object insert(Product product) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			Integer count = productService.insertProduct(product, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}


	/**
	 * GET 更新商品信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新商品信息", notes = "更新商品信息")
	@RequiresPermissions("product:detail:edit")
	@GetMapping(value = "/{productId}/edit")
	public String getUpdatePage(Model model, @PathVariable("productId") Long productId) {
		// 广告信息
		Product product = productService.selectById(productId);
		model.addAttribute("product", product);

		return "/modules/product/product_update";
	}

	/**
	 * PUT 更新商品信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新商品信息", notes = "根据ID修改")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/{productId}")
	@ResponseBody
	public Object update(Product product, @PathVariable("productId") Long productId,
						 @RequestParam(value = "showInNav", defaultValue = "0") Integer showInNav,
						 @RequestParam(value = "showInShelve", defaultValue = "0") Integer showInShelve,
						 @RequestParam(value = "showInTop", defaultValue = "0") Integer showInTop,
						 @RequestParam(value = "showInHot", defaultValue = "0") Integer showInHot) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			// 更新商品信息
			product.setShowInHot(showInHot);
			product.setShowInShelve(showInShelve);
			product.setShowInNav(showInNav);
			product.setShowInTop(showInTop);
			Integer count = productService.updateProduct(product, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * GET 更新商品详情信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新商品详情信息", notes = "更新商品详情信息")
	@RequiresPermissions("product:detail:edit")
	@GetMapping(value = "/{productId}/update")
	public String getUpdateProductDetailPage(Model model, @PathVariable("productId") Long productId) {
		// 广告信息
		ProductDetail productDetail = productDetailService.selectByProductId(productId);
		if (productDetail != null) {

			model.addAttribute("productDetail", productDetail);
		} else {
			ProductDetail pd = new ProductDetail();
			pd.setProductId(productId);
			model.addAttribute("productDetail", pd);
		}

		return "/modules/product/product_detail_update";
	}

	/**
	 * PUT 更新商品详情信息
	 *
	 * @return
	 */
	@ApiOperation(value = "更新商品详情信息", notes = "根据productId修改")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/update")
	@ResponseBody
	public Object updateProductDetail(ProductDetail productDetail) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			Integer count = productDetailService.updateProductDetail(productDetail, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}


	/**
	 * GET 商品参数页面
	 *
	 * @return
	 */
	@ApiOperation(value = "商品参数页面", notes = "商品参数页面")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/{productId}/parameterView")
	public String getProducParametertPage(Model model, @PathVariable("productId") Long productId) {

		model.addAttribute("productId", productId);
		return "/modules/product/product_parameter_list";
	}


	@ApiOperation(value = "获取商品参数列表", notes = "根据分页信息/搜索内容获取参数列表")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/{productId}/parameterLists")
	@ResponseBody
	public Object listParameter(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search,
								@PathVariable("productId") Long productId) {
		BasePageDTO<ProductParameter> basePageDTO = productParameterService.listByPage(pageInfo, search, productId);
		return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
	}
}

	/*@ApiOperation(value = "创建商品", notes = "创建广告页面")
	@RequiresPermissions("product:detail:create")
	@GetMapping(value = "/create")
	public String getInsertPage(Model model) {
		return "/modules/product/product_create";
	}

}*/
