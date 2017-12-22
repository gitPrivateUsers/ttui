package org.pussinboots.morning.product.service.impl;

import org.pussinboots.morning.product.entity.ProductDetail;
import org.pussinboots.morning.product.mapper.ProductDetailMapper;
import org.pussinboots.morning.product.service.IProductDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 项目名称：morning-product-service
 * 类名称：ProductDetailServiceImpl
 * 类描述：ProductDetail / 商品描述表 业务逻辑层接口实现
 * 创建人：陈星星
 * 创建时间：2017年4月14日 上午2:07:04
 */
@Service
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailMapper, ProductDetail> implements IProductDetailService {

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    public Integer updateProductDetail(ProductDetail productDetail, String userName, Long productDetailId) {
        if (productDetailId > 0) {
            productDetail.setUpdateBy(userName);
            productDetail.setUpdateTime(new Date());
            return productDetailMapper.updateById(productDetail);
        }
        productDetail.setCreateTime(new Date());
        productDetail.setCreateBy(userName);
        return productDetailMapper.insert(productDetail);

    }

    @Override
    public ProductDetail selectByProductId(Long productId) {
return productDetailMapper.selectByProductId(productId);

    }
}
