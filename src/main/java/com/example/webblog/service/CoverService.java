package com.example.webblog.service;


import com.example.webblog.Repository.CoverRepository;
import com.example.webblog.model.Cover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CoverService {

    @Autowired
    private CoverRepository coverRepository;

    public Cover save(Cover cover) {
        return coverRepository.save(cover);
    }
}