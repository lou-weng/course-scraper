package com.louweng.scheduler.services;

import java.util.List;

import com.louweng.scheduler.models.Section;
import com.louweng.scheduler.repositories.SectionRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SchedulingService {

    @Autowired
    SectionRepository sectionRepository;

    public List<List<Section>> schedule(List<Section> sections) {
        return null;
    }

}
