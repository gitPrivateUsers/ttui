package org.pussinboots.morning.cms.controller.customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pussinboots.morning.administrator.entity.Address;
import org.pussinboots.morning.administrator.pojo.dto.AddressPageDTO;
import org.pussinboots.morning.cms.common.result.CmsPageResult;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.administrator.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/address/list")
@Api(value = "地址信息", description = "地址信息")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;
    /**
     * GET 管理员列表页面
     * @param model
     * @return
     */
    @ApiOperation(value = "用户地址信息页面", notes = "用户地址信息页面")
//    @RequiresPermissions("customer:list:view")
    @GetMapping(value = "/view")
    public String getListPage(Model model) {
        return "/modules/address/address_list";
    }

    /**
     * GET 管理员列表
     * @return
     */
    @ApiOperation(value = "获取地址分页", notes = "根据分页信息获取用户地址信息列表")
//    @RequiresPermissions("customer:list:view")
    @GetMapping(value = "info")
    @ResponseBody
    public Object addressList(PageInfo pageInfo, @RequestParam(required = false, value = "search") String search) {
         AddressPageDTO addressPageDTO = addressService.listByPage(pageInfo,search);
        return new CmsPageResult(addressPageDTO.getAddressVOs(), addressPageDTO.getPageInfo().getTotal());
    }

}
