package org.pussinboots.morning.administrator.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.pussinboots.morning.administrator.entity.Address;
import org.pussinboots.morning.common.support.page.PageInfo;

import java.util.List;

/**
 * 
* 项目名称：morning-user-service   
* 类名称：AddressMapper   
* 类描述：Address / 收获地址表 数据访问层接口 
* 创建人：陈星星   
* 创建时间：2017年5月9日 下午9:59:34   
*
 */
public interface AddressMapper extends BaseMapper<Address> {

    List<Address> listByPage(@Param("pageInfo")PageInfo pageIngo, @Param("search") String search, RowBounds rowBounds);
}