package com.ll.service;

import com.ll.Util;
import com.ll.entity.WiseSaying;
import com.ll.repository.WiseSayingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
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

    public void build() {
        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll();

        Util.file.mkdir("prodBuild");

        String json = "[" + wiseSayings
                .stream()
                .map(wiseSaying -> wiseSaying.toJson())
                .collect(Collectors.joining(",\n")) + "]";

        Util.file.saveToFile("prodBuild/data.json", json);
    }
}
