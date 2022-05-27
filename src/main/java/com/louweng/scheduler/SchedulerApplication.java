package com.louweng.scheduler;

import java.util.List;

import com.louweng.scheduler.models.Subject;
import com.louweng.scheduler.repositories.SubjectRepository;
import com.louweng.utils.CourseScraper;

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
			scraper.scrapeCoursePage(subjectRepository.getAllSubjects());
		}
		catch(Exception e)
		{
			log.error("ERROR: " + e.getMessage());
		}
	}
}
