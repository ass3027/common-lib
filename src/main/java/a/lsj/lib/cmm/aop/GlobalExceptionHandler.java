package a.lsj.lib.cmm.aop;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import a.lsj.lib.cmm.enums.ErrorCode;
import a.lsj.lib.cmm.exception.GlobalException;
import a.lsj.lib.cmm.response.ErrorResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Exception 을 Catch 하여 응답값(Response)을 보내주는 기능을 수행함.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/* ************************************************************************
	 * 4xx
	 ************************************************************************ */
	/** 400
	 * [Exception] API 호출 시 '객체' 혹은 '파라미터' 데이터 값이 유효하지 않은 경우
	 *
	 * @param ex MethodArgumentNotValidException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponseBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		System.out.println("[Exception handler : 400-1] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An MethodArgumentNotValidException occurred", ex);
		BindingResult bindingResult = ex.getBindingResult();
		StringBuilder stringBuilder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			stringBuilder.append(fieldError.getField()).append(":");
			stringBuilder.append(fieldError.getDefaultMessage());
			stringBuilder.append(", ");
		}

		return new ResponseEntity<>( new ErrorResponseBody( ErrorCode.S400_INVALID_VALUE, String.valueOf(stringBuilder) ), HttpStatus.BAD_REQUEST);
	}


	/** 400
	 * [Exception] API 호출 시 'Header' 내에 데이터 값이 유효하지 않은 경우
	 *
	 * @param ex MissingRequestHeaderException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(MissingRequestHeaderException.class)
	protected ResponseEntity<ErrorResponseBody> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
		System.out.println("[Exception handler : 400-2] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An MissingRequestHeaderException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S400_MISSING_MANDATORY_ATTRIBUTE ), HttpStatus.BAD_REQUEST);
	}


	/** 400
	 * [Exception] 클라이언트에서 Body 로 '객체' 데이터가 넘어오지 않았을 경우
	 *
	 * @param ex HttpMessageNotReadableException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponseBody> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		System.out.println("[Exception handler : 400-3] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An HttpMessageNotReadableException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S400_MISSING_MANDATORY_ATTRIBUTE ), HttpStatus.BAD_REQUEST);
	}


	/** 400
	 * [Exception] 클라이언트에서 request 로 '파라미터로' 데이터가 넘어오지 않았을 경우
	 *
	 * @param ex MissingServletRequestParameterException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponseBody> handleMissingRequestHeaderExceptionException(MissingServletRequestParameterException ex) {
		System.out.println("[Exception handler : 400-4] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An MissingServletRequestParameterException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S400_MISSING_MANDATORY_ATTRIBUTE ), HttpStatus.BAD_REQUEST);
	}


	/** 400
	 * [Exception] 잘못된 서버 요청일 경우 발생한 경우
	 *
	 * @param ex HttpClientErrorException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(HttpClientErrorException.BadRequest.class)
	protected ResponseEntity<ErrorResponseBody> handleBadRequestException(HttpClientErrorException ex) {
		System.out.println("[Exception handler : 400-5] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An HttpClientErrorException.BadRequest occurred.BadRequest", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S400_GENERAL ), HttpStatus.BAD_REQUEST);
	}


	/** 404
	 * [Exception] 잘못된 주소로 요청 한 경우
	 *
	 * @param ex NoHandlerFoundException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ErrorResponseBody> handleNoHandlerFoundExceptionException(NoHandlerFoundException ex) {
		System.out.println("[Exception handler : 404-1] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An NoHandlerFoundException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S404_GENERAL ), HttpStatus.BAD_REQUEST);
	}

	/** 404
	 * [Exception] 요청 파일을 찾을 수 없을 경우
	 *
	 * @param e FileNotFoundException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(FileNotFoundException.class)
	protected ResponseEntity<ErrorResponseBody> handleFileNotFoundExceptionException(FileNotFoundException e) {
		System.out.println("[Exception handler : 404-2] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An FileNotFoundException occurred", e);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S404_FILE_NOT_FOUND), HttpStatus.BAD_REQUEST);
	}

	/* ************************************************************************
	 * 5xx
	 ************************************************************************ */
	/** 500
	 * [Exception] NULL 값이 발생한 경우
	 *
	 * @param ex NullPointerException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ErrorResponseBody> handleNullPointerException(NullPointerException ex) {
		System.out.println("[Exception handler : 500-1] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An NullPointerException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S500_GENERAL, ex.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/** 500 : File I/O
	 * Input / Output 내에서 발생한 경우
	 *
	 * @param ex IOException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(IOException.class)
	protected ResponseEntity<ErrorResponseBody> handleIOException(IOException ex) {
		System.out.println("[Exception handler : 500-2] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An IOException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S500_GENERAL, ex.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	/** 500 : JSON
	 * com.google.gson 내에 Exception 발생하는 경우
	 *
	 * @param ex JsonParseException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(JsonParseException.class)
	protected ResponseEntity<ErrorResponseBody> handleJsonParseExceptionException(JsonParseException ex) {
		System.out.println("[Exception handler : 500-3] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An JsonParseException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S500_JSON_PARSE_ERROR, ex.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/** 500 : JSON
	 * com.fasterxml.jackson.core 내에 Exception 발생하는 경우
	 *
	 * @param ex JsonProcessingException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(JsonProcessingException.class)
	protected ResponseEntity<ErrorResponseBody> handleJsonProcessingException(JsonProcessingException ex) {
		System.out.println("[Exception handler : 500-4] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An JsonProcessingException occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S500_JSON_PROCESSING_ERROR, ex.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ------------------------------------------------------------------------
	// -- 정의되지 않은 모든 예외는 500으로 처리
	// ------------------------------------------------------------------------
	/** 500
	 * [Exception] 모든 Exception 경우 발생
	 *
	 * @param ex Exception
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(Exception.class)
	protected final ResponseEntity<ErrorResponseBody> handleAllExceptions(Exception ex) {
		System.out.println("[Exception handler : 500-5] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("An unknown Exception occurred", ex);

		return new ResponseEntity<>(new ErrorResponseBody( ErrorCode.S500_GENERAL, ex.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	// ===== [ Custom Exception ] ======================================================================================

	/**
	 * GlobalException 에서 발생한 에러
	 *
	 * @param ex GlobalException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponseBody> handleGlobalException(GlobalException ex) {
		System.out.println("[Exception handler : 999-1] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		log.error("An GlobalException occurred, message : {}", ex.getErrorCode().getErrorMsg());

		return new ResponseEntity<>(new ErrorResponseBody( ex.getErrorCode() ), HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}




}
