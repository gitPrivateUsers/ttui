package org.pussinboots.morning.cms.controller.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.pussinboots.morning.administrator.entity.Customer;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.administrator.service.ICustomerService;
import org.pussinboots.morning.cms.common.result.CmsPageResult;
import org.pussinboots.morning.cms.common.result.CmsResult;
import org.pussinboots.morning.cms.common.security.AuthorizingUser;
import org.pussinboots.morning.cms.common.util.SingletonLoginUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.ProductRecommend;
import org.pussinboots.morning.product.entity.Recommend;
import org.pussinboots.morning.product.service.IProductRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 项目名称：morning-cms-web Maven Webapp
 * 类名称：CustomerController
 * 类描述：用户管理表示层控制器
 * 创建人：zhancl
 * 创建时间：2017年12月21日 上午10:12:17
 *
 */
@Controller
@RequestMapping(value = "/product/recommend")
@Api(value = "商品推荐", description = "商品推荐")
public class ProductRecommendController extends BaseController {
    @Autowired
    private IProductRecommendService iProductRecommendService;


    @ApiOperation(value = "推荐页面", notes = "推荐页面")
    @RequiresPermissions("product:recommend:list:view")
    @GetMapping(value = "/view")
    public String getRecommendPage(Model model) {
        return "/modules/recommend/recommend_list";
    }

    @ApiOperation(value = "获取推荐列表", notes = "根据分页信息获取推荐列表")
    @RequiresPermissions("product:recommend:list:view")
    @GetMapping(value = "/list")
    @ResponseBody
    public Object recommendList(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
        BasePageDTO<ProductRecommend> BasePageDTO = iProductRecommendService.listByPage(pageInfo, search);
        return new CmsPageResult(BasePageDTO.getList(), BasePageDTO.getPageInfo().getTotal());
    }



    @ApiOperation(value = "创建推荐位页面", notes = "推荐位页面")
    @RequiresPermissions("product:recommend:list:create")
    @GetMapping(value = "/create")
    public String getInsertPage(Model model) {
        return "/modules/recommend/recommend_create";
    }



    @ApiOperation(value = "创建一个推荐位", notes = "创建一个推荐位")
    @RequiresPermissions("product:recommend:list:create")
    @PostMapping(value= "/create")
    @ResponseBody
    public Object insert(ProductRecommend productRecommend) {
        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
            Integer count = iProductRecommendService.insertProductRecommend(productRecommend, authorizingUser.getUserName());
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "更新推荐信息", notes = "更新推荐信息")
    @RequiresPermissions("product:recommend:list:edit")
    @GetMapping(value = "/{recommendProductId}/edit")
    public String getUpdatePage(Model model, @PathVariable("recommendProductId") Long recommendProductId) {

        ProductRecommend productRecommend = iProductRecommendService.selectById(recommendProductId);
        model.addAttribute("productRecommend", productRecommend);

        return "/modules/recommend/recommend_update";
    }


   /* @ApiOperation(value = "更新用户信息", notes = "根据ID修改")
    @RequiresPermissions("customer:detail:edit")
    @PutMapping(value = "/{userId}")
    @ResponseBody
    public Object update(Customer customer, @PathVariable("userId") Long userId,
                         @RequestParam(value = "sex", defaultValue = "0") Integer sex,
                         @RequestParam(value = "status", defaultValue = "0") Integer status) {

        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
            // 更新用户记录
            customer.setStatus(status);
            Integer count = customerService.updateUserId(customer, authorizingUser.getUserName());
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }*/

}
