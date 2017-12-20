package org.pussinboots.morning.product.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.*;
import org.pussinboots.morning.product.mapper.*;
import org.pussinboots.morning.product.pojo.vo.ProductVO;
import org.pussinboots.morning.product.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：morning-product-service
 * 类名称：ProductServiceImpl
 * 类描述：Product / 商品表 业务逻辑层接口实现
 * 创建人：陈星星
 * 创建时间：2017年4月11日 下午3:17:31
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private ProductAttributeMapper productAttributeMapper;
    @Autowired
    private ProductParameterMapper productParameterMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductRecommendMapper productRecommendMapper;

    @Autowired
    private ProductSpecificationMapper productSpecificationMapper;

    @Override
    public ProductVO getByNumber(Long productNumber, Integer status) {
        return productMapper.getByNumber(productNumber, status);
    }

    @Override
    public BasePageDTO<Product> listByPage(PageInfo pageInfo, String search) {


        Page<Product> page = new Page<>(pageInfo.getCurrent(), pageInfo.getLimit());
        List<Product> products = productMapper.listByPage(pageInfo, search, page);
        pageInfo.setTotal(page.getTotal());
        return new BasePageDTO<Product>(pageInfo, products);
    }

    @Transactional
    public Integer insertProduct(Product product, String userName) {
        product.setCreateBy(userName);
        product.setCreateTime(new Date());
        //商品详情初始化
        ProductDetail pd = new ProductDetail();
        pd.setDescription(product.getPageDescription());
        pd.setProductId(product.getProductId());
        pd.setCreateBy(userName);
        pd.setCreateTime(new Date());
        //商品图片初始化
        ProductImage pi = new ProductImage();
        pi.setPicImg(product.getPicImg());
        pi.setProductId(product.getProductId());
        pi.setCreateBy(userName);
        pi.setCreateTime(new Date());
        //商品属性初始化
        ProductAttribute pa = new ProductAttribute();
        pa.setProductId(product.getProductId());
        pa.setQuestionNumber(0);
        pa.setPageViews(0);
        pa.setSalesVolume(0);
        pa.setStock(0);
        //商品参数初始化
        ProductParameter pp = new ProductParameter();
        pp.setProductId(product.getProductId());
        pp.setCreateBy(userName);
        pp.setCreateTime(new Date());
        //商品分类初始化
        ProductCategory pc = new ProductCategory();
        pc.setProductId(product.getProductId());
        pc.setCreateBy(userName);
        pc.setCreateTime(new Date());
        //商品推荐初始化
        ProductRecommend pr = new ProductRecommend();
        pr.setProductId(product.getProductId());
        pr.setCreateBy(userName);
        pr.setCreateTime(new Date());
        //商品多规格初始化
//        ProductSpecification ps = new ProductSpecification();
//        ps.setProductId(product.getProductId());
//        ps.setCreateBy(userName);
//        ps.setCreateTime(new Date());
        int a = productImageMapper.insert(pi);
        int b = productDetailMapper.insert(pd);
        int c = productAttributeMapper.insert(pa);
        int d = productParameterMapper.insert(pp);
        int e = productCategoryMapper.insert(pc);
        int f = productRecommendMapper.insert(pr);
//        int g = productSpecificationMapper.insert(ps);
        int h = productMapper.insert(product);
        return a+b+c+d+e+f+h==7?1:0;
    }

    /**
     * 修改商品信息
     * @param product 修改的商品信息
     * @param userName 登录用户
     * @return 1 or 0
     */

    @Override
    public Integer updateProduct(Product product, String userName) {
        product.setUpdateBy(userName);
        product.setUpdateTime(new Date());
        //TODO 暂时没有update其他关系表
        return productMapper.updateById(product);
    }

}
