package com.louweng.scheduler.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.louweng.scheduler.models.Course;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseRepository 
{
    @PersistenceContext
    EntityManager entityManager;

    public List<Course> getAllSubjects()
    {
        return entityManager.createQuery("select c from courses c", Course.class).getResultList();
    }

    public Course insertCourse(Course course)
    {
        return entityManager.merge(course);
    }
}

