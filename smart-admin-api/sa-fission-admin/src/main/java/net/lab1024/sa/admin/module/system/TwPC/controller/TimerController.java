package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.xxl.job.core.handler.annotation.XxlJob;
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


    //@XxlJob("hycarryout")
    @Scheduled(cron = "*/5 * * * * ?")
    public void hycarryout()  {
        timerService.hycarryout();
    }

    //@XxlJob("hycarryplanout")
    @Scheduled(cron = "*/5 * * * * ?")
    public void hycarryplanout()  {
        timerService.hycarryplanout();
    }

    //@XxlJob("mockhycarryout")
    @Scheduled(cron = "*/5 * * * * ?")
    public void mockhycarryout()  {
        timerService.mockhycarryout();
    }

    //@XxlJob("mockhycarryplanout")
    @Scheduled(cron = "*/5 * * * * ?")
    public void mockhycarryplanout()  {
        timerService.mockhycarryplanout();
    }

    //@XxlJob("report")
    @Scheduled(cron = "0 */1 * * * ?")
    public void report()  {
        timerService.report();
    }

    //@XxlJob("updateBalanceCron")
    @Scheduled(cron = "0 */2 * * * ?")
    public synchronized void updateBalanceCron()  {
        timerService.updateBalanceCron();
    }

    //@XxlJob("mockUserStatus")
    @Scheduled(cron = "0 0 1 1 * ?")
    public void mockUserStatus()  {
        timerService.mockUserStatus();
    }
}

