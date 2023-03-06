package com.ll.table;

import com.ll.Util;
import com.ll.entity.WiseSaying;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WiseSayingTable {
    private final List<WiseSaying> wiseSayings;
    private long lastWiseSayingId;

    public WiseSayingTable() {
        this.wiseSayings = new ArrayList<>();
        this.lastWiseSayingId = 0;
    }

    // 테이블의 데이터가 저장될 폴더 경로
    private static String getTableDirPath() {
        return "prodData/wiseSaying";
    }

    // 특정 데이터의 파일경로
    public static String getTableDataFilePath(long id) {
        return getTableDirPath() + "/" + id + ".json";
    }

    // 테이블에서 가장 마지막에 생성된 데이터의 ID를 저정한 파일의 경로
    public static String getTableLastIdFilePath() {
        return getTableDirPath() + "/last_id.txt";
    }

    // 테이블에서 가장 마지막에 생성된 데이터의 ID
    public long getLastId() {
        return Util.file.readNoFromFile(getTableLastIdFilePath(), 0);
    }

    // // 테이블에서 가장 마지막에 생성된 데이터의 ID 갱신
    private void saveLastId(long id) {
        Util.file.saveNoToFile(getTableLastIdFilePath(), id);
    }

    private List<Long> getFileIds() {
        String path = getTableDirPath();
        List<String> fileNames = Util.file.getFileNamesFromDir(path);

        return fileNames
                .stream()
                .filter(fileName -> !fileName.equals("last_id.txt"))
                .filter(fileName -> !fileName.equals("data.json"))
                .filter(fileName -> fileName.endsWith(".json"))
                .map(fileName -> fileName.replace(".json", ""))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<WiseSaying> findAll() {
        List<Long> fileIds = getFileIds();

        return fileIds.stream()
                .map(id -> findById(id))
                .collect(Collectors.toList());
    }

    public WiseSaying findById(long id) {
        String path = getTableDataFilePath(id);

        if (new File(path).exists() == false) {
            return null;
        }

        Map<String, Object> map = Util.json.jsonToMapFromFile(path);

        if (map == null) {
            return null;
        }

        return new WiseSaying((long) map.get("id"), (String) map.get("content"), (String) map.get("authorName"));
    }

    public long save(WiseSaying wiseSaying) {
        // 폴더 생성
        Util.file.mkdir(getTableDirPath());

        // 객체의 내용을 JSON 문장으로 변환
        String body = wiseSaying.toJson();

        // 파일이 저장할 경로 생성
        String filePath = getTableDataFilePath(wiseSaying.getId());

        // 파일 생성 or 갱신
        Util.file.saveToFile(filePath, body);

        saveLastId(wiseSaying.getId());

        return wiseSaying.getId();
    }

    public void remove(WiseSaying wiseSaying) {
        String path = getTableDataFilePath(wiseSaying.getId());
        Util.file.deleteFile(path);
    }

    public void modify(WiseSaying wiseSaying, String content, String authorName) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthorName(authorName);

        String body = wiseSaying.toJson();

        String filePath = getTableDataFilePath(wiseSaying.getId());
        Util.file.saveToFile(filePath, body);
    }
}
