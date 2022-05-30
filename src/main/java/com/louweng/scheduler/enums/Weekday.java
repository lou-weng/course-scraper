package com.louweng.scheduler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Weekday 
{
    MON("Mon"), 
    TUE("Tue"),
    WED("Wed"),
    THU("Thu"),
    FRI("Fri");

    @Getter 
    private String name;

    @Override
    public String toString() 
    {
        return name;
    }
}
