package com.louweng.scheduler.services;

import java.util.ArrayList;
import java.util.List;

import com.louweng.scheduler.exceptions.InvalidTimeException;
import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.repositories.SectionRepository;
import com.louweng.scheduler.enums.Weekday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
@NoArgsConstructor
public class SchedulingService 
{

    @Autowired
    SectionRepository sectionRepository;

    public List<List<Section>> schedule(List<Section> sections) 
    {
        return null;
    }

    // throws InvalidTimeException if input sections are not formatted properly
    // throws IllegalArgumentException if section days are not formatted properly
    public boolean checkValidTimeScheduling(List<Section> validSchedule) throws InvalidTimeException, IllegalArgumentException
    {
        log.info("Starting to check if section list has valid time scheduling");
        List<ScheduleInterval> mondayList = new ArrayList<>();
        List<ScheduleInterval> tuesdayList = new ArrayList<>();
        List<ScheduleInterval> wednesdayList = new ArrayList<>();
        List<ScheduleInterval> thursdayList = new ArrayList<>();
        List<ScheduleInterval> fridayList = new ArrayList<>();

        for (Section s : validSchedule) {
            String[] days = s.getDays().split(" ");
            ScheduleInterval scheduleInterval = new ScheduleInterval();

            scheduleInterval.setTime(s.getStartTime(), s.getEndTime());

            for (String day : days) {
                log.info("Checking for invalid section times for " + day);
                boolean valid;
                switch (Weekday.valueOf(day.toUpperCase())) {
                    case MON:
                        valid = checkIfIntervalOverlaps(mondayList, scheduleInterval);
                        mondayList.add(scheduleInterval);
                        break;
                    case TUE:
                        valid = checkIfIntervalOverlaps(tuesdayList, scheduleInterval);
                        tuesdayList.add(scheduleInterval);
                        break;
                    case WED:
                        valid = checkIfIntervalOverlaps(wednesdayList, scheduleInterval);
                        wednesdayList.add(scheduleInterval);
                        break;
                    case THU:
                        valid = checkIfIntervalOverlaps(thursdayList, scheduleInterval);  
                        thursdayList.add(scheduleInterval);
                        break;
                    case FRI:
                        valid = checkIfIntervalOverlaps(fridayList, scheduleInterval);
                        fridayList.add(scheduleInterval);
                        break;
                    default:
                        log.error("Invalid day, returning false on valid time scheduling");
                        return false;
                }
                log.info("Valid is " + valid + " with schedule interval " + scheduleInterval.toString());
                if (!valid) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkIfIntervalOverlaps(List<ScheduleInterval> scheduleIntervals, ScheduleInterval scheduleInterval) 
    {
        for (ScheduleInterval sInterval: scheduleIntervals) 
        {
            log.info("Checking for overlap: " + sInterval.toString() + " " + scheduleInterval.toString());
            if (!scheduleInterval.noIntervalAndTimeOverlap(sInterval.getStartTime(), sInterval.getEndTime()))
            {
                log.info("Weekday check returning false");
                return false;
            }
        }
        log.info("Weekday check returning true");
        return true;
    }
}
