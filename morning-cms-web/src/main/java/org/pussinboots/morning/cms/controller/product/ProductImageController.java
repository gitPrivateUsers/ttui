//package org.pussinboots.morning.cms.controller.product;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.pussinboots.morning.cms.common.result.CmsPageResult;
//import org.pussinboots.morning.cms.common.result.CmsResult;
//import org.pussinboots.morning.cms.common.security.AuthorizingUser;
//import org.pussinboots.morning.cms.common.util.SingletonLoginUtils;
//import org.pussinboots.morning.common.base.BaseController;
//import org.pussinboots.morning.common.base.BasePageDTO;
//import org.pussinboots.morning.common.constant.CommonReturnCode;
//import org.pussinboots.morning.common.support.page.PageInfo;
//import org.pussinboots.morning.product.entity.ProductImage;
//import org.pussinboots.morning.product.service.IProductImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 项目名称：morning-cms-web Maven Webapp
// * 类名称：ProductCategoryController
// * 类描述：分类管理表示层控制器
// * 创建人：陈星星
// * 创建时间：2017年5月20日 下午3:08:25
// */
//@Controller
//@RequestMapping(value = "/product/detail/")
//@Api(value = "商品图片管理", description = "商品图片管理")
//public class ProductImageController extends BaseController {
//
//    @Autowired
//    private IProductImageService productImageService;
//
//
//    /**
//     * GET 商品图片页面
//     *
//     * @return
//     */
//    @ApiOperation(value = "商品图片页面", notes = "商品图片页面")
//    @RequiresPermissions("product:detail:view")
//    @GetMapping(value = "/{productId}/imgView")
//    public String getProductImagePage(Model model, @PathVariable("productId") Long productId) {
//
//        model.addAttribute("productId", productId);
//        return "/modules/product/product_image_list";
//    }
//
//    /**
//     * GET 商品图片页面,
//     *
//     * @return
//     */
//    @ApiOperation(value = "获取商品图片页面列表", notes = "根据分页信息/搜索内容")
//    @RequiresPermissions("product:list:view")
//    @GetMapping(value = "/{productId}/lists")
//    @ResponseBody
//    public Object listProductImage(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search, @PathVariable("productId") Long productId) {
//
//        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//
//        if (authorizingUser != null) {
//            BasePageDTO<ProductImage> basePageDTO = productImageService.listByPage(pageInfo, search, productId);
//            return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
//        } else {
//            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
//        }
//    }
//
//
//    /**
//     * GET 创建product
//     *
//     * @return
//     */
//    @ApiOperation(value = "创建商品图片地址", notes = "创建商品图片地址")
//    @RequiresPermissions("product:detail:createImg")
//    @GetMapping(value = "/createImg")
//    public String getInsertProductImgPage(Model model) {
//        return "/modules/product/product_image_create";
//    }
//
//    /**
//     * POST 创建商品
//     *
//     * @return
//     */
//    @ApiOperation(value = "创建商品图片地址", notes = "创建商品图片地址")
//    @RequiresPermissions("product:detail:createImg")
//    @PostMapping(value = "")
//    @ResponseBody
//    public Object insertimg(ProductImage productImage) {
//        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//        if (authorizingUser != null) {
//            Integer count = productImageService.insertProductImage(productImage, authorizingUser.getUserName());
//            return new CmsResult(CommonReturnCode.SUCCESS, count);
//        } else {
//            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
//        }
//    }
//
//    /**
//     * GET 修改商品图片详情信息
//     *
//     * @return
//     */
//    @ApiOperation(value = "修改商品图片详情信息", notes = "修改商品图片详情信息")
//    @RequiresPermissions("product:detail:updateImg")
//    @GetMapping(value = "/{picImgId}/updateImg")
//    public String getUpdateProductImagelPage(Model model, @PathVariable("picImgId") Long picImgId) {
//
//        ProductImage productImage = productImageService.selectById(picImgId);
//        model.addAttribute("ProductImage", productImage);
//
//        return "/modules/product/productImg_update";
//    }
//
//    /**
//     * PUT 修改商品图片详情信息
//     *
//     * @return
//     */
//    @ApiOperation(value = "修改商品图片详情信息", notes = "根据productId修改")
//    @RequiresPermissions("product:detail:updateImg")
//    @PutMapping(value = "/{productImgId}")
//    @ResponseBody
//    public Object updateProductImage(ProductImage productImage, @PathVariable("picImgId") Long picImgId) {
//
//        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
//        if (authorizingUser != null) {
//
//            Integer count = productImageService.updateProductImage(productImage, authorizingUser.getUserName(), picImgId);
//            return new CmsResult(CommonReturnCode.SUCCESS, count);
//        } else {
//            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
//        }
//    }
//}
