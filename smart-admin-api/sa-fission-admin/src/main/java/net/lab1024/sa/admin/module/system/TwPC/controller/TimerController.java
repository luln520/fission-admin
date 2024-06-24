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


    @Scheduled(cron = "*/5 * * * * ?")
    public void hycarryout()  {
        timerService.hycarryout();
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void mockhycarryout()  {
        timerService.mockhycarryout();
    }


    @Scheduled(cron = "0 */5 * * * ?")
    public void report()  {
        timerService.report();
    }





}

