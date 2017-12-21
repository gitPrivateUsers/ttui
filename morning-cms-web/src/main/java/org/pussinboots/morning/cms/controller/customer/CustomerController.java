package org.pussinboots.morning.cms.controller.customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.pussinboots.morning.administrator.entity.User;
import org.pussinboots.morning.administrator.pojo.dto.CustomerPageDTO;
import org.pussinboots.morning.administrator.service.ICustomerService;
import org.pussinboots.morning.administrator.service.IUserService;
import org.pussinboots.morning.cms.common.result.CmsPageResult;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Api(value = "管理员列表", description = "管理员列表")
public class CustomerController extends BaseController {
    @Autowired
    private ICustomerService customerService;

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
    @ApiOperation(value = "获取用户列表", notes = "根据分页信息/搜索内容获取用户列表")
    @RequiresPermissions("customer:list:view")
    @GetMapping(value = "/info")
    @ResponseBody
    public Object listUser(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
        CustomerPageDTO customerPageDTO = customerService.listByPage(pageInfo, search);
        return new CmsPageResult(customerPageDTO.getUserVOs(), customerPageDTO.getPageInfo().getTotal());
    }
}
