package org.pussinboots.morning.os.controller.wxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.online.common.enums.AdvertTypeEnum;
import org.pussinboots.morning.online.common.enums.NavigationBarTypeEnum;
import org.pussinboots.morning.online.entity.AdvertDetail;
import org.pussinboots.morning.online.entity.NavigationBar;
import org.pussinboots.morning.online.service.IAdvertDetailService;
import org.pussinboots.morning.online.service.INavigationBarService;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.product.common.enums.ProductRecommendTypeEnum;
import org.pussinboots.morning.product.common.enums.ProductSortEnum;
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.entity.ProductRecommend;
import org.pussinboots.morning.product.pojo.vo.ProductVO;
import org.pussinboots.morning.product.service.ICategoryService;
import org.pussinboots.morning.product.service.IProductCategoryService;
import org.pussinboots.morning.product.service.IProductRecommendService;
import org.pussinboots.morning.product.service.IRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* 项目名称：morning-wx-web Maven Webapp
* 类名称：wxService
* 类描述：微信小程序api
* 创建人：zhancl
*
 */
@Controller
@Api(value = "微信小程序api", description = "微信小程序api")
public class wxService extends BaseController {
	
	@Autowired
	private IAdvertDetailService advertDetailService;
	@Autowired
	private INavigationBarService navigationBarService;
	@Autowired
	private IProductRecommendService productRecommendService;
	@Autowired
	private IRecommendService recommendService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IProductCategoryService productCategoryService;

	/**
	 * 首页数据结果集合
	 *
	 * @return Object
	 */
	@ApiOperation(value = "首页json", notes = "首页展示json")
	@GetMapping(value = "/index.json")
	public
	@ResponseBody
	Object index() {
		Map<String, Object> model = new HashMap<String, Object>();
		// 首页轮播广告列表
		List<AdvertDetail> indexCarouselImgs = advertDetailService.listByAdvertId(AdvertTypeEnum.INDEX_CAROUSEL.getType());
		model.put("indexCarouselImgs", indexCarouselImgs);
		//新品推荐 indexProductRecommendNew
		List<ProductVO> indexProductRecommendNew = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.NEW.getType());
		model.put("indexProductRecommendNew", indexProductRecommendNew);
		//推荐商品  productRecommend
		List<ProductVO> indexProductRecommend = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.POPULAR.getType());
		model.put("indexProductRecommend", indexProductRecommend);
		return new OsResult(StatusEnum.NORMAL.getStatus(), String.valueOf(JSONObject.fromObject(model)));
	}
	/**
	 * 分类数据结果集合
	 * product.category.json?limit=99
	 * @return Object
	 */
	@ApiOperation(value = "类目列表", notes = "类目列表")
	@GetMapping(value = "/product.category.json")
	public
	@ResponseBody Object list(
					   @RequestParam(value = "categoryId", required = false, defaultValue = "1") String reqCategoryId,
					   @RequestParam(value = "sort", required = false, defaultValue = "0") String reqSort,
					   @RequestParam(value = "page", required = false, defaultValue = "1") String reqPage,
					   @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {

		// 请求参数:类目ID,如果类目ID不存在或者不为Long类型,则默认1/全部商品
		Long categoryId = StringUtils.isNumeric(reqCategoryId) ? Long.valueOf(reqCategoryId) : 1;
		// 请求参数:排序方式,如果排序方式不存在或者不为Integer类型,则默认0/推荐排序
		Integer sort = StringUtils.isNumeric(reqSort) ? Integer.valueOf(reqSort) : ProductSortEnum.RECOMMEND.getType();
		// 请求参数:分页,如果分页不存在或者不为Integer类型,则默认1/默认页数
		Integer page = StringUtils.isNumeric(reqPage) ? Integer.valueOf(reqPage) : 1;

		// 查找当前类目信息
		Category category = categoryService.getById(categoryId, StatusEnum.SHOW.getStatus());
		if (category != null) {

			// 通过类目ID、排序、分页查找商品列表
			PageInfo pageInfo = new PageInfo(page, limit, ProductSortEnum.typeOf(sort).getSort(),
					ProductSortEnum.typeOf(sort).getOrder());
			BasePageDTO<ProductVO> basePageDTO = productCategoryService.listProducts(categoryId, pageInfo);

			// 根据类目ID查找子类目
			List<Category> lowerCategories = categoryService.listLowerCategories(categoryId, StatusEnum.SHOW.getStatus());

			// 根据类目ID查找上级类目列表
			List<Category> upperCategories = categoryService.listUpperCategories(categoryId, StatusEnum.SHOW.getStatus());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("sort", ProductSortEnum.typeOf(sort).getType());// 排序方式
			model.put("category", category);// 当前类目信息
			model.put("products", basePageDTO.getList());// 商品列表
			model.put("pageInfo", basePageDTO.getPageInfo()); // 分页信息
			model.put("lowerCategories", lowerCategories);// 子类目列表
			model.put("supperCategories", upperCategories);// 父类目列表

			return new OsResult(StatusEnum.NORMAL.getStatus(), String.valueOf(JSONObject.fromObject(model)));
		}
		return new OsResult(StatusEnum.FAIL_PRODUCT_CATEGORY.getStatus(),StatusEnum.FAIL_PRODUCT_CATEGORY.getStateInfo());
	}


	/**
	 * GET 搜索列表
	 * @return Object
	 */
	@ApiOperation(value = "搜索列表", notes = "搜索列表")
	@GetMapping(value = "/search.product.category.json")
	public @ResponseBody Object search(
						 @RequestParam(value = "search", required = false, defaultValue = "") String search,
						 @RequestParam(value = "sort", required = false, defaultValue = "0") String reqSort,
						 @RequestParam(value = "page", required = false, defaultValue = "1") String reqPage,
						 @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {
		// 请求参数:排序方式,如果排序方式不存在或者不为Integer类型,则默认0/推荐排序
		Integer sort = StringUtils.isNumeric(reqSort) ? Integer.valueOf(reqSort) : ProductSortEnum.RECOMMEND.getType();
		// 请求参数:分页,如果分页不存在或者不为Integer类型,则默认1/默认页数
		Integer page = StringUtils.isNumeric(reqPage) ? Integer.valueOf(reqPage) : 1;

		// 通过搜索内容、排序、分页查找商品列表
		PageInfo pageInfo = new PageInfo(page, limit, ProductSortEnum.typeOf(sort).getSort(),
				ProductSortEnum.typeOf(sort).getOrder());
		BasePageDTO<ProductVO> basePageDTO = productCategoryService.listBySearch(search, pageInfo);

		// 根据类目ID查找子类目
		List<Category> lowerCategories = categoryService.listLowerCategories(1L, StatusEnum.SHOW.getStatus());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("search", search); // 搜索内容
		model.put("sort", ProductSortEnum.typeOf(sort).getType());// 排序方式
		model.put("products", basePageDTO.getList());// 商品列表
		model.put("pageInfo", basePageDTO.getPageInfo()); // 分页信息
		model.put("lowerCategories", lowerCategories);// 子类目列表
		return new OsResult(StatusEnum.NORMAL.getStatus(), String.valueOf(JSONObject.fromObject(model)));
	}

	/**
	 * 商品数据结果集合
	 *
	 * @return Object
	 */


	/**
	 * 购物车数据结果集合
	 *
	 * @return Object
	 */


	/**
	 * 订单数据结果集合
	 *
	 * @return Object
	 */


	/**
	 * 个人中心数据结果集合
	 *
	 * @return Object
	 */


}
