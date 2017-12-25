package org.pussinboots.morning.product.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.ProductDetail;
import org.pussinboots.morning.product.entity.ProductImage;
import com.baomidou.mybatisplus.service.IService;

/**
 * 
* 项目名称：morning-product-facade   
* 类名称：IProductImageService   
* 类描述：ProductImage / 商品图片表 业务逻辑层接口    
* 创建人：陈星星   
* 创建时间：2017年4月14日 上午2:02:08   
*
 */
public interface IProductImageService extends IService<ProductImage> {
	
	/**
	 * 根据商品ID查找展示图片
	 * @param productId 商品ID
	 * @param showNumber 显示图片数量
	 * @param status 状态
	 * @return
	 */
	List<ProductImage> listByProductId(Long productId, Integer showNumber, Integer status);

	BasePageDTO<ProductImage> listByPage(PageInfo pageInfo, String search,@Param("productId") Long productId);

	Integer updateProductImage(ProductImage productImage, String userName, Long picImgId);

	List<ProductImage> selectByProductId(Long productId);

	Integer insertProductImage(ProductImage productImage, String userName);

	Integer deleteByPicImgId(Long picImgId);
}
