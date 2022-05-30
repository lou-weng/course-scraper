package com.louweng.scheduler.exceptions;

public class InvalidTimeException extends ScheduleException 
{
    public InvalidTimeException(String errorMessage) 
    {
        super(errorMessage);
    }
}
