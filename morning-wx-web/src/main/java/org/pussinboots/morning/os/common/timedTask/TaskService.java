package org.pussinboots.morning.os.common.timedTask;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.product.entity.ProductRecommend;
import org.pussinboots.morning.product.service.IProductRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by ttui on 2018/1/11.
 */
@Component
public class TaskService {
    @Autowired
    private IProductRecommendService productRecommendService;

    public void batchUpdateProductRecommendStatusByTime() {
        //取出全部的数据
        ProductRecommend pr = new ProductRecommend();
//        pr.setStatus(StatusEnum.SHOW.getStatus());
        List<ProductRecommend> productRecommends = productRecommendService.selectList(new EntityWrapper<ProductRecommend>(pr));
        //验证是否时间已经结束
        for (ProductRecommend prd : productRecommends) {
            if (prd.getEndTime() != null && prd.getBeginTime() != null) {
                if (Objects.equals(prd.getStatus(), StatusEnum.HIDDEN.getStatus())) {
                    //隐藏的数据结束时间大于当前时间并且开始时间小于当前时间的状态改为显示
                    if (prd.getEndTime().after(new Date()) && prd.getBeginTime().before(new Date())) {
                        System.out.println("修改了ID：" + prd.getRecommendProductId() + "的状态！SHOW");
                        prd.setUpdateTime(new Date());
                        prd.setStatus(StatusEnum.SHOW.getStatus());
                        productRecommendService.updateById(prd);
                    }
                    //显示的数据结束时间小于当前时间或者开始时间大于当前时间的 状态改为隐藏
                } else if (prd.getEndTime().before(new Date()) || prd.getBeginTime().after(new Date())) {
                    System.out.println("修改了ID：" + prd.getRecommendProductId() + "的状态！HIDDEN");
                    prd.setUpdateTime(new Date());
                    prd.setStatus(StatusEnum.HIDDEN.getStatus());
                    productRecommendService.updateById(prd);
                }
                //时间为null的非隐藏状态的统一更新隐藏
            } else if (!Objects.equals(prd.getStatus(), StatusEnum.HIDDEN.getStatus())) {
                prd.setUpdateTime(new Date());
                prd.setStatus(StatusEnum.HIDDEN.getStatus());
                productRecommendService.updateById(prd);
            }

        }
    }
}
