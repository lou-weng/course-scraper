package com.louweng.scheduler.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name="sections")
@Table(name="sections")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Section 
{
    @Id
    @Column(name="section_id")
    final String sectionId;
    String activity;
    int term;
    @Column(name="mode_of_delivery")
    String modeOfDelivery;
    final String days;
    @Column(name="start_time")
    final String startTime;
    @Column(name="end_time")
    final String endTime;
    @Column(name="subject_id")
    final String subjectId;
    @Column(name="course_id")
    final String courseId;

    @Override
    public int hashCode() 
    {
        return subjectId.hashCode() + courseId.hashCode() + sectionId.hashCode();
    }

    @Override
    public boolean equals(Object o) 
    {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Section)) {
            return false;
        }

        Section s = (Section) o;
        return s.hashCode() == this.hashCode();
    }
}
