package brave.btc.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import brave.btc.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class BindingAopHandler {

	@Around("execution(* brave.btc.controller..*.*(..))")
	public Object validationCheck(ProceedingJoinPoint pj) throws Throwable{

		String type = pj.getSignature().getDeclaringTypeName();
		String method= pj.getSignature().getName();
		log.debug("type: {},  method: {}",type,method);

		Object[] args = pj.getArgs();
		for (Object arg : args) {
			if(arg instanceof Errors){
				ResponseEntity<CommonResponseDto<?>> response= bindingError((BindingResult) arg);
				if(ObjectUtils.isNotEmpty(response)) return response;
			}
		}
		return pj.proceed();
	}

	public ResponseEntity<CommonResponseDto<?>> bindingError(BindingResult br ){
		if(br.hasErrors()){
			log.debug("유효성 검사 통과 실패");
			Map<String,String> errorMap = new HashMap<>();
			br.getFieldErrors().forEach(error->{
				errorMap.put(error.getField(),error.getDefaultMessage());
			});
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(CommonResponseDto.builder()
							.data(errorMap)
							.message("유효성 검사에 실패하였습니다.")
							.build());
		}
		return null;
	}
}
