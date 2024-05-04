package net.lab1024.sa.admin.module.system.TwPC.controller;

import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.service.impl.TimerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 定时器
 */

//缺一个矿机是否过期的任务

@Component
@Slf4j
public class TimerController {
    @Autowired
    private TimerServiceImpl timerService;


    @Scheduled(cron = "0 0 8 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    public void autokjsy() {
        timerService.autokjsy();
    }


    /**
     * 矿机到期时间结算
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void endkjsy() {
        timerService.endkjsy();
    }




    @Scheduled(cron = "*/5 * * * * ?")
    public void hycarryout()  {
        timerService.hycarryout();
    }



    @Scheduled(cron = "0 */1 * * * ?")
    public void kjUser() {
        timerService.kjUser();
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void curreny() {
        timerService.curreny();
    }


    @Scheduled(cron = "0 0 7 * * ?")
    public void currenyList() {
        timerService.currenyList();
    }



}

