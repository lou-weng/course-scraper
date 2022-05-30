package com.louweng.scheduler;

import com.louweng.scheduler.repositories.CourseRepository;
import com.louweng.scheduler.repositories.SectionRepository;
import com.louweng.scheduler.repositories.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SchedulerApplication 
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
}
