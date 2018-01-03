package org.pussinboots.morning.os.common.dto;

import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.pojo.vo.ProductVO;

import java.io.Serializable;
import java.util.List;

public class ProductCategoryDto implements Serializable{

	private static final long serialVersionUID = 1L;
	// 排序方式
	private int sort;
	// 当前类目信息
	private Category category;

	/**
	 * 分页信息
	 */
	private PageInfo pageInfo;
	/**
	 * 分页实体列表
	 */
	private List<ProductVO> productList;
	// 子类目列表
	List<Category> lowerCategories;
	// 父类目列表
	List<Category> upperCategories;

	public List<Category> getUpperCategories() {
		return upperCategories;
	}

	public void setUpperCategories(List<Category> upperCategories) {
		this.upperCategories = upperCategories;
	}

	public List<Category> getLowerCategories() {
		return lowerCategories;
	}

	public void setLowerCategories(List<Category> lowerCategories) {
		this.lowerCategories = lowerCategories;
	}

	public List<ProductVO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductVO> productList) {
		this.productList = productList;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
