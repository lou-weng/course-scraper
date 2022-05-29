## Data Model

Data will be extracted from [UBC's Course Site](https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-all-departments). Since this information needs to be scraped from a website, a util tool to load the course info into a database is included with the project. 

### Database Schema
Due to the structured nature of UBC's course information, a relational database schema made sense. There are one to many relationships between subjects and courses, courses and sections. 

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