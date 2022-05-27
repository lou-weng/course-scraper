## Data Model

Data will be extracted from [UBC's Course Site](https://courses.students.ubc.ca/cs/courseschedule?pname=subjarea&tname=subj-all-departments). Since this information needs to be scraped from a website, a util tool to load the course info into a database is included with the project. 

### Database Schema
Due to the structured nature of UBC's course information, a relational database schema made sense. There are one to many relationships between subjects and courses, courses and sections. 

```
CREATE TABLE subjects (
    subject_id VARCHAR(10) PRIMARY KEY,
    subject_title VARCHAR(100) NOT NULL,
    faculty VARCHAR(100) NOT NULL
);

CREATE TABLE courses (
    course_id VARCHAR(50) PRIMARY KEY,
    course_title VARCHAR(50) NOT NULL, 
    subject_id VARCHAR(10) REFERENCES subjects(subject_id)
);

CREATE TABLE sections (
    section_id VARCHAR(50) PRIMARY KEY, 
    activity VARCHAR(50) NOT NULL, 
    term INT NOT NULL, 
    mode_of_delivery VARCHAR(50) NOT NULL, 
    days VARCHAR(50) NOT NULL, 
    start_time VARCHAR(10) NOT NULL, 
    end_time VARCHAR(10) NOT NULL
    course_id VARCHAR(50) REFERENCES courses(course_id)
);
```