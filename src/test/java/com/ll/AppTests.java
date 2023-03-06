package com.ll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTests {
    // 유틸 테스트
    @Test
    @DisplayName("스캐너에 키보드가 아닌 문자열을 입력으로 설정")
    public void t1() {
        Scanner sc = TestUtil.genScanner("안녕");

        String cmd = sc.nextLine().trim();
        assertThat(cmd).isEqualTo("안녕");
    }

    @Test
    @DisplayName("출력을 모니터에 하지 않고 문자열로 얻기")
    public void t2() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        System.out.print("안녕");
        String rs = output.toString();
        TestUtil.clearSetOutToByteArray(output);

        assertThat(rs).isEqualTo("안녕");
    }

    // 앱 테스트
    @Test
    @DisplayName("프로그램 시작시 타이틀 출력 그리고 종료")
    public void t3() {
        String rs = AppTestRunner.run("");

        assertThat(rs)
                .contains("== 명언 앱 ==")
                .contains("명령) ")
                .contains("프로그램이 종료되었습니다.")
                .doesNotContain("올바르지 않은 명령입니다.");
    }

    @Test
    @DisplayName("잘못된 명령어 입력에 대한 처리")
    public void t4() {
        String rs = AppTestRunner.run("종료2");

        assertThat(rs)
                .contains("올바르지 않은 명령입니다.");
    }

    @Test
    @DisplayName("등록화면에서 명언과 작가를 입력받고, 명언을 생성한다.")
    public void t5() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(rs)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("명언이 등록될 때 마다 생성되는 명언의 번호가 1씩 증가한다.")
    public void t6() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 마라.
                이순신
                등록
                왼손은 거들뿐
                강백호
                """);

        assertThat(rs)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.")
                .contains("3번 명언이 등록되었습니다.")
                .doesNotContain("4번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록을 최신 등록 순으로 출력한다.")
    public void t7() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 마라.
                이순신
                등록
                왼손은 거들뿐
                강백호
                목록
                """);

        assertThat(rs)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.")
                .contains("3번 명언이 등록되었습니다.")
                .doesNotContain("4번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("3 / 강백호 / 왼손은 거들뿐")
                .contains("2 / 이순신 / 나의 죽음을 적들에게 알리지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제?id=(정수)를 입력하면 해당 숫자의 id를 갖는 명언을 삭제한다.")
    public void t8() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 마라.
                이순신
                등록
                왼손은 거들뿐
                강백호
                목록
                삭제?id=1
                """);

        assertThat(rs)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.")
                .contains("3번 명언이 등록되었습니다.")
                .doesNotContain("4번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("3 / 강백호 / 왼손은 거들뿐")
                .contains("2 / 이순신 / 나의 죽음을 적들에게 알리지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("삭제?id=(정수)를 입력하고 해당 숫자의 id를 갖는 명언이 없을 때 예외처리")
    public void t9() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 마라.
                이순신
                등록
                왼손은 거들뿐
                강백호
                목록
                삭제?id=1
                삭제?id=1
                """);

        assertThat(rs)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.")
                .contains("3번 명언이 등록되었습니다.")
                .doesNotContain("4번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("3 / 강백호 / 왼손은 거들뿐")
                .contains("2 / 이순신 / 나의 죽음을 적들에게 알리지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언이 존재하지 않습니다.");
    }
}
