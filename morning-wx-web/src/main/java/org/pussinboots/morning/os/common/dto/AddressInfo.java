package org.pussinboots.morning.os.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.pussinboots.morning.user.entity.Address;

import java.io.Serializable;

/**
 * Created by ttui on 2018/1/9.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressInfo implements Serializable{
    //     @RequestParam("address") String address
//   , @RequestParam("provinceId") Integer provinceId//省
//   , @RequestParam("provinceName") String provinceName//省
//   , @RequestParam("cityId") Integer cityId//市
//   , @RequestParam("cityName") String cityName//市
//   , @RequestParam("districtId") Integer districtId //区
//   , @RequestParam("districtName") String districtName //区
//   , @RequestParam("linkMan") String linkMan //联系人
//   , @RequestParam("id") Long id
//   , @RequestParam("token") String token
//   , @RequestParam("isDefault") String isDefault
//   , @RequestParam("code") String code //邮政编码
//   , @RequestParam("mobile") String mobile //手机号
//    @RequestParam("token") String token
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer provinceId;
    private String provinceName;
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
    private String linkMan;
    private String address;
    private String mobile;
    private String code;
    private String isDefault;
    private String token;


//   private Address ad;
//
//    public Address getAd() {
//        return ad;
//    }

    public void setAd(Address ad) {
//        this.ad = ad;
        this.setProvinceId(ad.getProvinceId());
        this.setProvinceName(ad.getProvinceName());
        this.setCityName(ad.getCityName());
        this.setCityId(ad.getCityId());
        this.setDistrictName(ad.getDistrictName());
        this.setDistrictId(ad.getDistrictId());
        this.setIsDefault(ad.getUserTag());
        this.setLinkMan(ad.getUserName());
        this.setAddress(ad.getUserAdress());
        this.setMobile(ad.getUserPhone());
        this.setCode(String.valueOf(ad.getUserZipcode()));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
