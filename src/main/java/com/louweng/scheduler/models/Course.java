package com.louweng.scheduler.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="courses")
@Data
@Table(name="courses")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column(name="course_id")
    String courseId;
    @Column(name="course_title")
    String courseTitle;
    @Column(name="subject_id")
    String subjectId;
}
