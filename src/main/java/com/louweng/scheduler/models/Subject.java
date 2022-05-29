package com.louweng.scheduler.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="subjects")
@Table(name="subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject 
{
    @Id
    @Column(name="subject_id")
    private String subjectId;
    @Column(name="subject_title")
    private String subjectTitle;
    private String faculty;
}
