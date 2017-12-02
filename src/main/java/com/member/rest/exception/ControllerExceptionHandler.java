package com.member.rest.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.member.rest.exception.UserNotFoundException;

/**
 * Handles Controller exceptions by providing the {@link} MessageDTO
 * corresponding to each ExceptionHandler
 * 
 * @author Imen Gharsalli
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	/**
	 * 
	 * @param ex
	 *            the MethodArgumentNotValidException
	 * @return the messageDTO corresponding to the
	 *         MethodArgumentNotValidException
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();
		return processFieldError(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param request
	 *            the Http Servlet Request
	 * @param ex
	 *            the HttpMessageNotReadableException
	 * @return the messageDTO corresponding to the
	 *         HttpMessageNotReadableException
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO handleMessageNotReadableException(HttpServletRequest request,
			HttpMessageNotReadableException ex) {
		return processFieldError(new ObjectError("Member", "typeMismatch.postForm"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @return the messageDTO corresponding to the MethodArgumentTypeMismatchException
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO handleMethodArgumentTypeMismatchException() {
		return processFieldError(new FieldError("Member", "id", "typeMismatch.member.id"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @return the messageDTO corresponding to the NoHandlerFoundException
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public MessageDTO handleNotFoundError() {
		return processFieldError(new ObjectError("Member", "member.resource.notFound"), HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param ex the UserNotFoundException
	 * @return the messageDTO corresponding to the UserNotFoundException
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public MessageDTO handleUserNotFoundError(UserNotFoundException ex) {
		return new MessageDTO(MessageType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param ex
	 * @return the messageDTO corresponding to the MemberNotFoundException
	 */
	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public MessageDTO handleMemberNotFoundError(MemberNotFoundException ex) {
		return new MessageDTO(MessageType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/***
	 * 
	 * @param ex
	 * @return the messageDTO corresponding to the ConstraintViolationException
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageDTO handleConstraintViolationException(ConstraintViolationException ex) {
		List<String> msg = new ArrayList<>();
		List<ObjectError> violations = new ArrayList<>();
		ex.getConstraintViolations().forEach(entry -> msg.add(entry.getMessage()));
		msg.forEach(violation -> violations.add(new ObjectError("Member", violation)));
		return processFieldsErrors(HttpStatus.INTERNAL_SERVER_ERROR, violations);
	}

	/**
	 * 
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidMemberException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageDTO handleInvalidMemberException(InvalidMemberException ex) {
		MessageDTO m = new MessageDTO(MessageType.ERROR, ex.getMessage(), HttpStatus.NOT_FOUND);
		List<String> msg = new ArrayList<>();
		List<ObjectError> violations = new ArrayList<>();
		Throwable cause = ex.getCause();
		if ((Exception) cause instanceof ConstraintViolationException) {
			((ConstraintViolationException) cause).getConstraintViolations()
					.forEach(entry -> msg.add(entry.getMessage()));
			msg.forEach(violation -> violations.add(new ObjectError("Member", violation)));
			m = processFieldsErrors(HttpStatus.INTERNAL_SERVER_ERROR, violations);
		}
		return m;

	}

	/**
	 * 
	 * @param objectError 
	 * @param httpStatus the correspondign HTTPStatus
	 * @return the messageDTO corresponding to the given objectError
	 */
	private MessageDTO processFieldError(ObjectError objectError, HttpStatus httpStatus) {
		MessageDTO messageDTO = null;
		if (objectError != null) {
			Locale locale = LocaleContextHolder.getLocale();
			String message = messageSource.getMessage(objectError.getDefaultMessage(), null, locale);
			messageDTO = new MessageDTO(MessageType.ERROR, message, httpStatus);
		}
		return messageDTO;
	}

	/**
	 * 
	 * @param httpStatus
	 * @param objectErrors
	 * @return
	 */
	private MessageDTO processFieldsErrors(HttpStatus httpStatus, List<ObjectError> objectErrors) {
		String msg = "";
		for (ObjectError objectError : objectErrors) {
			msg = String.format(msg + " *** " + processFieldError(objectError, httpStatus).getMessage());
		}
		return new MessageDTO(MessageType.ERROR, msg, httpStatus);
	}
}