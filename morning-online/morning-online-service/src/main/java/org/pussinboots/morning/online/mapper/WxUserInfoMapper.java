package org.pussinboots.morning.online.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.pussinboots.morning.online.entity.WxUserInfo;

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
public interface WxUserInfoMapper extends BaseMapper<WxUserInfo> {

	WxUserInfo getByOpenId(String loginName);

	 WxUserInfo getById(Long userId);

	Integer insert(WxUserInfo wxUserInfo);

	List<WxUserInfo> selectByToken(String token);
}