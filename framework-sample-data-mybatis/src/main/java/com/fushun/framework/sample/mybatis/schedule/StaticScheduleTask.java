package com.fushun.framework.sample.mybatis.schedule;

import com.fushun.framework.sample.mybatis.entity.SysUser;
import com.fushun.framework.sample.mybatis.service.SysUser2Service;
import com.fushun.framework.sample.mybatis.service.SysUser3Service;
import com.fushun.framework.sample.mybatis.service.SysUser4Service;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@Slf4j
public class StaticScheduleTask {

    private final Logger logger = LoggerFactory.getLogger(StaticScheduleTask.class);

    @Autowired
    private SysUser2Service sysUser2Service;

    @Autowired
    private SysUser3Service sysUs3erService;

    @Autowired
    private SysUser4Service sysUser4Service;
    /**
     * 每分钟查询是否存在需要发送的防腐通知
     */
    @Scheduled(cron = "*/1 * * * * ?")
    public void sendPreventCorruptionSMS2() {
        log.info("开始定时任务 sendPreventCorruptionSMS(发送防腐通知) startTime:{}", LocalDateTime.now());
        try {
            SysUser sysUser=sysUser2Service.getByUserId(1L);
            logger.info("sysUser:{}",sysUser);
        } catch (Exception e) {
            logger.error("定时任务异常 sendPreventCorruptionSMS(发送防腐通知)", e);
        }
        log.info("定时任务结束 sendPreventCorruptionSMS(发送防腐通知) endTime:{}", LocalDateTime.now());
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void sendPreventCorruptionSMS3() {
        log.info("开始定时任务 sendPreventCorruptionSMS(发送防腐通知) startTime:{}", LocalDateTime.now());
        try {
            SysUser sysUser=sysUs3erService.getByUserId(1L);
            logger.info("sysUser:{}",sysUser);
        } catch (Exception e) {
            logger.error("定时任务异常 sendPreventCorruptionSMS(发送防腐通知)", e);
        }
        log.info("定时任务结束 sendPreventCorruptionSMS(发送防腐通知) endTime:{}", LocalDateTime.now());
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void sendPreventCorruptionSMS4() {
        log.info("开始定时任务 sendPreventCorruptionSMS(发送防腐通知) startTime:{}", LocalDateTime.now());
        try {
            SysUser sysUser=sysUser4Service.getByUserId(1L);
            logger.info("sysUser:{}",sysUser);
        } catch (Exception e) {
            logger.error("定时任务异常 sendPreventCorruptionSMS(发送防腐通知)", e);
        }
        log.info("定时任务结束 sendPreventCorruptionSMS(发送防腐通知) endTime:{}", LocalDateTime.now());
    }

}
