package com.louweng.scheduler.serviceTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.services.SchedulingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchedulingServiceTest 
{
    SchedulingService schedulingService = new SchedulingService();

    List<Section> schedule;
    Section s1;
    Section s2;
    Section s3;
    Section s4;

    @BeforeEach
    public void setupTests() 
    {
        schedule = new ArrayList<>();
    }

    @Test
    public void checkValidScheduling_noSections_shouldPass() {
        try 
        {
            assertTrue(schedulingService.checkValidTimeScheduling(schedule));
        }
        catch (Exception e) 
        {
            fail();
        }
    }

    @Test
    public void checkValidScheduling_validTimeSchedule_shouldPass() 
    {
        s1 = new Section("101", "Mon Tue Wed", "10:30", "12:00", "COMM", "202");
        s2 = new Section("102", "Thu Fri", "10:30", "12:00", "CPSC", "202");
        s3 = new Section("103", "Mon Tue Wed", "13:30", "15:00", "ANTH", "202");
        schedule.add(s1);
        schedule.add(s2);
        schedule.add(s3);

        try 
        {
            assertTrue(schedulingService.checkValidTimeScheduling(schedule));
        }
        catch (Exception e) 
        {
            fail();
        }
    }

    @Test
    public void checkValidScheduling_invalidCourseDuplicate_shouldFail() 
    {
        s1 = new Section("101", "Mon Tue Wed", "10:30", "12:00", "COMM", "202");
        s2 = new Section("102", "Mon Tue Wed", "10:30", "12:00", "COMM", "202");
        s3 = new Section("103", "Mon Tue Wed", "13:30", "15:00", "CPSC", "202");
        schedule.add(s1);
        schedule.add(s2);
        schedule.add(s3);

        try 
        {
        assertFalse(schedulingService.checkValidTimeScheduling(schedule));
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    public void checkValidScheduling_invalidTimeConflict_shouldFail() 
    {
        s1 = new Section("101", "Mon Tue Wed", "10:30", "12:00", "COMM", "202");
        s2 = new Section("102", "Thu Fri", "10:30", "12:00", "CPSC", "202");
        s3 = new Section("103", "Mon Tue Wed", "11:30", "15:00", "ANTH", "202");
        schedule.add(s1);
        schedule.add(s2);
        schedule.add(s3);

        try 
        {
        assertFalse(schedulingService.checkValidTimeScheduling(schedule));
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test 
    public void checkValidScheduling_invalidDates_shouldFail() 
    {
        s1 = new Section("101", "Mon Tueday Wed", "10:30", "12:00", "COMM", "202");
        s2 = new Section("102", "Thuday Fri", "10:30", "12:00", "CPSC", "202");
        s3 = new Section("103", "Mon Tue Wednesday", "13:30", "15:00", "ANTH", "202");
        schedule.add(s1);
        schedule.add(s2);
        schedule.add(s3);

        try 
        {
            assertFalse(schedulingService.checkValidTimeScheduling(schedule));
            fail();
        }
        catch (Exception e)
        {
            log.error("Successfully threw an error " + e.getMessage());
        }
    }

    @Test
    public void checkValidScheduling_invalidTime_shouldFail() 
    {
        s1 = new Section("101", "Mon Tue Wed", "43:30", "12:00", "COMM", "202");
        s2 = new Section("102", "Thu Fri", "10:86", "12:00", "CPSC", "202");
        s3 = new Section("103", "Mon Tue Wed", "13:30", "15:00", "ANTH", "202");
        schedule.add(s1);
        schedule.add(s2);
        schedule.add(s3);

        try 
        {
            assertFalse(schedulingService.checkValidTimeScheduling(schedule));
            fail();
        }
        catch (Exception e)
        {
            log.error("Successfully threw an error " + e.getMessage());
        }
    }
}
