package org.pussinboots.morning.administrator.pojo.dto;

import org.pussinboots.morning.administrator.entity.Address;
import org.pussinboots.morning.common.support.page.PageInfo;

import java.io.Serializable;
import java.util.List;

public class AddressPageDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户地址列表信息
	 */
	private List<Address> addressVOs;

	/**
	 * 分页信息
	 */
	private PageInfo pageInfo;

	public AddressPageDTO(List<Address> addressVOs, PageInfo pageInfo) {
		super();
		this.addressVOs = addressVOs;
		this.pageInfo = pageInfo;
	}

	public List<Address> getAddressVOs() {
		return addressVOs;
	}

	public void setAddressVOs(List<Address> addressVOs) {
		this.addressVOs = addressVOs;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
