package org.pussinboots.morning.cms.controller.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.spi.LoggerFactory;
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
import org.pussinboots.morning.product.entity.*;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;


/**
 * 类名称：ProductController
 * 类描述：商品管理表示层控制器
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

	@ApiOperation(value = "推荐页面", notes = "推荐页面")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/recommendView")
	public String getRecommendPage(Model model) {
		return "/modules/recommend/recommend_list";
	}

	@ApiOperation(value = "获取推荐列表", notes = "根据分页信息获取推荐列表")
	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/recommendList")
	@ResponseBody
	public Object recommendList(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
		BasePageDTO<ProductRecommend> BasePageDTO = productRecommendService.listByPage(pageInfo, search);
		return new CmsPageResult(BasePageDTO.getList(), BasePageDTO.getPageInfo().getTotal());
	}

	@ApiOperation(value = "创建推荐位页面", notes = "推荐位页面")
	@RequiresPermissions("product:detail:create")
	@GetMapping(value = "/recommendCreate")
	public String getInsertRecommend(Model model) {
		ProductRecommend productRecommend=new ProductRecommend();
		productRecommend.setStatus(StatusEnum.NORMAL.getStatus());
		model.addAttribute("productRecommend",productRecommend);
		return "/modules/recommend/recommend_create";
	}



	@ApiOperation(value = "创建一个推荐位", notes = "创建一个推荐位")
	@RequiresPermissions("product:detail:create")
	@PostMapping(value= "/recommendCreate")
	@ResponseBody
	public Object insertProductRecommend(ProductRecommend productRecommend) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			Integer count = productRecommendService.insertProductRecommend(productRecommend, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "更新推荐信息", notes = "更新推荐信息")
	@RequiresPermissions("product:detail:edit")
	@GetMapping(value = "/{recommendProductId}/productRecommendEdit")
	public String getUpdateByRecommendProductId(Model model, @PathVariable("recommendProductId") Long recommendProductId) {

		ProductRecommend productRecommend = productRecommendService.selectById(recommendProductId);
		model.addAttribute("productRecommend", productRecommend);

		return "/modules/recommend/recommend_update";
	}

	@ApiOperation(value = "更新推荐信息", notes = "根据推荐位ID更新推荐信息")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/{recommendProductId}/productRecommendEdit")
	@ResponseBody
	public Object UpdateByRecommendProductId(ProductRecommend productRecommend, @PathVariable("recommendProductId") Long recommendProductId) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null){
			Integer count = productRecommendService.updateProductRecommecd(productRecommend,authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		}
		return new CmsResult(CommonReturnCode.UNAUTHORIZED);
	}

	@ApiOperation(value = "删除推荐",notes = "根据url推荐位ID删除广告")
	@RequiresPermissions("product:detail:edit")
	@DeleteMapping(value = "/recommendProductDelete/{recommendProductId}")
	@ResponseBody
	public Object delete(@PathVariable("recommendProductId") Long recommendProductId){
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if(authorizingUser != null){
			Integer count = productRecommendService.deleteByRecommendProductId(recommendProductId);
			return new CmsResult(CommonReturnCode.SUCCESS,count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
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

	@ApiOperation(value = "商品图片", notes = "创建商品图片")
	@RequiresPermissions("product:detail:create")
	@GetMapping(value = "/addImg/{productId}/page")
	public String getInsertProductImagePage(Model model, @PathVariable("productId") Long productId) {
		model.addAttribute("productId", productId);
		return "/modules/product/product_image_create";
	}

	@ApiOperation(value = "创建保存商品图片信息", notes = "创建保存商品图片信息")
	@RequiresPermissions("product:detail:create")
	@PostMapping(value = "/save/addImg")
	@ResponseBody
	public Object saveInsertProductImage(ProductImage productImage,@RequestParam(value = "status", defaultValue = "0") Integer status ) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if(authorizingUser != null){
			productImage.setStatus(status);
			Integer count = productImageService.insertProductImage(productImage,authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS,count);
		}else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

//	@ApiOperation(value = "上传商品图片", notes = "上传商品图片")
//    @RequiresPermissions("product:detail:create")
//	@RequestMapping(value = "/upload/image")
//	@ResponseBody
//	public Map<String,Object> uploadPhoto(HttpServletRequest request, HttpServletResponse response, @RequestParam("myFile")MultipartFile myFile) {
//		Map<String, Object> json = new HashMap<String, Object>();
//
//		原文件名
//		String oldFileName = myFile.getOriginalFilename();
//		文件后缀
//		String suffix = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
//		获取文件前缀
//		String prefix = oldFileName.replace(suffix,"");
//		新文件名
//		String df = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//		Random r = new Random();
//		for (int i = 0;i<3;i++){
//			df += r.nextInt(10);
//		}
//		String newFileName = df + suffix;
//		上传文件的路径
//		String path = request.getServletContext().getRealPath("uploads\\photo\\");
//		String dateFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		path = path + File.separator + dateFile;
//		File file = new File(path);
//        logger.info("文件的上传路径是：{}", path);
//		不存在则创建
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		目标文件
//		File dest = new File(path + File.separator + newFileName);
//		上传
//		try {
//			myFile.transferTo(dest);
//			json.put("success","upload/photo/"+newFileName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return json ;
//	}

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
	public Object updateProductCategory(ProductCategory productCategory ) {

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
	public Object updateProductImage(ProductImage productImage, @PathVariable("picImgId") Long picImgId, @RequestParam(value = "status", defaultValue = "0") Integer status) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			productImage.setStatus(status);
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
	@RequiresPermissions("product:detail:edit")
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
	public Object insert(Product product,
						 @RequestParam(value = "showInNav", defaultValue = "0") Integer showInNav,
						 @RequestParam(value = "showInShelve", defaultValue = "0") Integer showInShelve,
						 @RequestParam(value = "showInTop", defaultValue = "0") Integer showInTop,
						 @RequestParam(value = "showInHot", defaultValue = "0") Integer showInHot) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			product.setShowInHot(showInHot);
			product.setShowInShelve(showInShelve);
			product.setShowInNav(showInNav);
			product.setShowInTop(showInTop);
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

	@ApiOperation(value = "创建商品参数", notes = "创建商品参数")
	@RequiresPermissions("product:detail:create")
	@GetMapping(value = "/{productId}/createProductParameter")
	public String getInsertProductParameterPage(Model model, @PathVariable("productId") Long productId) {
		model.addAttribute("productId", productId);
		return "/modules/product/product_parameter_create";
	}


	@ApiOperation(value = "创建商品参数", notes = "创建商品参数")
	@RequiresPermissions("product:detail:create")
	@PostMapping(value = "/createProductParameter")
	@ResponseBody
	public Object insertProductParameter(ProductParameter productParameter) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {
			Integer count = productParameterService.insertProductParameter(productParameter, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * GET 更新商品信息
	 * @return
	 */
	@ApiOperation(value = "更新参数页面", notes = "更新参数页面")
	@RequiresPermissions("product:detail:edit")
	@GetMapping(value = "/{productParameterId}/editProductParameter")
	public String getUpdateProductParameterPage(Model model, @PathVariable("productParameterId") Long productParameterId) {
		// 类目信息
		ProductParameter productParameter = productParameterService.selectById(productParameterId);
		model.addAttribute("productParameter", productParameter);

		return "/modules/product/product_parameter_update";
	}

	/**
	 * PUT 更新商品信息
	 * @return
	 */
	@ApiOperation(value = "更新商品参数信息", notes = "根据ID修改")
	@RequiresPermissions("product:detail:edit")
	@PutMapping(value = "/editProductParameters")
	@ResponseBody
	public Object updateProductParameter(ProductParameter productParameter) {

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if (authorizingUser != null) {

			Integer count = productParameterService.updateProductParameter(productParameter, authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS, count);
		} else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

	/**
	 * GET 商品类目选择页面
	 *
	 * @return
	 */
	@ApiOperation(value = "商品类目选择页面", notes = "商品类目选择页面")
//	@RequiresPermissions("product:list:view")
	@GetMapping(value = "/template")
	public String getProductTemplate(Model model) {
		return "/modules/product/product_specification_template";
	}

}


