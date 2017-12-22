package org.pussinboots.morning.product.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.ProductImage;
import org.pussinboots.morning.product.mapper.ProductImageMapper;
import org.pussinboots.morning.product.service.IProductImageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
* 项目名称：morning-product-service   
* 类名称：ProductImageServiceImpl   
* 类描述：ProductImage / 商品图片表 业务逻辑层接口实现        
* 创建人：陈星星   
* 创建时间：2017年4月14日 上午2:07:20   
*
 */
@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage> implements IProductImageService {

	@Autowired
	private ProductImageMapper productImageMapper;
	
	@Override
	public List<ProductImage> listByProductId(Long productId, Integer showNumber, Integer status) {
		return productImageMapper.listByProductId(productId, showNumber, status);
	}
	@Override
	public BasePageDTO<ProductImage> listByPage(PageInfo pageInfo, String search,Long productId) {


		Page<ProductImage> page = new Page<>(pageInfo.getCurrent(), pageInfo.getLimit());
		List<ProductImage> products = productImageMapper.listByPage(pageInfo, search, page,productId);
		pageInfo.setTotal(page.getTotal());
		return new BasePageDTO<ProductImage>(pageInfo, products);
	}


	@Override
	public List<ProductImage> selectByProductId(Long productId) {
		return productImageMapper.selectByProductId(productId);
	}

	@Override
	public Integer insertProductImage(ProductImage productImage, String userName) {
		productImage.setCreateBy(userName);
		productImage.setCreateTime(new Date());
		return productImageMapper.insert(productImage);
	}

	@Override
	public Integer updateProductImage(ProductImage productImage, String userName, Long picImgId) {
		if (picImgId > 0) {
			return productImageMapper.updateById(productImage);
		}
		productImage.setCreateTime(new Date());
		productImage.setCreateBy(userName);
		return productImageMapper.insert(productImage);

	}
}
