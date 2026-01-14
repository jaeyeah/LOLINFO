package com.lol.lolinfo.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorRestControllerAdvice {
	// catch 블럭처럼 사용할 수 있는 도구
		@ExceptionHandler(value= {TargetNotfoundException.class, NoResourceFoundException.class})
		public ResponseEntity<String> notFound(TargetNotfoundException e) {
			return ResponseEntity.notFound().build(); // 404
		}
		
//	    @ExceptionHandler(value= {TargetNotfoundException.class, NoResourceFoundException.class})
//	    public ResponseEntity<String> notFound(RuntimeException e) { 
//	        return ResponseEntity.notFound().build(); // 404
//	    }

		@ExceptionHandler(UnauthorizationException.class)
		public ResponseEntity<String> unauthorize(UnauthorizationException e) {
			return ResponseEntity.status(401).build(); // 401
		}
		
		@ExceptionHandler(NotEnoughHeartException.class)
		public ResponseEntity<String> handleNotEnoughHeart(NotEnoughHeartException e) {
			
		    return ResponseEntity.status(402).body(e.getMessage());
		}
		
		@ExceptionHandler(NeedPermissionException.class)
		public ResponseEntity<String> needPermission(NeedPermissionException e) {
			return ResponseEntity.status(403).build(); // 403
		}
		
		//나머지 모든 예외  <- 에러 500번대 : 해결해야함
		@ExceptionHandler(Exception.class)
		public ResponseEntity all(Exception e) {
			log.error("예외 발생",e);
			return ResponseEntity.internalServerError().build();
		}
}
