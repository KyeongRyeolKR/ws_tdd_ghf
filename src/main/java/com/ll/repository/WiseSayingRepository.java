package com.ll.repository;

import com.ll.entity.WiseSaying;
import com.ll.service.WiseSayingService;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings;
    private long lastWiseSayingId;

    public WiseSayingRepository() {
        this.wiseSayings = new ArrayList<>();
        this.lastWiseSayingId = 0;
    }

    public List<WiseSaying> findALl() {
        return wiseSayings;
    }

    public WiseSaying findById(long id) {
        for(WiseSaying wiseSaying : wiseSayings) {
            if(wiseSaying.getId() == id) {
                return wiseSaying;
            }
        }
        return null;
    }

    public long write(String content, String authorName) {
        long id = ++lastWiseSayingId;

        wiseSayings.add(new WiseSaying(id, content, authorName));

        lastWiseSayingId = id;

        return id;
    }

    public void remove(WiseSaying wiseSaying) {
        wiseSayings.remove(wiseSaying);
    }

    public void modify(WiseSaying wiseSaying, String content, String authorName) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthorName(authorName);
    }
}
