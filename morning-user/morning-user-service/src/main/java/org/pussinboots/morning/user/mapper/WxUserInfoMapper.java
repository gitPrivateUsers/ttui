package org.pussinboots.morning.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.user.entity.User;
import org.pussinboots.morning.user.entity.WxUserInfo;
import org.pussinboots.morning.user.pojo.vo.UserVO;

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
	 Integer insertWxUserInfo(WxUserInfo wxUserInfo);

}