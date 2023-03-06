package com.ll.controller;

import com.ll.Rq;
import com.ll.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private Scanner sc;
    private List<WiseSaying> wiseSayings;
    private long lastWiseSayingId;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
        this.wiseSayings = new ArrayList<>();
    }

    public void write() {
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();
        System.out.print("작가 : ");
        String authorName = sc.nextLine().trim();

        long id = ++lastWiseSayingId;

        wiseSayings.add(new WiseSaying(id, content, authorName));

        lastWiseSayingId = id;

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    public void list() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i=wiseSayings.size()-1; i>=0; i--) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthorName(), wiseSaying.getContent());
        }
    }

    public void remove(Rq rq) {
        long id = rq.getLongParam("id", -1);

        if(id == -1) {
            System.out.println("id(정수)를 입력해주세요.");
            return;
        }

        for(WiseSaying wiseSaying : wiseSayings) {
            if(wiseSaying.getId() == id) {
                wiseSayings.remove(wiseSaying);
                System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
                return;
            }
        }
        System.out.printf("%d번 명언이 존재하지 않습니다.\n", id);
    }

    public void modify(Rq rq) {
        long id = rq.getLongParam("id", -1);

        if(id == -1) {
            System.out.println("id(정수)를 입력해주세요.");
            return;
        }

        for(WiseSaying wiseSaying : wiseSayings) {
            if(wiseSaying.getId() == id) {
                System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
                System.out.print("명언 : ");
                wiseSaying.setContent(sc.nextLine());

                System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthorName());
                System.out.print("작가 : ");
                wiseSaying.setAuthorName(sc.nextLine());

                System.out.printf("%d번 명언이 수정되었습니다.\n", id);
                return;
            }
        }
        System.out.printf("%d번 명언이 존재하지 않습니다.\n", id);
    }
}
