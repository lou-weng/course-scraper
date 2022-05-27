package com.louweng.scheduler.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.louweng.scheduler.models.Section;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SectionRepository 
{
    @PersistenceContext
    EntityManager entityManager;

    public List<Section> getAllSubjects()
    {
        return entityManager.createQuery("select s from sections s", Section.class).getResultList();
    }

    public Section insertSection(Section section)
    {
        return entityManager.merge(section);
    }
}