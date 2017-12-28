package org.pussinboots.morning.os.wxService;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.online.common.enums.AdvertTypeEnum;
import org.pussinboots.morning.online.entity.AdvertDetail;
import org.pussinboots.morning.online.service.IAdvertDetailService;
import org.pussinboots.morning.order.common.enums.OrderStatusEnum;
import org.pussinboots.morning.order.entity.Order;
import org.pussinboots.morning.order.entity.OrderProduct;
import org.pussinboots.morning.order.entity.OrderShipment;
import org.pussinboots.morning.order.pojo.vo.OrderShoppingCartVO;
import org.pussinboots.morning.order.service.IOrderProductService;
import org.pussinboots.morning.order.service.IOrderService;
import org.pussinboots.morning.order.service.IOrderShipmentService;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.security.AuthorizingUser;
import org.pussinboots.morning.os.common.util.SingletonLoginUtils;
import org.pussinboots.morning.product.common.constant.ProductConstantEnum;
import org.pussinboots.morning.product.common.enums.ProductRecommendTypeEnum;
import org.pussinboots.morning.product.common.enums.ProductSortEnum;
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.entity.ProductAttribute;
import org.pussinboots.morning.product.entity.ProductImage;
import org.pussinboots.morning.product.entity.ProductParameter;
import org.pussinboots.morning.product.pojo.dto.ProductSpecificationDTO;
import org.pussinboots.morning.product.pojo.vo.CartVO;
import org.pussinboots.morning.product.pojo.vo.ProductVO;
import org.pussinboots.morning.product.pojo.vo.ShoppingCartVO;
import org.pussinboots.morning.product.service.*;
import org.pussinboots.morning.user.entity.Address;
import org.pussinboots.morning.user.entity.Favorite;
import org.pussinboots.morning.user.pojo.vo.UserVO;
import org.pussinboots.morning.user.service.IAddressService;
import org.pussinboots.morning.user.service.IFavoriteService;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "微信小程序api", description = "微信小程序api")
public class wxApiSearchService extends BaseController {

    @Autowired
    private IProductCategoryService productCategoryService;
    @Autowired
    private ICategoryService categoryService;

    /**
     * GET 搜索列表
     *
     * @return Object
     */
    @ApiOperation(value = "搜索列表", notes = "搜索列表")
    @GetMapping(value = "/search.product.category.json")
    public
    @ResponseBody
    OsResult search(
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
        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
    }

}
