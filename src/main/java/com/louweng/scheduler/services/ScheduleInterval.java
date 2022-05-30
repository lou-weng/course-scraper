package com.louweng.scheduler.services;

import com.louweng.scheduler.exceptions.InvalidTimeException;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
public class ScheduleInterval 
{
    private double startTime;
    private double endTime;

    public void setTime(String startTime, String endTime) throws InvalidTimeException 
    {
        String[] startInterval = startTime.split(":");
        String[] endInterval = endTime.split(":");

        try 
        {
            double startHour = Double.parseDouble(startInterval[0]);
            double startMin = Double.parseDouble(startInterval[1]) / 60;

            double endHour = Double.parseDouble(endInterval[0]);
            double endMin = Double.parseDouble(endInterval[1]) / 60;

            /*
                No courses are able to start before 5AM or after 11PM
                No courses are able to end before 6AM or after 12PM
            */
            if (startHour < 5 || startHour > 23 || endHour < 6 ||  endHour > 24) 
            {
                log.info(startHour + " " + endInterval[0]);
                throw new InvalidTimeException("Course schedule starts or ends too early");
            }

            if (startMin < 0 || startMin >= 1 || endMin < 0 || endMin >= 1) 
            {
                throw new InvalidTimeException("Course minute is improperly formatted");
            }

            this.startTime = startHour + startMin;
            this.endTime = endHour + endMin;
            log.info("Creating schedule interval with : " + this.startTime + " " + this.endTime);

            if (this.endTime <= this.startTime) 
            {
                throw new InvalidTimeException("Course end time cannot be before start time");
            }
        } 
        catch (Throwable t)
        {
            log.error("Values: " + this.startTime + " " + this.endTime);
            throw new InvalidTimeException(t.getMessage());
        }
    }

    public boolean noIntervalAndTimeOverlap(double startTime, double endTime) 
    {
        return this.startTime >= endTime || this.endTime <= startTime;
    }
}
