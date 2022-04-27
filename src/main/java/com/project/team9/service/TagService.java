package com.project.team9.service;

import com.project.team9.model.Tag;
import com.project.team9.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Tag getById(String id) {
        return tagRepository.getById(Long.parseLong(id));
    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
