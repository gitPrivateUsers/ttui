package org.pussinboots.morning.os.controller.wxService;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.os.common.dto.ProductDto;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.product.common.constant.ProductConstantEnum;
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.entity.ProductAttribute;
import org.pussinboots.morning.product.entity.ProductImage;
import org.pussinboots.morning.product.entity.ProductParameter;
import org.pussinboots.morning.product.pojo.dto.ProductSpecificationDTO;
import org.pussinboots.morning.product.pojo.vo.ProductVO;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "商品详情api", description = "商品详情api")
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

    @Autowired
    private IProductDetailService productDetailService;

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
    OsResult item(@RequestParam("productId") Long productId) {
        // 根据商品编号查找商品信息
        ProductVO product = productService.getByProductId(productId, StatusEnum.SHELVE.getStatus());
        if (product != null) {
            // 根据类目ID查找上级类目列表
            List<Category> upperCategories = categoryService.listUpperByProductId(product.getProductId(),
                    StatusEnum.SHOW.getStatus());

            // 根据商品ID查找商品属性
            ProductAttribute productAttribute = productAttributeService.getByProductId(product.getProductId());

            // 根据商品ID查找商品展示图片
            List<ProductImage> productImages = productImageService.listByProductId(product.getProductId(),
                    ProductConstantEnum.PRODUCT_PICIMG_NUMBER.getValue(), StatusEnum.SHOW.getStatus());

            // 根据商品ID查找商品参数
            List<ProductParameter> productParameters = productParameterService.listByProductId(product.getProductId(),
                    StatusEnum.SHOW.getStatus());

            // 根据商品ID查找商品类型列表以及商品规格列表
            ProductSpecificationDTO productSpecificationDTO = productSpecificationService
                    .getByProductId(product.getProductId(), StatusEnum.SHOW.getStatus());

            ProductDto productDto = new ProductDto();
            productDto.setProduct(product);// 商品信息
            productDto.setProductImages(productImages);
            productDto.setProductAttribute(productAttribute);// 商品属性
            productDto.setUpperCategories(upperCategories);// 上级类目列表
            productDto.setKindVOs(productSpecificationDTO.getKindVOs());// 商品类型列表
            productDto.setProductParameters(productParameters);// 商品参数
            productDto.setProductSpecifications(productSpecificationDTO.getProductSpecifications());
            return new OsResult(CommonReturnCode.SUCCESS, productDto);
        }
        return new OsResult(CommonReturnCode.FAIL_PRODUCT_INFO, CommonReturnCode.FAIL_PRODUCT_INFO);
    }

}
