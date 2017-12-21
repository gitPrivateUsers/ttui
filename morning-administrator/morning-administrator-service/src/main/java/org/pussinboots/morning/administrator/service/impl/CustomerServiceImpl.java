package org.pussinboots.morning.administrator.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.administrator.service.ICustomerService;
import org.pussinboots.morning.user.entity.User;
import org.pussinboots.morning.user.mapper.UserMapper;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.user.pojo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
* 项目名称：morning-administrator-service   
* 类名称：CustomerServiceImpl
* 类描述：User / 用户表 业务逻辑层接口实现
* 创建人：zhancl
*
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<UserMapper, User> implements ICustomerService {
	
	@Autowired
	private UserMapper userMapper;
	

	@Override
	public CustomerPageDTO listByPage(PageInfo pageInfo, String search) {
		Page<UserVO> page = new Page<>(pageInfo.getCurrent(), pageInfo.getLimit());
		List<UserVO> userVOs = userMapper.listByPage(pageInfo, search, page);
		pageInfo.setTotal(page.getTotal());
		return new CustomerPageDTO(userVOs, pageInfo);
	}

}
