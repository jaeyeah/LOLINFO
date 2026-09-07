package com.lol.lolinfo.vo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** CK 경기 날짜 기준 조회 조건. 날짜가 모두 없으면 전체 기간을 조회한다. */
public class CkPeriodVO extends PageVO {
    public static class InvalidPeriodException extends IllegalArgumentException {
        private static final long serialVersionUID = 1L;
        public InvalidPeriodException(String message) { super(message); }
    }

    private final int streamerNo;
    private final String startDate;
    private final String endDate;

    public CkPeriodVO(int streamerNo, int page, String startDate, String endDate) {
        if (streamerNo < 1 || page < 1) {
            throw new InvalidPeriodException("스트리머 번호와 페이지는 1 이상이어야 합니다.");
        }
        if ((startDate == null) != (endDate == null)) {
            throw new InvalidPeriodException("시작일과 종료일을 함께 입력해주세요.");
        }
        if (startDate != null) {
            try {
                if (!startDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
                        || !endDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                    throw new InvalidPeriodException("날짜는 YYYY-MM-DD 형식이어야 합니다.");
                }
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                if (start.getYear() < 1 || end.getYear() < 1
                        || end.equals(LocalDate.of(9999, 12, 31)) || start.isAfter(end)) {
                    throw new InvalidPeriodException("조회 가능한 날짜 범위와 시작일·종료일 순서를 확인해주세요.");
                }
            } catch (DateTimeParseException e) {
                throw new InvalidPeriodException("유효한 날짜를 입력해주세요.");
            }
        }
        this.streamerNo = streamerNo;
        this.startDate = startDate;
        this.endDate = endDate;
        setPage(page);
    }

    public int getStreamerNo() { return streamerNo; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
}
