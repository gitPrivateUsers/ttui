package org.pussinboots.morning.user.service;

import com.baomidou.mybatisplus.service.IService;
import org.pussinboots.morning.user.entity.WxUserInfo;

/**
 * 
* 项目名称：morning-user-facade   
* 类名称：IUserService   
* 类描述：User / 用户表 业务逻辑层接口   
* 创建人：陈星星   
* 创建时间：2017年4月8日 下午2:14:21   
*
 */
public interface IWxUserInfoService extends IService<WxUserInfo> {

	Integer insertWxUserInfo(WxUserInfo wxUserInfo);


	WxUserInfo getWxUserInfo(String openId);

	WxUserInfo getWxUserInfoByWxUserId(long wxUserId);
	WxUserInfo getWxUserInfoByToken(String token);
}
