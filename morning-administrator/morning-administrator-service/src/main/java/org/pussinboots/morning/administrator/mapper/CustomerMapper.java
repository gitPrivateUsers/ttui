package org.pussinboots.morning.administrator.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.pussinboots.morning.administrator.entity.Customer;
import org.pussinboots.morning.administrator.pojo.vo.CustomerVO;
import org.pussinboots.morning.administrator.pojo.vo.UserVO;
import org.pussinboots.morning.common.support.page.PageInfo;

import java.util.List;

/**
 * 
* 项目名称：morning-user-service   
* 类名称：UserMapper   
* 类描述：User / 用户表 数据访问层接口       
* 创建人：陈星星   
* 创建时间：2017年4月8日 下午2:16:04   
*
 */
public interface CustomerMapper extends BaseMapper<Customer> {
	
	/**
	 * 根据登录名查找用户信息
	 * @param loginName 手机号码/邮箱
	 * @return
	 */
	Customer getByLoginName(String loginName);
	
	/**
	 * 根据用户ID查找用户显示信息
	 * @param userId 用户ID
	 * @return
	 */
	CustomerVO getById(Long userId);

	List<Customer> listByPage(@Param("pageInfo") PageInfo pageInfo, @Param("search") String search, RowBounds rowBounds);


}