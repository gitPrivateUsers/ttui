//package org.pussinboots.morning.os.controller.wxService;
//
//import com.alibaba.fastjson.JSON;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.pussinboots.morning.common.base.BaseController;
//import org.pussinboots.morning.common.base.BasePageDTO;
//import org.pussinboots.morning.common.constant.CommonReturnCode;
//import org.pussinboots.morning.common.enums.StatusEnum;
//import org.pussinboots.morning.common.support.page.PageInfo;
//import org.pussinboots.morning.online.common.enums.AdvertTypeEnum;
//import org.pussinboots.morning.online.entity.AdvertDetail;
//import org.pussinboots.morning.online.service.IAdvertDetailService;
//import org.pussinboots.morning.order.common.enums.OrderStatusEnum;
//import org.pussinboots.morning.order.entity.Order;
//import org.pussinboots.morning.order.entity.OrderProduct;
//import org.pussinboots.morning.order.entity.OrderShipment;
//import org.pussinboots.morning.order.pojo.vo.OrderShoppingCartVO;
//import org.pussinboots.morning.order.service.IOrderProductService;
//import org.pussinboots.morning.order.service.IOrderService;
//import org.pussinboots.morning.order.service.IOrderShipmentService;
//import org.pussinboots.morning.os.common.result.OsResult;
//import org.pussinboots.morning.os.common.security.AuthorizingUser;
//import org.pussinboots.morning.os.common.util.SingletonLoginUtils;
//import org.pussinboots.morning.product.common.constant.ProductConstantEnum;
//import org.pussinboots.morning.product.common.enums.ProductRecommendTypeEnum;
//import org.pussinboots.morning.product.common.enums.ProductSortEnum;
//import org.pussinboots.morning.product.entity.Category;
//import org.pussinboots.morning.product.entity.ProductAttribute;
//import org.pussinboots.morning.product.entity.ProductImage;
//import org.pussinboots.morning.product.entity.ProductParameter;
//import org.pussinboots.morning.product.pojo.dto.ProductSpecificationDTO;
//import org.pussinboots.morning.product.pojo.vo.CartVO;
//import org.pussinboots.morning.product.pojo.vo.ProductVO;
//import org.pussinboots.morning.product.pojo.vo.ShoppingCartVO;
//import org.pussinboots.morning.product.service.*;
//import org.pussinboots.morning.user.entity.Address;
//import org.pussinboots.morning.user.entity.Favorite;
//import org.pussinboots.morning.user.pojo.vo.UserVO;
//import org.pussinboots.morning.user.service.IAddressService;
//import org.pussinboots.morning.user.service.IFavoriteService;
//import org.pussinboots.morning.user.service.IUserService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 项目名称：morning-wx-web Maven Webapp
// * 类名称：wxService
// * 类描述：微信小程序api
// * 创建人：zhancl
// */
//@Controller
//@Api(value = "微信小程序api", description = "微信小程序api")
//public class wxService extends BaseController {
//
//    @Autowired
//    private IAdvertDetailService advertDetailService;
//    @Autowired
//    private IProductRecommendService productRecommendService;
//    @Autowired
//    private IProductCategoryService productCategoryService;
//
//    @Autowired
//    private IProductService productService;
//    @Autowired
//    private ICategoryService categoryService;
//    @Autowired
//    private IProductAttributeService productAttributeService;
//    @Autowired
//    private IProductImageService productImageService;
//    @Autowired
//    private IProductParameterService productParameterService;
//    @Autowired
//    private IProductSpecificationService productSpecificationService;
//
//
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private IFavoriteService favoriteService;
//
//    @Autowired
//    private IAddressService addressService;
//    @Autowired
//    private IShoppingCartService shoppingCartService;
//    @Autowired
//    private IOrderService orderService;
//    @Autowired
//    private IOrderProductService orderProductService;
//    @Autowired
//    private IOrderShipmentService orderShipmentService;
//
//    /**
//     * 首页数据结果集合
//     *
//     * @return Object
//     */
//    @ApiOperation(value = "首页json", notes = "首页展示json")
//    @GetMapping(value = "/index.json")
//    public
//    @ResponseBody
//    OsResult index() {
//        Map<String, Object> model = new HashMap<String, Object>();
//        // 首页轮播广告列表
//        List<AdvertDetail> indexCarouselImgs = advertDetailService.listByAdvertId(AdvertTypeEnum.INDEX_CAROUSEL.getType());
//        model.put("indexCarouselImgs", indexCarouselImgs);
//        //新品推荐 indexProductRecommendNew
//        List<ProductVO> indexProductRecommendNew = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.NEW.getType());
//        model.put("indexProductRecommendNew", indexProductRecommendNew);
//        //推荐商品  productRecommend
//        List<ProductVO> indexProductRecommend = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.POPULAR.getType());
//        model.put("indexProductRecommend", indexProductRecommend);
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
//
//    /**
//     * 分类数据结果集合
//     * product.category.json?limit=99
//     *
//     * @return Object
//     */
//    @ApiOperation(value = "类目列表", notes = "类目列表")
//    @GetMapping(value = "/product.category.json")
//    public
//    @ResponseBody
//    OsResult list(
//            @RequestParam(value = "categoryId", required = false, defaultValue = "1") String reqCategoryId,
//            @RequestParam(value = "sort", required = false, defaultValue = "0") String reqSort,
//            @RequestParam(value = "page", required = false, defaultValue = "1") String reqPage,
//            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {
//
//        // 请求参数:类目ID,如果类目ID不存在或者不为Long类型,则默认1/全部商品
//        Long categoryId = StringUtils.isNumeric(reqCategoryId) ? Long.valueOf(reqCategoryId) : 1;
//        // 请求参数:排序方式,如果排序方式不存在或者不为Integer类型,则默认0/推荐排序
//        Integer sort = StringUtils.isNumeric(reqSort) ? Integer.valueOf(reqSort) : ProductSortEnum.RECOMMEND.getType();
//        // 请求参数:分页,如果分页不存在或者不为Integer类型,则默认1/默认页数
//        Integer page = StringUtils.isNumeric(reqPage) ? Integer.valueOf(reqPage) : 1;
//
//        // 查找当前类目信息
//        Category category = categoryService.getById(categoryId, StatusEnum.SHOW.getStatus());
//        if (category != null) {
//
//            // 通过类目ID、排序、分页查找商品列表
//            PageInfo pageInfo = new PageInfo(page, limit, ProductSortEnum.typeOf(sort).getSort(),
//                    ProductSortEnum.typeOf(sort).getOrder());
//            BasePageDTO<ProductVO> basePageDTO = productCategoryService.listProducts(categoryId, pageInfo);
//
//            // 根据类目ID查找子类目.
//
//
//            List<Category> lowerCategories = categoryService.listLowerCategories(categoryId, StatusEnum.SHOW.getStatus());
//
//            // 根据类目ID查找上级类目列表
//            List<Category> upperCategories = categoryService.listUpperCategories(categoryId, StatusEnum.SHOW.getStatus());
//
//            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("sort", ProductSortEnum.typeOf(sort).getType());// 排序方式
//            model.put("category", category);// 当前类目信息
//            model.put("products", basePageDTO.getList());// 商品列表
//            model.put("pageInfo", basePageDTO.getPageInfo()); // 分页信息
//            model.put("lowerCategories", lowerCategories);// 子类目列表
//            model.put("supperCategories", upperCategories);// 父类目列表
//
//            return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//        }
//        return new OsResult(CommonReturnCode.FAIL_PRODUCT_CATEGORY, CommonReturnCode.FAIL_PRODUCT_CATEGORY);
//    }
//
//
//    /**
//     * GET 搜索列表
//     *
//     * @return Object
//     */
//    @ApiOperation(value = "搜索列表", notes = "搜索列表")
//    @GetMapping(value = "/search.product.category.json")
//    public
//    @ResponseBody
//    OsResult search(
//            @RequestParam(value = "search", required = false, defaultValue = "") String search,
//            @RequestParam(value = "sort", required = false, defaultValue = "0") String reqSort,
//            @RequestParam(value = "page", required = false, defaultValue = "1") String reqPage,
//            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {
//        // 请求参数:排序方式,如果排序方式不存在或者不为Integer类型,则默认0/推荐排序
//        Integer sort = StringUtils.isNumeric(reqSort) ? Integer.valueOf(reqSort) : ProductSortEnum.RECOMMEND.getType();
//        // 请求参数:分页,如果分页不存在或者不为Integer类型,则默认1/默认页数
//        Integer page = StringUtils.isNumeric(reqPage) ? Integer.valueOf(reqPage) : 1;
//
//        // 通过搜索内容、排序、分页查找商品列表
//        PageInfo pageInfo = new PageInfo(page, limit, ProductSortEnum.typeOf(sort).getSort(),
//                ProductSortEnum.typeOf(sort).getOrder());
//        BasePageDTO<ProductVO> basePageDTO = productCategoryService.listBySearch(search, pageInfo);
//
//        // 根据类目ID查找子类目
//        List<Category> lowerCategories = categoryService.listLowerCategories(1L, StatusEnum.SHOW.getStatus());
//
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("search", search); // 搜索内容
//        model.put("sort", ProductSortEnum.typeOf(sort).getType());// 排序方式
//        model.put("products", basePageDTO.getList());// 商品列表
//        model.put("pageInfo", basePageDTO.getPageInfo()); // 分页信息
//        model.put("lowerCategories", lowerCategories);// 子类目列表
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
//
//    /**
//     * 商品数据结果集合
//     *
//     * @return Object
//     */
//    /**
//     * GET 商品详情页面
//     *
//     * @return
//     */
//    @ApiOperation(value = "商品详情页面", notes = "根据传过来的商品编号获取商品详情信息")
//    @GetMapping(value = "/product.info")
//    public
//    @ResponseBody
//    OsResult item(@RequestParam("productNumber") Long productNumber) {
//        // 根据商品编号查找商品信息
//        ProductVO product = productService.getByNumber(productNumber, StatusEnum.SHELVE.getStatus());
//        if (product != null) {
//            Map<String, Object> model = new HashMap<String, Object>();
//            // 根据类目ID查找上级类目列表
//            List<Category> upperCategories = categoryService.listUpperByProductId(product.getProductId(),
//                    StatusEnum.SHOW.getStatus());
//
//            // 根据商品ID查找商品属性
//            ProductAttribute productAttribute = productAttributeService.getByProductId(product.getProductId());
//
//            // 根据商品ID查找商品展示图片
//            List<ProductImage> productImages = productImageService.listByProductId(product.getProductId(),
//                    ProductConstantEnum.PRODUCT_PICIMG_NUMBER.getValue(), StatusEnum.SHOW.getStatus());
//            model.put("productImages", productImages);
//
//            // 根据商品ID查找商品参数
//            List<ProductParameter> productParameters = productParameterService.listByProductId(product.getProductId(),
//                    StatusEnum.SHOW.getStatus());
//
//            // 根据商品ID查找商品类型列表以及商品规格列表
//            ProductSpecificationDTO productSpecificationDTO = productSpecificationService
//                    .getByProductId(product.getProductId(), StatusEnum.SHOW.getStatus());
//
//            model.put("product", product);// 商品信息
//            model.put("upperCategories", upperCategories);// 上级类目列表
//            model.put("productAttribute", productAttribute);// 商品属性
//            model.put("productParameters", productParameters);// 商品参数
//            model.put("kindVOs", productSpecificationDTO.getKindVOs());// 商品类型列表
//            model.put("productSpecifications", JSON.toJSON(productSpecificationDTO.getProductSpecifications()));
//
//            return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//        }
//        return new OsResult(CommonReturnCode.FAIL_PRODUCT_INFO, CommonReturnCode.FAIL_PRODUCT_INFO);
//    }
//
//
//    /**
//     * 个人中心数据结果集合
//     * <p>
//     * GET 我的个人中心
//     *
//     * @return
//     */
//    @ApiOperation(value = "我的个人中心", notes = "我的个人中心")
//    @GetMapping(value = "/portal.my")
//    @ResponseBody
//    public OsResult portal() {
//
//        Map<String, Object> model = new HashMap<String, Object>();
//        if (SingletonLoginUtils.getUserId() == null) {
//            //未登录
//            return new OsResult(CommonReturnCode.UNAUTHORIZED);
//        }
//        UserVO userVO = userService.getById(SingletonLoginUtils.getUserId());
//        model.put("userVO", userVO);
//
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
//
//    /**
//     * GET 喜欢的商品
//     *
//     * @return
//     */
//    @ApiOperation(value = "喜欢的商品", notes = "喜欢的商品")
//    @GetMapping(value = "/favorite.product")
//    @ResponseBody
//    public OsResult favorite(
//            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
//            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit) {
//
//        PageInfo pageInfo = new PageInfo(limit, page);
//        Map<String, Object> model = new HashMap<String, Object>();
//        BasePageDTO<Favorite> basePageDTO = favoriteService.listByUserId(SingletonLoginUtils.getUserId(), pageInfo);
//
//        model.put("favorites", basePageDTO.getList());
//        model.put("pageInfo", basePageDTO.getPageInfo());
//
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
//
//    /**
//     * DELETE 喜欢的商品
//     *
//     * @return
//     */
//    @ApiOperation(value = "删除喜欢的商品", notes = "根据url传过来的商品编号删除喜欢的商品")
//    @DeleteMapping(value = "/favorite.product.del")
//    @ResponseBody
//    public OsResult favoriteDelete(@RequestParam("productNumber") Long productNumber) {
//        Integer count = favoriteService.deleteByProductNumber(SingletonLoginUtils.getUserId(), productNumber);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * GET 收货地址
//     *
//     * @return
//     */
//    @ApiOperation(value = "收货地址列表", notes = "收货地址列表")
//    @GetMapping(value = "/address.list")
//    @ResponseBody
//    public OsResult address(
//            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
//            @RequestParam(value = "limit", required = false, defaultValue = "11") Integer limit) {
//
//        Map<String, Object> model = new HashMap<String, Object>();
//        PageInfo pageInfo = new PageInfo(limit, page);
//        BasePageDTO<Address> basePageDTO = addressService.listByUserId(SingletonLoginUtils.getUserId(), pageInfo);
//        model.put("addresses", basePageDTO.getList());
//        model.put("pageInfo", basePageDTO.getPageInfo());
//
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
//
//    /**
//     * POST 创建收货地址
//     *
//     * @return
//     */
//    @ApiOperation(value = "创建收货地址", notes = "根据url传过来的收货地址信息创建收货地址")
//    @PostMapping(value = "/address.create")
//    @ResponseBody
//    public OsResult addressCreate(Address address) {
//        Integer count = addressService.insertAddress(SingletonLoginUtils.getUserId(), address);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * PUT 更新收货地址
//     *
//     * @return
//     */
//    @ApiOperation(value = "更新收货地址", notes = "根据url传过来的收获地址ID和收货地址信息更新收货地址")
//    @PutMapping(value = "/address.edit")
//    @ResponseBody
//    public OsResult addressUpdate(Address address, @RequestParam("addressId") Long addressId) {
//        address.setAddressId(addressId);
//        Integer count = addressService.updateAddress(SingletonLoginUtils.getUserId(), address);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * DELETE 收货地址
//     *
//     * @return
//     */
//    @ApiOperation(value = "删除收货地址", notes = "根据url传过来的地址ID删除收货地址")
//    @DeleteMapping(value = "/address.del")
//    @ResponseBody
//    public OsResult addressDelete(@RequestParam("addressId") Long addressId) {
//        Integer count = addressService.deleteByAddressId(SingletonLoginUtils.getUserId(), addressId);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//
//    /**
//     * 购物车数据结果集合
//     *
//     * @return
//     */
//    @ApiOperation(value = "添加购物车商品", notes = "添加购物车商品")
//    @PostMapping(value = "/cart.create")
//    @ResponseBody
//    public OsResult create(
//            @RequestParam(value = "productSpecNumber", required = true) Long productSpecNumber) {
//        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//        if (authorizingUser != null) {
//            shoppingCartService.insertShoppingCart(productSpecNumber, SingletonLoginUtils.getUserId());
//            return new OsResult(CommonReturnCode.SUCCESS, productSpecNumber.toString());
//        } else {
//            return new OsResult(CommonReturnCode.UNAUTHORIZED);
//        }
//    }
//
//    /**
//     * GET 成功加入购物车
//     *
//     * @return
//     */
//    @ApiOperation(value = "成功加入购物车", notes = "成功加入购物车")
//    @GetMapping(value = "/add.cart.success")
//    @ResponseBody
//    public Object view(@RequestParam("productSpecNumber") Long productSpecNumber) {
//
//        Map<String, Object> model = new HashMap<String, Object>();
//        // 用户已登录,查看数据库中购物车列表是否有该商品
//        ShoppingCartVO shoppingCartVO = shoppingCartService.getCart(SingletonLoginUtils.getUserId(), productSpecNumber);
//        if (shoppingCartVO == null) {
//            // 重定向到购物车列表
//            return redirectTo("/cart.list");
//        }
//        model.put("shoppingCartVO", shoppingCartVO);
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
//
//    /**
//     * GET 购物车列表
//     *
//     * @return
//     */
//    @ApiOperation(value = "购物车列表", notes = "购物车列表")
//    @GetMapping(value = "/cart.list")
//    @ResponseBody
//    public OsResult list() {
//        Map<String, Object> model = new HashMap<String, Object>();
//        CartVO cartVO = shoppingCartService.list(SingletonLoginUtils.getUserId(), StatusEnum.ALL.getStatus());
//        model.put("cartVO", cartVO);
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//    }
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
//
//    /**
//     * GET 购物车商品数量
//     *
//     * @return
//     */
//    @ApiOperation(value = "购物车商品数量", notes = "购物车商品数量")
//    @GetMapping(value = "/cartNumber")
//    @ResponseBody
//    public OsResult cartNumber() {
//        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//        if (authorizingUser != null) {
//            CartVO cartVO = shoppingCartService.list(SingletonLoginUtils.getUserId(), StatusEnum.ALL.getStatus());
//            if (cartVO != null) {
//                return new OsResult(CommonReturnCode.SUCCESS, cartVO.getTotalNumber());
//            } else {
//                return new OsResult(CommonReturnCode.FAILED);
//            }
//        } else {
//            return new OsResult(CommonReturnCode.FAILED);
//        }
//    }
//
//    /**
//     * DELETE 删除购物车商品
//     *
//     * @return
//     */
//    @ApiOperation(value = "删除购物车商品", notes = "根据URL传过来的商品规格ID删除购物车商品")
//    @DeleteMapping(value = "/cart.del")
//    @ResponseBody
//    public OsResult delete(@RequestParam("productSpecNumber") Long productSpecNumber) {
//        Integer count = shoppingCartService.delete(productSpecNumber, SingletonLoginUtils.getUserId());
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * PUT 修改购物车商品数量
//     *
//     * @return
//     */
//    @ApiOperation(value = "修改购物车商品数量", notes = "根据URL传过来的商品规格ID修改购物车商品数量")
//    @PutMapping(value = "/cart.edit")
//    @ResponseBody
//    public OsResult updateNumber(@RequestParam("productSpecNumber") Long productSpecNumber,
//                                 @RequestParam(value = "buyNumber", required = true) Integer buyNumber) {
//        Integer count = shoppingCartService.updateBuyNumber(productSpecNumber, SingletonLoginUtils.getUserId(),
//                buyNumber);
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * PUT 修改购物车商品选中状态
//     *
//     * @return
//     */
//    @ApiOperation(value = "修改购物车商品选中状态", notes = "根据URL传过来的商品规格ID修改购物车商品选中状态")
//    @PutMapping(value = "/cart.add.status")
//    @ResponseBody
//    public OsResult updateStatus(@RequestParam("productSpecNumber") Long productSpecNumber,
//                                 @RequestParam(value = "checkStatus", required = true) Integer checkStatus) {
//        if (StatusEnum.CHECKED.getStatus().equals(checkStatus)) {
//            Integer count = shoppingCartService.updateStatus(productSpecNumber, SingletonLoginUtils.getUserId(),
//                    StatusEnum.UNCHECKED.getStatus());
//            return new OsResult(CommonReturnCode.SUCCESS, count);
//        } else if (StatusEnum.UNCHECKED.getStatus().equals(checkStatus)) {
//            Integer count = shoppingCartService.updateStatus(productSpecNumber, SingletonLoginUtils.getUserId(),
//                    StatusEnum.CHECKED.getStatus());
//            return new OsResult(CommonReturnCode.SUCCESS, count);
//        }
//        return new OsResult(CommonReturnCode.BAD_REQUEST.getCode(), CommonReturnCode.BAD_REQUEST.getMessage());
//    }
//
//
//    /**
//     * 订单数据结果集合
//     *
//     * @return Object
//     */
//
//
//    /**
//     * GET 填写订单信息
//     *
//     * @return
//     */
//    @ApiOperation(value = "填写订单信息", notes = "填写订单信息")
//    @GetMapping(value = "/order.info")
//    @ResponseBody
//    public OsResult checkout() {
//        Map<String, Object> model = new HashMap<String, Object>();
//        // 收获地址
//        List<Address> addresses = addressService.listAddress(SingletonLoginUtils.getUserId());
//        model.put("addresses", addresses);
//
//        // 购物车选中商品
//        CartVO cartVO = shoppingCartService.list(SingletonLoginUtils.getUserId(), StatusEnum.CHECKED.getStatus());
//        model.put("cartVO", cartVO);
//
//        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//
//    }
//
//    /**
//     * POST 提交订单
//     *
//     * @return
//     */
//    @ApiOperation(value = "提交订单", notes = "提交订单")
//    @PostMapping(value = "/order.commit")
//    @ResponseBody
//    public Object confirm(Order order, @RequestParam(value = "addressId", required = true) Long addressId) {
//        // 收货地址
//        Address address = addressService.getAddress(addressId, SingletonLoginUtils.getUserId());
//        if (address != null) {
//            OrderShipment orderShipment = new OrderShipment();
//            BeanUtils.copyProperties(address, orderShipment);
//
//            // 购物车选中商品
//            CartVO cartVO = shoppingCartService.list(SingletonLoginUtils.getUserId(), StatusEnum.CHECKED.getStatus());
//            if (!cartVO.getShoppingCartVOs().isEmpty()) {
//                order.setBuyNumber(cartVO.getTotalNumber());// 订单总数量
//                order.setOrderAmount(cartVO.getTotalPrice());// 订单总价格
//                order.setOrderScore(cartVO.getTotalScore());// 订单总积分
//
//                // 遍历购物车选中商品列表
//                List<OrderShoppingCartVO> orderShoppingCartVOs = new ArrayList<OrderShoppingCartVO>();
//                for (ShoppingCartVO vo : cartVO.getShoppingCartVOs()) {
//                    OrderShoppingCartVO orderShoppingCartVO = new OrderShoppingCartVO();
//                    BeanUtils.copyProperties(vo, orderShoppingCartVO);
//                    orderShoppingCartVOs.add(orderShoppingCartVO);
//                }
//                Long orderNumber = orderService.insertOrder(order, orderShipment, orderShoppingCartVOs,
//                        SingletonLoginUtils.getUserId());
//
//                if (orderNumber != null) {
//                    shoppingCartService.deleteCheckStatus(SingletonLoginUtils.getUserId());
//                    return new OsResult(CommonReturnCode.SUCCESS, orderNumber.toString());
//                    // TODO Long
//                    // 17位传送末尾精读损失,例14944366378872495前台接收变成14944366378872494,解决方法toString,原因未知
//                } else {
//                    return new OsResult(CommonReturnCode.UNKNOWN_ERROR.getCode(),
//                            CommonReturnCode.UNKNOWN_ERROR.getMessage());
//                }
//            } else {
//                return new OsResult(CommonReturnCode.BAD_REQUEST.getCode(), "请选择想要购买的商品!");
//            }
//        } else {
//            return new OsResult(CommonReturnCode.BAD_REQUEST.getCode(), "请选择正确的收货地址!");
//        }
//    }
//
//    /**
//     * GET 确认订单
//     *
//     * @return
//     */
//    @ApiOperation(value = "确认订单", notes = "确认订单")
//    @GetMapping(value = "/order.confirm")
//    @ResponseBody
//    public OsResult confirmShow(@RequestParam("orderNumber") Long orderNumber) {
//
//        Order order = orderService.getByOrderNumber(orderNumber, SingletonLoginUtils.getUserId(),
//                OrderStatusEnum.SUBMIT_ORDERS.getStatus());
//
//        if (order != null) {
//
//            Map<String, Object> model = new HashMap<String, Object>();
//            List<OrderProduct> orderProducts = orderProductService.listByOrderId(order.getOrderId());
//
//            OrderShipment orderShipment = orderShipmentService.getByOrderId(order.getOrderId());
//
//            model.put("order", order); // 订单信息
//            model.put("orderProducts", orderProducts);// 订单详情表
//            model.put("orderShipment", orderShipment);// 订单配送表
//            return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
//        }
//        return new OsResult(CommonReturnCode.FAILED);
//    }
//
//    /**
//     * PUT 取消订单
//     *
//     * @return
//     */
//    @ApiOperation(value = "取消订单", notes = "根据URL传过来的订单编号取消订单")
//    @PutMapping(value = "/order.cancel")
//    @ResponseBody
//    public Object cancelOrder(Model model, @RequestParam(value = "orderNumber", required = true) Long orderNumber) {
//        Integer count = orderService.updateCancelOrder(orderNumber, SingletonLoginUtils.getUserId());
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * PUT 修改送货时间
//     *
//     * @return
//     */
//    @ApiOperation(value = "修改送货时间", notes = "根据URL传过来的订单编号修改送货时间")
//    @PutMapping(value = "/order.update.time")
//    @ResponseBody
//    public Object timeOrder(Model model, @RequestParam(value = "orderNumber", required = true) Long orderNumber,
//                            @RequestParam(value = "shipmentTime", required = true) Integer shipmentTime) {
//        Integer count = orderService.updateShipmentTime(orderNumber, shipmentTime, SingletonLoginUtils.getUserId());
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//    /**
//     * PUT 修改收货地址
//     *
//     * @return
//     */
//    @ApiOperation(value = "修改收货地址", notes = "根据URL传过来的收货地址信息修改收货地址")
//    @PutMapping(value = "/order.shipment")
//    @ResponseBody
//    public Object orderShipment(OrderShipment orderShipment) {
//        Integer count = orderShipmentService.update(orderShipment, SingletonLoginUtils.getUserId());
//        return new OsResult(CommonReturnCode.SUCCESS, count);
//    }
//
//
//}
