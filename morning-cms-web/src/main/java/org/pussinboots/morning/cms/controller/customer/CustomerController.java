package org.pussinboots.morning.cms.controller.customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.pussinboots.morning.administrator.entity.Address;
import org.pussinboots.morning.administrator.entity.Customer;
import org.pussinboots.morning.administrator.pojo.dto.AddressPageDTO;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.administrator.service.IAddressService;
import org.pussinboots.morning.administrator.service.ICustomerService;
import org.pussinboots.morning.cms.common.result.CmsPageResult;
import org.pussinboots.morning.cms.common.result.CmsResult;
import org.pussinboots.morning.cms.common.security.AuthorizingUser;
import org.pussinboots.morning.cms.common.util.SingletonLoginUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

/**
 *
 * 项目名称：morning-cms-web Maven Webapp
 * 类名称：CustomerController
 * 类描述：用户管理表示层控制器
 * 创建人：zhancl
 * 创建时间：2017年12月21日 上午10:12:17
 *
 */
@Controller
@RequestMapping(value = "/customer/detail")
@Api(value = "用户管理", description = "用户管理")
public class CustomerController extends BaseController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IAddressService addressService;

    /**
     * GET 管理员列表页面
     * @param model
     * @return
     */
    @ApiOperation(value = "用户列表页面", notes = "用户列表页面")
    @RequiresPermissions("customer:list:view")
    @GetMapping(value = "/view")
    public String getListPage(Model model) {
        return "/modules/customer/customer_list";
    }

    /**
     * GET 管理员列表
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "根据分页信息获取用户列表")
    @RequiresPermissions("customer:list:view")
    @GetMapping(value = "/info")
    @ResponseBody
    public Object customerList(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
        CustomerPageDTO customerPageDTO = customerService.listByPage(pageInfo, search);
        return new CmsPageResult(customerPageDTO.getUserVOs(), customerPageDTO.getPageInfo().getTotal());
    }


    /**
     * GET 创建product
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "创建用户")
    @RequiresPermissions("customer:detail:create")
    @GetMapping(value = "/create")
    public String getInsertPage(Model model) {
        return "/modules/customer/customer_create";
    }

    /**
     * POST 创建商品
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "创建用户")
    @RequiresPermissions("customer:detail:create")
    @PostMapping(value = "")
    @ResponseBody
    public Object insert(Customer customer,
                         @RequestParam(value = "sex", defaultValue = "0") Integer sex,
                         @RequestParam(value = "status", defaultValue = "0") Integer status) {
        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
            customer.setSex(sex);
            customer.setStatus(status);
            Integer count = customerService.insertCustomer(customer, authorizingUser.getUserName());
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

    /**
     * GET 更新商品信息
     * @return
     */
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @RequiresPermissions("customer:detail:edit")
    @GetMapping(value = "/{userId}/edit")
    public String getUpdatePage(Model model, @PathVariable("userId") Long userId) {
        // 广告信息
        Customer customer = customerService.selectById(userId);
        model.addAttribute("customer", customer);

        return "/modules/customer/customer_update";
    }

    /**
     * PUT 更新商品信息
     * @return
     */
    @ApiOperation(value = "更新用户信息", notes = "根据ID修改")
    @RequiresPermissions("customer:detail:edit")
    @PutMapping(value = "/{userId}")
    @ResponseBody
    public Object update(Customer customer, @PathVariable("userId") Long userId,
                         @RequestParam(value = "sex", defaultValue = "0") Integer sex,
                         @RequestParam(value = "status", defaultValue = "0") Integer status) {

        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
            // 更新用户记录
            customer.setStatus(status);
            Integer count = customerService.updateUserId(customer, authorizingUser.getUserName());
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

    /**
     * GET 查看用户地址信息
     * @return
     */
    @ApiOperation(value = "查看用户地址信息页面", notes = "查看用户地址信息页面")
//    @RequiresPermissions("customer:detail:edit")
    @GetMapping(value = "/{userId}/address")
    public String getAddressPage(Model model, @PathVariable("userId") Long userId) {
         model.addAttribute("userId", userId);
         return "/modules/customer/customer_address_list";
    }

    @ApiOperation(value = "获取用户地址列表",notes = "根据用户id获取用户分页信息")
//    @RequiresPermissions("customer:detail:edit")
    @GetMapping(value = "/address/{userId}")
    @ResponseBody
    public Object addressList(PageInfo pageInfo, @PathVariable("userId") Long userId) {
        BasePageDTO<Address> basePageDTO = addressService.listByUserId(userId,pageInfo);
        return new CmsPageResult(basePageDTO.getList(), basePageDTO.getPageInfo().getTotal());
    }

    @ApiOperation(value = "删除地址信息", notes = "根据url用户ID删除地址信息")
//    @RequiresPermissions("administrator:list:delete")
    @DeleteMapping(value = "/address/{userId}/{adressId}")
    @ResponseBody
    public Object delete(@PathVariable("userId") Long userId,@PathVariable("adressId") Long addressId) {
        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
            Integer count = addressService.deleteByAddressId(userId,addressId);
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

    /**
     * GET 新增用户地址
     * @return
     */
    @ApiOperation(value = "新增用户地址", notes = "新增用户地址")
//    @RequiresPermissions("customer:detail:create")
    @GetMapping(value = "/address/create/{userId}")
    public String getInsertAddressPage(Model model,@PathVariable("userId") Long userId) {
        model.addAttribute("userId",userId);
        return "/modules/customer/customer_address_create";
    }

    /**
     * POST 新增用户地址
     * @return
     */
    @ApiOperation(value = "新增用户地址", notes = "新增用户地址")
//    @RequiresPermissions("customer:detail:create")
    @PostMapping(value = "/address/{userId}/create")
    @ResponseBody
    public Object insert(@PathVariable("userId") Long userId,Address address) {
        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
            Integer count = addressService.insertAddress(userId,address);
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }


    /**
     * GET 更新商品地址信息
     * @return
     */
    @ApiOperation(value = "更新用户地址页面", notes = "更新用户地址页面")
//    @RequiresPermissions("customer:detail:edit")
    @GetMapping(value = "/address/{addressId}/update")
    public String getAddressUpdate(Model model,@PathVariable("addressId") Long addressId){
        Address address = addressService.selectById(addressId);
        model.addAttribute("address",address);
        return "/modules/customer/customer_address_update";
    }

    /**
     * PUT 更新用户地址信息
     * @return
     */
    @ApiOperation(value = "更新用户地址信息", notes = "根据ID更新")
//    @RequiresPermissions("customer:detail:edit")
    @PutMapping(value = "/address/update/{addressId}")
    @ResponseBody
    public Object putAddressUpdate(Address address,@PathVariable("addressId") Long addressId) {
        AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
        if (authorizingUser != null) {
             //更新用户地址记录
            Integer count = addressService.updateAddressId(addressId,address);
            return new CmsResult(CommonReturnCode.SUCCESS, count);
        } else {
            return new CmsResult(CommonReturnCode.UNAUTHORIZED);
        }
    }

}
