package org.pussinboots.morning.administrator.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.pussinboots.morning.administrator.entity.Customer;
import org.pussinboots.morning.administrator.mapper.CustomerMapper;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.administrator.pojo.vo.CustomerVO;
import org.pussinboots.morning.administrator.service.ICustomerService;
import org.pussinboots.morning.common.support.page.PageInfo;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
	
	@Autowired
	private CustomerMapper userMapper;
	

	@Override
	public CustomerPageDTO listByPage(PageInfo pageInfo, String search) {
		Page<CustomerVO> page = new Page<>(pageInfo.getCurrent(), pageInfo.getLimit());
		List<CustomerVO> userVOs = userMapper.listByPage(pageInfo, search, page);
		pageInfo.setTotal(page.getTotal());
		return new CustomerPageDTO(userVOs, pageInfo);
	}

}
