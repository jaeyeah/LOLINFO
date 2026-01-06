package com.lol.lolinfo.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PageResponseVO<T> {

	private List<T> list;
    private PageVO pageVO;
	
	
}
