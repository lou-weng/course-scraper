package com.louweng.scheduler.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="sections")
@Table(name="sections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section 
{
    @Id
    @Column(name="section_id")
    String sectionId;
    String activity;
    int term;
    @Column(name="mode_of_delivery")
    String modeOfDelivery;
    String days;
    @Column(name="start_time")
    String startTime;
    @Column(name="end_time")
    String endTime;
    @Column(name="subject_id")
    String subjectId;
    @Column(name="course_id")
    String courseId;
}
