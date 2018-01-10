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
@Api(value = "首页json", description = "首页json")
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
    @ApiOperation(value = "根据type=【首页轮播广告列表 indexCarouselImgs 新品推荐 indexProductRecommendNew 推荐商品  indexProductRecommend 】获取首页json ",
            notes = "根据type=【首页轮播广告列表 indexCarouselImgs 新品推荐 indexProductRecommendNew 推荐商品  indexProductRecommend 】获取首页json")
    @GetMapping(value = "/index.json")
    public
    @ResponseBody
    Object index(@RequestParam(value = "type", required = true) String type) {
        // 首页轮播广告列表
        if (type.equals("indexCarouselImgs")) {
            List<AdvertDetail> indexCarouselImgs = advertDetailService.listByAdvertId(AdvertTypeEnum.INDEX_CAROUSEL.getType());
            return new OsResult(CommonReturnCode.SUCCESS, indexCarouselImgs);
        }
        //新品推荐 indexProductRecommendNew
        if (type.equals("indexProductRecommendNew")) {
            List<ProductVO> indexProductRecommendNew = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.NEW.getType());
            return new OsResult(CommonReturnCode.SUCCESS, indexProductRecommendNew);
        }
        //推荐商品  indexProductRecommend
        if (type.equals("indexProductRecommend")) {
            List<ProductVO> indexProductRecommend = productRecommendService.listByRecommendId(ProductRecommendTypeEnum.POPULAR.getType());
            return new OsResult(CommonReturnCode.SUCCESS, indexProductRecommend);
        }
        return new OsResult(CommonReturnCode.NOT_FOUND, CommonReturnCode.NOT_FOUND);
    }

}
