package org.pussinboots.morning.administrator.service;

import com.baomidou.mybatisplus.service.IService;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.user.entity.User;
import org.pussinboots.morning.common.support.page.PageInfo;


/**
 * 
* 项目名称：morning-administrator-facade   
* 类名称：ICustomerService
* 类描述：User / 用户表 业务逻辑层接口
* 创建人：zhancl
*
 */
public interface ICustomerService extends IService<User> {
	

	/**
	 * 根据分页信息/搜索内容查找用户列表
	 * @param pageInfo 分页信息
	 * @param search 搜索内容
	 * @return
	 */
	CustomerPageDTO listByPage(PageInfo pageInfo, String search);

}
