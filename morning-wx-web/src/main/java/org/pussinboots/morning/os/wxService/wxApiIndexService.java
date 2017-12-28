package org.pussinboots.morning.os.wxService;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.base.BasePageDTO;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.common.support.page.PageInfo;
import org.pussinboots.morning.online.common.enums.AdvertTypeEnum;
import org.pussinboots.morning.online.entity.AdvertDetail;
import org.pussinboots.morning.online.service.IAdvertDetailService;
import org.pussinboots.morning.order.common.enums.OrderStatusEnum;
import org.pussinboots.morning.order.entity.Order;
import org.pussinboots.morning.order.entity.OrderProduct;
import org.pussinboots.morning.order.entity.OrderShipment;
import org.pussinboots.morning.order.pojo.vo.OrderShoppingCartVO;
import org.pussinboots.morning.order.service.IOrderProductService;
import org.pussinboots.morning.order.service.IOrderService;
import org.pussinboots.morning.order.service.IOrderShipmentService;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.os.common.security.AuthorizingUser;
import org.pussinboots.morning.os.common.util.SingletonLoginUtils;
import org.pussinboots.morning.product.common.constant.ProductConstantEnum;
import org.pussinboots.morning.product.common.enums.ProductRecommendTypeEnum;
import org.pussinboots.morning.product.common.enums.ProductSortEnum;
import org.pussinboots.morning.product.entity.Category;
import org.pussinboots.morning.product.entity.ProductAttribute;
import org.pussinboots.morning.product.entity.ProductImage;
import org.pussinboots.morning.product.entity.ProductParameter;
import org.pussinboots.morning.product.pojo.dto.ProductSpecificationDTO;
import org.pussinboots.morning.product.pojo.vo.CartVO;
import org.pussinboots.morning.product.pojo.vo.ProductVO;
import org.pussinboots.morning.product.pojo.vo.ShoppingCartVO;
import org.pussinboots.morning.product.service.*;
import org.pussinboots.morning.user.entity.Address;
import org.pussinboots.morning.user.entity.Favorite;
import org.pussinboots.morning.user.pojo.vo.UserVO;
import org.pussinboots.morning.user.service.IAddressService;
import org.pussinboots.morning.user.service.IFavoriteService;
import org.pussinboots.morning.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：morning-wx-web Maven Webapp
 * 类名称：wxService
 * 类描述：微信小程序api
 * 创建人：zhancl
 */
@Controller
@Api(value = "微信小程序api", description = "微信小程序api")
public class wxApiIndexService extends BaseController {

    @Autowired
    private IAdvertDetailService advertDetailService;
    @Autowired
    private IProductRecommendService productRecommendService;

    /**
     * 首页数据结果集合
     *
     * @return Object
     */
    @ApiOperation(value = "首页json", notes = "首页展示json")
    @GetMapping(value = "/index.json")
    public
    @ResponseBody
    OsResult index() {
        Map<String, Object> model = new HashMap<String, Object>();
        // 首页轮播广告列表
        List<AdvertDetail> indexCarouselImgs = advertDetailService.listByAdvertId(AdvertTypeEnum.INDEX_CAROUSEL.getType());
        model.put("indexCarouselImgs", indexCarouselImgs);
        //新品推荐 indexProductRecommendNew
        List<ProductVO> indexProductRecommendNew = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.NEW.getType());
        model.put("indexProductRecommendNew", indexProductRecommendNew);
        //推荐商品  productRecommend
        List<ProductVO> indexProductRecommend = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.POPULAR.getType());
        model.put("indexProductRecommend", indexProductRecommend);
        return new OsResult(CommonReturnCode.SUCCESS, String.valueOf(JSONObject.fromObject(model)));
    }

}
