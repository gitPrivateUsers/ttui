package org.pussinboots.morning.administrator.pojo.dto;

import org.pussinboots.morning.administrator.pojo.vo.CustomerVO;
import org.pussinboots.morning.common.support.page.PageInfo;

import java.io.Serializable;
import java.util.List;

public class CustomerPageDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户列表信息
	 */
	private List<CustomerVO> userVOs;

	/**
	 * 分页信息
	 */
	private PageInfo pageInfo;

	public CustomerPageDTO(List<CustomerVO> userVOs, PageInfo pageInfo) {
		super();
		this.userVOs = userVOs;
		this.pageInfo = pageInfo;
	}

	public List<CustomerVO> getUserVOs() {
		return userVOs;
	}

	public void setUserVOs(List<CustomerVO> userVOs) {
		this.userVOs = userVOs;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
