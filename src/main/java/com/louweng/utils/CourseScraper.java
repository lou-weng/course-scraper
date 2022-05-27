package com.louweng.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            Element mainTable = document.getElementById("mainTable");
            Elements tableBody = mainTable.getElementsByTag("tbody");
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

    public void scrapeCoursePage(List<Subject> subjects) {
        String path = "./src/main/resources/scrapedFiles/courses/";
        String url = "https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-department&dept=";
        BufferedWriter writer;

        try 
        {
            for (Subject s : subjects) {
                connectDocument(url + s.getSubjectId());
                Element mainTable = document.getElementById("mainTable");
                log.info(mainTable.html());
                writer = new BufferedWriter(new FileWriter(path + s.getSubjectId() + ".html"));
                writer.write(mainTable.html());
                writer.close();
                wait(20000);
                log.info("Waiting 20 seconds before next request");
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        } 
        
    }
}
