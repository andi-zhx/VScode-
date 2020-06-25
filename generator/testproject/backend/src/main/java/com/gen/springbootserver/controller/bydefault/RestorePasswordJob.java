package com.gen.springbootserver.controller.bydefault;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.gen.springbootserver.service.bydefault.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RestorePasswordJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestorePasswordJob.class);

    private RestorePasswordService restorePasswordService;

    @Autowired
    public RestorePasswordJob(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;

    }

    @Scheduled(cron = "${gg.client.resetPasswordToken.clearJob}")
    public void reportCurrentTime() {
        LOGGER.info("Clear expired restore tokens");
        restorePasswordService.deleteExpiredRestorePasswordTokens(LocalDateTime.now(ZoneId.systemDefault()));
    }

}
