package com.ll.repository;

import com.ll.entity.WiseSaying;
import com.ll.service.WiseSayingService;
import com.ll.table.WiseSayingTable;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private final WiseSayingTable wiseSayingTable;

    public WiseSayingRepository() {
        wiseSayingTable = new WiseSayingTable();
    }

    public List<WiseSaying> findALl() {
        return wiseSayingTable.findAll();
    }

    public WiseSaying findById(long id) {
        return wiseSayingTable.findById(id);
    }

    public long write(String content, String authorName) {
        long id = wiseSayingTable.getLastId() + 1;

        WiseSaying wiseSaying = new WiseSaying(id, content, authorName);

        return wiseSayingTable.save(wiseSaying);
    }

    public void remove(WiseSaying wiseSaying) {
        wiseSayingTable.remove(wiseSaying);
    }

    public void modify(WiseSaying wiseSaying, String content, String authorName) {
        wiseSayingTable.modify(wiseSaying, content, authorName);
    }
}
