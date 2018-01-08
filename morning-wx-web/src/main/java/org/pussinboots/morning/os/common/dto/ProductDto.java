package org.pussinboots.morning.os.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.*;
import org.pussinboots.morning.product.pojo.vo.KindVO;
import org.pussinboots.morning.product.pojo.vo.ProductVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProductVO product;
    private ProductAttribute productAttribute;
    private List<ProductParameter> productParameters;
    private List<ProductImage> productImages;
    private List<Category> upperCategories;

    /**
     * 商品类型列表
     */
    private List<KindVO> kindVOs;

    /**
     * 商品规格列表
     */
    private ProductDetail productDetail;

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    private Map<String, Object> productSpecifications;

    public List<Category> getUpperCategories() {
        return upperCategories;
    }

    public void setUpperCategories(List<Category> upperCategories) {
        this.upperCategories = upperCategories;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public ProductVO getProduct() {
        return product;
    }

    public void setProduct(ProductVO product) {
        this.product = product;
    }

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }

    public List<ProductParameter> getProductParameters() {
        return productParameters;
    }

    public void setProductParameters(List<ProductParameter> productParameters) {
        this.productParameters = productParameters;
    }

    public List<KindVO> getKindVOs() {
        return kindVOs;
    }

    public void setKindVOs(List<KindVO> kindVOs) {
        this.kindVOs = kindVOs;
    }

    public Map<String, Object> getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(Map<String, Object> productSpecifications) {
        this.productSpecifications = productSpecifications;
    }
}
