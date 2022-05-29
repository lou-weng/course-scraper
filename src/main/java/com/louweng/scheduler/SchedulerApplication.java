package com.louweng.scheduler;

import java.io.File;
import java.util.List;

import com.louweng.scheduler.models.Course;
import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.models.Subject;
import com.louweng.scheduler.repositories.CourseRepository;
import com.louweng.scheduler.repositories.SectionRepository;
import com.louweng.scheduler.repositories.SubjectRepository;
import com.louweng.scrapeTools.CourseScraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SchedulerApplication implements CommandLineRunner 
{
	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SectionRepository sectionRepository;

	public static void main(String[] args) 
	{
		SpringApplication.run(SchedulerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{
		CourseScraper scraper = new CourseScraper();
		try 
		{
			scraper.connectDocument("https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-all-departments");
			List<Subject> result = scraper.scrapeSubjectHTML();
			for (Subject s : result)
			{
				subjectRepository.insertSubject(s);
			}

			File courseDir = new File("src/main/resources/scrapedFiles/courses/");
			File[] listFiles = courseDir.listFiles();

			for (File f : listFiles) 
			{
				scraper.connectFile(f);

				List<Course> courses = scraper.scrapeCourseHTML();
				for (Course c : courses)
				{
					courseRepository.insertCourse(c);
				}
			}

			File dir = new File("src/main/resources/scrapedFiles/sections/");
			listFiles = dir.listFiles();

			for (File f : listFiles) 
			{
				scraper.connectFile(f);

				List<Section> sections = scraper.scrapeSectionHTML();
				for (Section s : sections)
				{
					sectionRepository.insertSection(s);
				}
			}
		}
		catch(Exception e)
		{
			log.error("ERROR: " + e.getMessage() + " " + e.getStackTrace()[0]);
		}
	}
}
