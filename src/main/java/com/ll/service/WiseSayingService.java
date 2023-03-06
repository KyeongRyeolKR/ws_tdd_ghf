package com.ll.service;

import com.ll.entity.WiseSaying;
import com.ll.repository.WiseSayingRepository;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findALl();
    }

    public WiseSaying findById(long id) {
        return wiseSayingRepository.findById(id);
    }

    public long write(String content, String authorName) {
        return wiseSayingRepository.write(content, authorName);
    }


    public void remove(WiseSaying wiseSaying) {
        wiseSayingRepository.remove(wiseSaying);
    }

    public void modify(WiseSaying wiseSaying, String content, String authorName) {
        wiseSayingRepository.modify(wiseSaying,content,authorName);
    }
}
