package com.louweng.scrapeTools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.louweng.scheduler.models.Course;
import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.models.Subject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class CourseScraper
{
    private Document document;
    private ArrayList<String> results = new ArrayList<>();

    public void connectDocument(String url) throws IOException 
    {
        document = Jsoup.connect(url).get();
        log.info("Loaded document from " + url + " successfully");
    }

    public void connectFile(File file) throws IOException
    {
        document = Jsoup.parse(file, "UTF-8", "");
        log.info("Loaded document from File successfully");
    }

    public List<Subject> scrapeSubjectHTML() 
    {
        List<Subject> result = new ArrayList<>();

        try 
        {
            Elements tableBody = document.getElementsByTag("tbody");
            Elements tableRows = tableBody.first().getElementsByTag("tr");
            log.info("Received table rows");
            for (Element elem : tableRows) 
            {
                Elements tableColumn = elem.getElementsByTag("td");
                if (tableColumn.isEmpty()) 
                {
                    continue;
                }
                Element subjectNameCell = tableColumn.get(0);
                Elements subjectName = subjectNameCell.getElementsByTag("a");
                if (subjectName.isEmpty()) 
                {
                    continue;
                }
                Element subjectTitle = tableColumn.get(1);
                Element subjectFaculty = tableColumn.get(2);
                
                result.add(new Subject(subjectName.first().text(), subjectTitle.text(), subjectFaculty.text()));
            }
        } 
        catch (Exception e) 
        {
            log.error("ERROR Result of scrape: " + e.getMessage());
        }
        return result;
    }

    public List<Course> scrapeCourseHTML() 
    {
        List<Course> result = new ArrayList<>();

        try
        {
            Elements tableBody = document.getElementsByTag("tbody");
            log.info(document.html());
            Elements tableRows = tableBody.first().getElementsByTag("tr");
            log.info("Received table rows");
            for (Element elem : tableRows) 
            {
                Elements tableColumn = elem.getElementsByTag("td");
                if (tableColumn.isEmpty()) 
                {
                    continue;
                }
                Element courseIdCell = tableColumn.get(0);
                Elements courseId = courseIdCell.getElementsByTag("a");
                if (courseId.isEmpty()) 
                {
                    continue;
                }
                Element courseTitle = tableColumn.get(1);
                String[] courseInfo = courseId.text().split(" ");

                result.add(new Course(courseInfo[1], courseTitle.text(), courseInfo[0]));
            }
        }
        catch (Exception e)
        {
            log.error("ERROR: " + e.getStackTrace()[0]);
        }
        return result;
    }

    public List<Section> scrapeSectionHTML() 
    {
        List<Section> result = new ArrayList<>();

        try
        {
            Elements tableBody = document.getElementsByTag("tbody");
            Elements tableRows = tableBody.first().getElementsByTag("tr");
            log.info("Received table rows " + tableBody.html());
            for (Element elem : tableRows) 
            {
                Elements tableColumn = elem.getElementsByTag("td");
                if (tableColumn.isEmpty()) 
                {
                    continue;
                }
                log.info(tableColumn.html());
                Element sectionIdCell = tableColumn.get(1);
                Elements sectionId = sectionIdCell.getElementsByTag("a");
                if (sectionId.isEmpty()) 
                {
                    continue;
                }
                Element sectionActivity = tableColumn.get(2);
                Element sectionTerm = tableColumn.get(3);
                Element sectionMode = tableColumn.get(4);
                Element sectionDays = tableColumn.get(6);
                Element sectionStartTime = tableColumn.get(7);
                Element sectionEndTime = tableColumn.get(8);
                String[] sectionInfo = sectionId.text().split(" ");

                Section section = new Section(
                    sectionInfo[2],
                    sectionActivity.text(),
                    Integer.parseInt(sectionTerm.text()),
                    sectionMode.text(),
                    sectionDays.text(),
                    sectionStartTime.text(),
                    sectionEndTime.text(),
                    sectionInfo[0],
                    sectionInfo[1]
                );
                result.add(section);
            }
        }
        catch (Exception e)
        {
            log.error("ERROR: " + e.getMessage() + e.getStackTrace()[0]);
        }
        return result;
    }

    // Got this from stack overflow to sleep between API calls 
    // https://stackoverflow.com/a/57567069
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public void scrapeSubjectPage() {
        String path = "./src/main/resources/scrapedFiles/subjects/";
        String url = "https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-all-departments";
        BufferedWriter writer;

        try 
        {
            connectDocument(url);

            log.info("Writing into subjects.html");
            writer = new BufferedWriter(new FileWriter(path + "subjects.html"));
            writer.write(document.html());
            writer.close();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }    
    }

    public void scrapeCoursePage(List<Subject> subjects) {
        String path = "./src/main/resources/scrapedFiles/courses/";
        String url = "https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-department&dept=";
        BufferedWriter writer;

        try 
        {
            for (Subject s : subjects) {
                connectDocument(url + s.getSubjectId());

                log.info("Writing into " + path + s.getSubjectId() + ".html");
                writer = new BufferedWriter(new FileWriter(path + s.getSubjectId() + ".html"));
                writer.write(document.html());
                writer.close();
                log.info("Waiting 60 seconds before next request");
                wait(60000);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }    
    }

    public void scrapeSectionPage(List<Course> courses) {
        String path = "./src/main/resources/scrapedFiles/sections/";
        BufferedWriter writer;

        try 
        {
            for (Course s : courses) {
                String url = "https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-course&dept=" + s.getSubjectId() + "&course=" + s.getCourseId();
                connectDocument(url + s.getSubjectId());

                log.info("Writing into " + path + s.getSubjectId() + " " + s.getCourseId() + ".html");
                writer = new BufferedWriter(new FileWriter(path + s.getSubjectId() + " " + s.getCourseId() + ".html"));
                writer.write(document.html());
                writer.close();
                log.info("Waiting 30 seconds before next request");
                wait(30000);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }    
    }
}
