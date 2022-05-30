package com.louweng.scheduler.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ScheduleException extends Exception 
{
    public ScheduleException(String errorMessage) 
    {
        super(errorMessage);
        log.error("SCHEDULE ERROR:" + errorMessage);
    }
}
