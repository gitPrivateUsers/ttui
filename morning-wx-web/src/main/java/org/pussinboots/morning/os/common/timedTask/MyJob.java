package org.pussinboots.morning.os.common.timedTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configurable
@EnableScheduling
@Component
public class MyJob{
@Autowired
        private TaskService taskService;
    Logger logger  = LoggerFactory.getLogger(MyJob.class);

//    @Scheduled(cron = "0/30 * * * * ?")
    @Scheduled(cron = "0 0/5 * * * ?")
    public void initializerTimes(){
        logger.info("............timedTaskStart............");
        long t1 = System.currentTimeMillis();
        taskService.batchUpdateProductRecommendStatusByTime();
        long t2 = System.currentTimeMillis();
        logger.info("商品推荐根据过期时间批量改状态 耗时   :: {} 秒",t2-t1/1000D);
        logger.info("............timedTaskEnd...........");

    }

}
