package org.pussinboots.morning.cms.controller.product;

import io.swagger.annotations.Api;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.product.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 项目名称：morning-cms-web Maven Webapp
 * 类名称：ProductCategoryController
 * 类描述：分类管理表示层控制器
 * 创建人：陈星星
 * 创建时间：2017年5月20日 下午3:08:25
 */
@Controller
@RequestMapping(value = "/product/detail/{productId}/category")
@Api(value = "商品分类管理", description = "商品分类管理")
public class ProductByCategoryController extends BaseController {

    @Autowired
    private IProductCategoryService productCategoryService;


}
