package org.pussinboots.morning.product.service;

import java.util.List;

import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.ProductParameter;
import com.baomidou.mybatisplus.service.IService;

/**
 * 
* 项目名称：morning-product-facade   
* 类名称：IProductParameterService   
* 类描述：ProductParameter / 商品参数表 业务逻辑层接口      
* 创建人：陈星星   
* 创建时间：2017年4月14日 上午2:02:25   
*
 */
public interface IProductParameterService extends IService<ProductParameter> {
	
	/**
	 * 根据商品ID查找商品参数列表
	 * @param productId 商品ID
	 * @param status 参数状态
	 * @return List<ProductParameter>
	 */
	List<ProductParameter> listByProductId(Long productId, Integer status);

	Integer updateProductParameter(ProductParameter productParameter, String userName);

	BasePageDTO<ProductParameter> listByPage(PageInfo pageInfo, String search, Long parentId);

	Integer insertProductParameter(ProductParameter productParameter, String userName);
}
