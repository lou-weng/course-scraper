package com.louweng.scheduler.utilTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import com.louweng.scheduler.models.Subject;
import com.louweng.utils.CourseScraper;

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
        this.filePath += "subjectsExistsSucess.html";
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
    public void scrapeSubject_ifMainTableNotFound_returnEmpty()
    {
        filePath += "subjectsExistNoMainTable.html";
        testCaseFile = new File(filePath);

        try 
        {
            scraper.connectFile(testCaseFile);
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
}
