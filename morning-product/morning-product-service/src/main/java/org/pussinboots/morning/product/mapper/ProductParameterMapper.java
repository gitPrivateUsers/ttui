package org.pussinboots.morning.product.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.ProductParameter;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 
* 项目名称：morning-product-service   
* 类名称：ProductParameterMapper   
* 类描述：ProductParameter / 商品参数表 数据访问层接口               
* 创建人：陈星星   
* 创建时间：2017年4月14日 上午2:04:57   
*
 */
public interface ProductParameterMapper extends BaseMapper<ProductParameter> {


    List<ProductParameter> listByPage(@Param("pageInfo") PageInfo pageInfo, @Param("search") String search, RowBounds rowBounds, @Param("productId") Long productId);
}