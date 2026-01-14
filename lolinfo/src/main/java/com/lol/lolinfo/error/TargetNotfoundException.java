package com.lol.lolinfo.error;

// 사용처 : 대상이 없어서 더이상 진행할 수 없는 경우 사용하는 커스텀 예외
public class TargetNotfoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public TargetNotfoundException() {
		super();
	}

	public TargetNotfoundException(String message) {
		super(message);
	}
	
}
