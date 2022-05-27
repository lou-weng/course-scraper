package com.louweng.scheduler.repositories;

import java.util.List;

import com.louweng.scheduler.models.Subject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SubjectRepository 
{
    @PersistenceContext
    EntityManager entityManager;

    public List<Subject> getAllSubjects()
    {
        return entityManager.createQuery("select s from subjects s", Subject.class).getResultList();
    }

    public Subject insertSubject(Subject subject)
    {
        return entityManager.merge(subject);
    }
}
