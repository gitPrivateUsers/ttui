package org.pussinboots.morning.administrator.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.pussinboots.morning.administrator.entity.Customer;
import org.pussinboots.morning.administrator.mapper.CustomerMapper;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.administrator.pojo.vo.CustomerVO;
import org.pussinboots.morning.administrator.service.ICustomerService;
import org.pussinboots.morning.administrator.util.CustomerUtils;
import org.pussinboots.morning.administrator.util.PasswordUtils;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.common.exception.ValidateException;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 项目名称：morning-administrator-service
 * 类名称：CustomerServiceImpl
 * 类描述：User / 用户表 业务逻辑层接口实现
 * 创建人：zhancl
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public CustomerPageDTO listByPage(PageInfo pageInfo, String search) {
        Page<Customer> page = new Page<>(pageInfo.getCurrent(), pageInfo.getLimit());
        List<Customer> userVOs = customerMapper.listByPage(pageInfo, search, page);
        pageInfo.setTotal(page.getTotal());
        return new CustomerPageDTO(userVOs, pageInfo);
    }

    @Override
    public Integer insertCustomer(Customer customer, String userName) {


        // 邮箱唯一性验证（邮箱存在且已经被激活）
        Customer queryUser = new Customer();
        queryUser.setEmail(customer.getEmail());
        Customer emailUser = customerMapper.selectOne(queryUser);
        if (emailUser != null && StatusEnum.ACTIVATED.getStatus().equals(emailUser.getEmailIsActive())) {
            throw new ValidateException(CommonReturnCode.BAD_REQUEST.getCode(), "该电子邮箱已被注册了");
        }
        if (emailUser != null && StatusEnum.NONACTIVATED.getStatus().equals(emailUser.getEmailIsActive())) {
            customerMapper.deleteById(emailUser.getUserId()); // 如果未被激活则删除用户
        }
        customer.setUserNumber(CustomerUtils.getUserNumber());
        customer.setSalt(PasswordUtils.getSalt());
        customer.setPicImg(CustomerUtils.getPicImg());
        customer.setRegeistTime(new Date());
        customer.setCreateBy(customer.getUserName());
        customer.setLoginPassword(PasswordUtils.getMd5(customer.getLoginPassword(), customer.getUserNumber(), customer.getSalt()));
        return customerMapper.insert(customer);
    }

    @Override
    public Integer updateUserId(Customer customer, String userName) {

        customer.setUpdateTime(new Date());
        customer.setUpdateBy(userName);
        return customerMapper.updateById(customer);
    }

}
