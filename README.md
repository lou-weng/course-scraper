# UBC Course Timetable Generator Project

## Purpose
At the start of every study semester, it can often be a daunting task selecting the right combination of courses given a wide plethora of options. Creating worklists/timetables of the 4-6 courses per semester can be a time consuming task, especially when done on the old and not user friendly course registration platform that UBC uses. I wanted to try my hand at creating a tool for the following reasons:

1. Saving time when registering for courses by having a generator output different timetable options rather than using an old UI and constantly using trial and error to generate timetables. 
2. The prospect of generating permutations of timetable options presented interesting design options, which will make for a thought-provoking project.
3. I want to learn how to use Spring, and a project with a straight forward data model seemed like a good one to start with.

## Project Overview
The end goal of the project includes the following:

### (initial scope)
1. A backend that will be able to handle:

| Input                                                                        | Behaviour                                                                  | Output                                        |
| ---------------------------------------------------------------------------- | -------------------------------------------------------------------------- | --------------------------------------------- |
| A subject code (CPSC)                                                        | Query database for subject information and return it to the user           | UBC Subject information                       |
| A subject code + course code (CPSC 330)                                      | Query database for course information and return it to the user            | UBC Course information                        |
| A subject code + course code + section code (CPSC 330 101)                   | Query database for section information and return it to the user           | UBC Section information                       |
| A list of courses that a person needs to take [CPSC 330, COMM 202, ANTH 201] | Generate lists of all course sections (timetables) that a student can take | List of list of sections (list of timetables) |

### (required data/utilities)
2. Data will be extracted from [UBC's Course Site](https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-all-departments). 

Since this information needs to be scraped from a website, a util tool to load the course info into a database is included with the project. 

To reduce the amount of queries made to the UBC site and to avoid DDoSing the school, the html pages at each REST endpoint containing subject/course/section information will be 'cached' in a folder and scrapes will be done on the folder files. 

Jsoup will be the library of choice to follow the java theme of the project. Introducing the library dependency into the project through Maven is trivial. 

### (expanded scope)
3. Include a front end that will be able to output a UI showcasing a timetable. The front-end will be created using thymeleaf to simplify deployment of the full-stack app.

## Learning Goals
- [x] Spring Boot: learning how to work within a loose MVC framework
- [x] Using Spring abstractions to operate on the database persistence layer (Spring-JPA)
- [x] Leverageing annotation libraries like Lombok to simplify verbose Java code
- [x] Logging frequently (I'm guilty of always using System.out.println() for debugging)
- [ ] Being security minded and securely configuring database password and other confidential information (Will try to leverage AWS SSM)

### Future Goals
- [ ] deploying the app through a CI/CD pipeline
- [ ] dockerizing the app for easy deployment
- [ ] making the course website scraper application into its own service that schedules scrapes throughout the year (to make sure course information is updated at the start of each semester since it changes tri-annually)

## Data Model
### Database Schema
Due to the structured nature of UBC's course information, a relational database schema made sense. One subject can have many courses and one course may have multiple sections. Students needs all three pieces of information when registering. 

```
CREATE TABLE subjects (
    subject_id TEXT PRIMARY KEY,
    subject_title TEXT NOT NULL,
    faculty TEXT NOT NULL
);

CREATE TABLE courses (
    course_id TEXT NOT NULL,
    course_title TEXT NOT NULL, 
    subject_id TEXT REFERENCES subjects(subject_id),
    constraint pk_courses PRIMARY KEY(subject_id, course_id)
);

CREATE TABLE sections (
    section_id TEXT NOT NULL, 
    activity TEXT NOT NULL, 
    term INT NOT NULL, 
    mode_of_delivery TEXT NOT NULL, 
    days TEXT NOT NULL, 
    start_time TEXT NOT NULL, 
    end_time TEXT NOT NULL,
    subject_id TEXT NOT NULL,
    course_id TEXT NOT NULL,
    constraint fk_sections FOREIGN KEY (subject_id, course_id) REFERENCES courses(subject_id, course_id),
    constraint pk_sections PRIMARY KEY (subject_id, course_id, section_id)
);
```

## Why use RDS and complain about AWS charging you money?
Since I'm using AWS, a good question is why not use the 25GB of always free DynamoDB instead of a relational postgres database? The data model of sections means that its primary key is composed of three different fields. DynamoDB only allows the use of two keys (partition and sort key) which means that two of the fields would have to be consolidated in order for DynamoDB tables to be created. Since scraping all subjects, then courses, then sections requires three distinct urls and scrapes, it was easier to just dump the information into a relational model than to manage concatenating keys for Dynamo while scraping. 