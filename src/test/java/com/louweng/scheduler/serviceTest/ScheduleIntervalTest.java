package com.louweng.scheduler.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.louweng.scheduler.exceptions.InvalidTimeException;
import com.louweng.scheduler.services.ScheduleInterval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleIntervalTest 
{
    ScheduleInterval scheduleInterval;

    @BeforeEach
    public void setupTests() 
    {
        scheduleInterval = new ScheduleInterval();
    }

    @Test
    public void scheduleInterval_validInterval_willPass() 
    {
        try
        {
            scheduleInterval.setTime("10:30", "11:45");
            assertEquals(10.5, scheduleInterval.getStartTime());
            assertEquals(11.75, scheduleInterval.getEndTime());
        }
        catch (InvalidTimeException e) 
        {
            log.error("Incorrectly threw an exception");
            fail();
        }
    }

    @Test
    public void scheduleInterval_invalidIntervalHourOutOfBounds_willFail() 
    {
        try 
        {
            scheduleInterval.setTime("76:30", "98:40");
            fail();
        } 
        catch (InvalidTimeException e)
        {
            log.error("Correctly threw an error");
        }
    }

    @Test
    public void scheduleInterval_invalidIntervalMinuteOutOfBounds_willFail() 
    {
        try 
        {
            scheduleInterval.setTime("12:75", "3:80");
            fail();
        } 
        catch (InvalidTimeException e)
        {
            log.error("Correctly threw an error");
        }
    }

    @Test
    public void scheduleInterval_invalidTimeHourFormat_willFail() 
    {
        try 
        {
            scheduleInterval.setTime("DF:30", "11:23");
        } 
        catch (InvalidTimeException e)
        {
            log.error("Correctly threw an error");
        }
    }

    @Test
    public void scheduleInterval_invalidTimeMinuteFormat_willFail() 
    {
        try 
        {
            scheduleInterval.setTime("10:IO", "11:40");
        } 
        catch (InvalidTimeException e)
        {
            log.error("Correctly threw an error");
        }
    }

    @Test
    public void scheduleInterval_endTimeBeforeStartTime_willFail() 
    {
        try 
        {
            scheduleInterval.setTime("10:00", "9:45");
        } 
        catch (InvalidTimeException e)
        {
            log.error("Correctly threw an error");
        }
    }

    @Test
    public void intervalOverlapsTime_validNoOverlap_willPass() 
    {
        try
        {
            scheduleInterval.setTime("10:30", "11:45");
            assertTrue(scheduleInterval.noIntervalAndTimeOverlap(14.5, 15.5));
        }
        catch (InvalidTimeException e) 
        {
            log.error("Incorrectly threw an exception");
            fail();
        }
    }

    @Test
    public void intervalOverlapsTime_invalidSameTime_willFail() 
    {
        try
        {
            scheduleInterval.setTime("10:30", "11:45");
            assertFalse(scheduleInterval.noIntervalAndTimeOverlap(10.5, 11.75));
        }
        catch (InvalidTimeException e) 
        {
            log.error("Incorrectly threw an exception");
            fail();
        }
    }

    @Test
    public void intervalOverlapsTime_invalidIntervalOverlapStart_willFail() 
    {
        try
        {
            scheduleInterval.setTime("10:30", "11:45");
            assertFalse(scheduleInterval.noIntervalAndTimeOverlap(10.75, 12.75));
        }
        catch (InvalidTimeException e) 
        {
            log.error("Incorrectly threw an exception");
            fail();
        }
    }

    @Test
    public void intervalOverlapsTime_invalidIntervalOverlapEnd_willFail() 
    {
        try
        {
            scheduleInterval.setTime("10:30", "11:45");
            assertFalse(scheduleInterval.noIntervalAndTimeOverlap(9.75, 10.75));
        }
        catch (InvalidTimeException e) 
        {
            log.error("Incorrectly threw an exception");
            fail();
        }
    }

    @Test
    public void intervalOverlapsTime_invalidIntervalOverlap_willFail()
    {
        try
        {
            scheduleInterval.setTime("10:30", "11:45");
            assertFalse(scheduleInterval.noIntervalAndTimeOverlap(10.0, 12.75));
        }
        catch (InvalidTimeException e) 
        {
            log.error("Incorrectly threw an exception");
            fail();
        }
    }
}
