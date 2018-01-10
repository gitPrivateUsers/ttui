package org.pussinboots.morning.common.constant;

import org.pussinboots.morning.common.exception.ReturnCode;

public enum CommonReturnCode implements ReturnCode {
	
	/** 请求失败 */
	FAILED(0, "failed"),
	/** 请求成功 */
	SUCCESS(1, "success"),
	/** 未知错误 */
	UNKNOWN_ERROR(-1, "未知错误,请联系管理员!"),
	
	/** 200请求成功 */
	OK(200, "请求成功"),
	/** 207频繁操作 */
	MULTI_STATUS(207, "频繁操作"),
	
	/** 303登录失败 */
	LOGIN_FAIL(303, "登录失败 "),
	
	/** 400请求参数出错 */
	BAD_REQUEST(400, "请求参数出错"),
	/** 401没有登录 */
	UNAUTHORIZED(401, "您未登录或者登录已超时,请先登录!"),
	/** 403没有权限 */
	FORBIDDEN(403, "没有权限"),
	/** 404找不到页面 */
	NOT_FOUND(404, "找不到页面"),
	/** 408请求超时 */
	REQUEST_TIMEOUT(408, "请求超时"),
	/** 409发生冲突 */
	CONFLICT(409, "发生冲突"),
	/** 410已被删除 */
	GONE(410, "已被删除"),
	/** 423已被锁定 */
	LOCKED(423, "已被锁定"),

	/** api返回分类商品失败 */
	FAIL_PRODUCT_CATEGORY(102,"没有对应的分类及商品,api返回分类商品失败!"),
	/** api返回分类商品失败 */
	FAIL_PRODUCT_INFO(104,"没有对应的商品详情,api返回商品详情失败!"),

	/** 该账号已存在 */
	ACCOUNT_EXISTENCE(1005, "该账号已存在"),
//	/** 登录超时 */
//	LOGIN_TIMEOUT(1006, "登录超时"),
	/** 暂无地址可选 */
	NOT_ADDRESS(1007, "暂无地址可选"),
	/** 该用户被冻结 */
	THE_USER_IS_FROZEN(1008, "该用户被冻结"),
	/** 修改失败 */
	FAIL_UPDATE(1009, "修改失败"),



	/** 500服务器出错 */
	INTERNAL_SERVER_ERROR(500, "服务器出错");

	/** 返回状态码 */
	private Integer code;

	/** 返回消息 */
	private String message;
	
	private CommonReturnCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
