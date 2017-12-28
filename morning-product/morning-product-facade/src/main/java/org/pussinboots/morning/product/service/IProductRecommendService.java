package org.pussinboots.morning.product.service;

import java.util.List;

import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.Product;
import org.pussinboots.morning.product.entity.ProductRecommend;
import org.pussinboots.morning.product.entity.Recommend;
import org.pussinboots.morning.product.pojo.vo.ProductVO;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
* 项目名称：morning-product-facade   
* 类名称：IProductRecommendService   
* 类描述：ProductRecommend / 商品推荐表 业务逻辑层接口       
* 创建人：陈星星   
* 创建时间：2017年4月11日 下午3:12:50   
*
 */
public interface IProductRecommendService extends IService<ProductRecommend> {

	BasePageDTO<ProductRecommend> listByPage(PageInfo pageInfo, String search);
	/**
	 * 根据推荐位ID查找显示推荐商品
	 * @param recommendId 推荐位ID
	 * @param status 状态
	 * @return
	 */
	List<ProductVO> listByRecommendId(Long recommendId);
	
	/**
	 * 根据推荐位ID查找热评商品
	 * @param recommendId 推荐位ID
	 * @param type 评论类型
	 * @return
	 */
	List<ProductVO> listComment(Long recommendId, Integer type);

	Integer insertProductRecommend(ProductRecommend productRecommend, String userName);
}
