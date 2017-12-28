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
public class wxApiProductService extends BaseController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductAttributeService productAttributeService;
    @Autowired
    private IProductImageService productImageService;
    @Autowired
    private IProductParameterService productParameterService;
    @Autowired
    private IProductSpecificationService productSpecificationService;


    /**
     * 商品数据结果集合
     *
     * @return Object
     */
    /**
     * GET 商品详情页面
     *
     * @return
     */
    @ApiOperation(value = "商品详情页面", notes = "根据传过来的商品编号获取商品详情信息")
    @GetMapping(value = "/product.info")
    public
    @ResponseBody
    OsResult item(@RequestParam("productNumber") Long productNumber) {
        // 根据商品编号查找商品信息
        ProductVO product = productService.getByNumber(productNumber, StatusEnum.SHELVE.getStatus());
        if (product != null) {
            Map<String, Object> model = new HashMap<String, Object>();
            // 根据类目ID查找上级类目列表
            List<Category> upperCategories = categoryService.listUpperByProductId(product.getProductId(),
                    StatusEnum.SHOW.getStatus());

            // 根据商品ID查找商品属性
            ProductAttribute productAttribute = productAttributeService.getByProductId(product.getProductId());

            // 根据商品ID查找商品展示图片
            List<ProductImage> productImages = productImageService.listByProductId(product.getProductId(),
                    ProductConstantEnum.PRODUCT_PICIMG_NUMBER.getValue(), StatusEnum.SHOW.getStatus());
            model.put("productImages", productImages);

            // 根据商品ID查找商品参数
            List<ProductParameter> productParameters = productParameterService.listByProductId(product.getProductId(),
                    StatusEnum.SHOW.getStatus());

            // 根据商品ID查找商品类型列表以及商品规格列表
            ProductSpecificationDTO productSpecificationDTO = productSpecificationService
                    .getByProductId(product.getProductId(), StatusEnum.SHOW.getStatus());

            model.put("product", product);// 商品信息
            model.put("upperCategories", upperCategories);// 上级类目列表
            model.put("productAttribute", productAttribute);// 商品属性
            model.put("productParameters", productParameters);// 商品参数
            model.put("kindVOs", productSpecificationDTO.getKindVOs());// 商品类型列表
            model.put("productSpecifications", JSON.toJSON(productSpecificationDTO.getProductSpecifications()));

            return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
        }
        return new OsResult(CommonReturnCode.FAIL_PRODUCT_INFO, CommonReturnCode.FAIL_PRODUCT_INFO);
    }


}
