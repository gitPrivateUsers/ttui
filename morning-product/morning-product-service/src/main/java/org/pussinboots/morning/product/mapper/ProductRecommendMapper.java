package org.pussinboots.morning.product.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.ProductRecommend;
import org.pussinboots.morning.product.entity.Recommend;
import org.pussinboots.morning.product.pojo.vo.ProductVO;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
* 项目名称：morning-product-service   
* 类名称：ProductRecommendMapper   
* 类描述：ProductRecommend / 商品推荐表 数据访问层接口        
* 创建人：陈星星   
* 创建时间：2017年4月11日 下午3:15:18   
*
 */
public interface ProductRecommendMapper extends BaseMapper<ProductRecommend> {


	List<ProductRecommend> listByPage(@Param("pageInfo") PageInfo pageInfo, @Param("search") String search, RowBounds rowBounds);
	/**
	 * 根据推荐位ID查找显示推荐商品
	 * @param recommendId 推荐位ID
	 * @param status 状态
	 * @param showNumber 显示数量
	 * @param date 显示时间
	 * @return
	 */
	List<ProductVO> listByRecommendId(@Param("recommendId") Long recommendId, @Param("status") Integer status,
			@Param("showNumber") Integer showNumber, @Param("date") Date date);
	
	/**
	 * 根据商品ID查找热门评论
	 * @param productId 商品ID
	 * @param type 评论类型
	 * @param status 状态
	 * @return
	 */
	ProductVO getComment(@Param("productId") Long productId, @Param("type") Integer type,
			@Param("status") Integer status);
}