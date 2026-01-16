package com.lol.lolinfo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PageVO {

	private int page = 1;
	private int size = 10;
	private int totalCount;
	private int blockSize = 10;

	public int getBegin() {
		return page * size - (size-1);
	}
	public int getEnd() {
		return page * size;
	}
	
	public int getTotalPage() {
        return (int) Math.ceil((double) totalCount / size);
    }
	
	public String keyword;
	
	
	// 그룹의 시작~끝번호
	public int getBlockStart() {
		return (page - 1) / blockSize * blockSize + 1;
	}
	public int getBlockFinish() {
		int number = (page - 1) / blockSize * blockSize + blockSize;
		return Math.min(getTotalPage(), number);
	}

}
