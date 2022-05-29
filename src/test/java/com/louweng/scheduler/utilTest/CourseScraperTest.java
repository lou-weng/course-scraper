package com.louweng.scheduler.utilTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import com.louweng.scheduler.models.Course;
import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.models.Subject;
import com.louweng.scrapeTools.CourseScraper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseScraperTest 
{

    CourseScraper scraper = new CourseScraper();
    String filePath;
    File testCaseFile;

    @BeforeEach
    public void setupTests() 
    {
        filePath = "./src/test/testCases/";
    }

    @Test
    public void scrapeSubject_ifFound_willSucceed()
    {
        this.filePath += "subjectsExistsSuccess.html";
        testCaseFile = new File(filePath);
        
        try 
        {
            scraper.connectFile(testCaseFile);
            List<Subject> result = scraper.scrapeSubjectHTML();
            assertEquals(4, result.size());
        } 
        catch (Exception e) 
        {
            fail();
        }
    }

    @Test
    public void scrapeSubject_ifElementBodyNotFound_returnEmpty()
    {
        filePath += "subjectsExistNoBodyFound.html";
        testCaseFile = new File(filePath);

        try 
        {
            scraper.connectFile(testCaseFile);
            log.info(scraper.getDocument().html());
            List<Subject> result = scraper.scrapeSubjectHTML();
            log.info(result.toString());
            assertEquals(0, result.size());
        } 
        catch (Exception e) 
        {
            fail();
        }
    }

    @Test
    public void scrapeSubject_ifNoSubjects_returnEmpty()
    {
        filePath += "subjectsNoneFoundReturnEmpty.html";
        testCaseFile = new File(filePath);

        try 
        {
            scraper.connectFile(testCaseFile);
            List<Subject> result = scraper.scrapeSubjectHTML();
            assertEquals(0, result.size());
        }
        catch (Exception e) 
        {
            fail();
        }
    }   

    @Test
    public void scrapeCourse_ifFound_willSucceed()
    {
        this.filePath += "coursesExistsSuccess.html";
        testCaseFile = new File(filePath);
        
        try 
        {
            scraper.connectFile(testCaseFile);
            log.info(scraper.getDocument().html());
            List<Course> result = scraper.scrapeCourseHTML();
            log.info(result.toString());
            assertEquals(3, result.size());
        } 
        catch (Exception e) 
        {
            fail();
        }
    }

    @Test
    public void scrapeCourse_ifElementBodyNotFound_returnEmpty()
    {
        filePath += "coursesExistsNoBodyFound.html";
        testCaseFile = new File(filePath);

        try 
        {
            scraper.connectFile(testCaseFile);
            log.info(scraper.getDocument().html());
            List<Course> result = scraper.scrapeCourseHTML();
            log.info(result.toString());
            assertEquals(0, result.size());
        } 
        catch (Exception e) 
        {
            fail();
        }
    }

    @Test
    public void scrapeCourse_ifNoCourses_returnEmpty()
    {
        filePath += "coursesNoneFoundReturnEmpty.html";
        testCaseFile = new File(filePath);

        try 
        {
            scraper.connectFile(testCaseFile);
            List<Course> result = scraper.scrapeCourseHTML();
            assertEquals(0, result.size());
        }
        catch (Exception e) 
        {
            fail();
        }
    }   

    @Test
    public void scrapeSection_ifFound_willSucceed()
    {
        this.filePath += "sectionExistsSuccess.html";
        testCaseFile = new File(filePath);
        
        try 
        {
            scraper.connectFile(testCaseFile);
            List<Section> result = scraper.scrapeSectionHTML();
            assertEquals(2, result.size());
        } 
        catch (Exception e) 
        {
            fail();
        }
    }
}
