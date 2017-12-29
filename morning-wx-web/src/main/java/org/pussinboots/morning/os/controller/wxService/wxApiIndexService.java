package org.pussinboots.morning.os.controller.wxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.online.common.enums.AdvertTypeEnum;
import org.pussinboots.morning.online.entity.AdvertDetail;
import org.pussinboots.morning.online.service.IAdvertDetailService;
import org.pussinboots.morning.os.common.result.OsResult;
import org.pussinboots.morning.product.common.enums.ProductRecommendTypeEnum;
import org.pussinboots.morning.product.pojo.vo.ProductVO;
import org.pussinboots.morning.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/view.index.json")
    public
    @ResponseBody
    Object index() {
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
