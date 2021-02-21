package com.alijaver.libraryapp.services;

import com.alijaver.libraryapp.daos.PublisherDao;
import com.alijaver.libraryapp.models.Publisher;
import com.alijaver.libraryapp.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublisherService implements PublisherDao {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    @Override
    public List<Publisher> getAll(Sort sort) {
        return publisherRepository.findAll(sort);
    }

    @Override
    public Publisher get(Long id) {

        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        Publisher publisher;
        if (optionalPublisher.isPresent()) {
            publisher = optionalPublisher.get();
        } else {
            throw new RuntimeException("Publisher not found by id :: " + id);
        }
        return publisher;
    }

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public void delete(Publisher publisher) {
        publisherRepository.delete(publisher);
    }

    @Override
    public List<Publisher> search(String keyword) {
        List<Publisher> publishers;
        if (keyword != null && !keyword.isEmpty()) {
            publishers = publisherRepository.findByNameContainingIgnoreCaseOrderByNameAsc(keyword);
        } else {
            publishers = publisherRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        }
        return publishers;
    }
}
