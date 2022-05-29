package com.louweng.scrapeTools;

import java.io.File;
import java.util.List;

import com.louweng.scheduler.models.Course;
import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.models.Subject;
import com.louweng.scheduler.repositories.CourseRepository;
import com.louweng.scheduler.repositories.SectionRepository;
import com.louweng.scheduler.repositories.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class CourseDBLoad {

    @Autowired
	SubjectRepository subjectRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SectionRepository sectionRepository;

    public void run() {
        CourseScraper scraper = new CourseScraper();
		try 
		{
			scraper.connectDocument("https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-all-departments");
			List<Subject> result = scraper.scrapeSubjectHTML();
			for (Subject s : result)
			{
				subjectRepository.insertSubject(s);
			}

            scraper.scrapeCoursePage(subjectRepository.getAllSubjects());
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

            scraper.scrapeSectionPage(courseRepository.getAllCourses());
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
