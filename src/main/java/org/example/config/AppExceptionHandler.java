package org.example.config;

import org.example.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e, WebRequest request){
        logger.error(e.toString());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> handlePropertyReferenceException(PropertyReferenceException e, WebRequest request){
        logger.error(e.toString());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, WebRequest request){
        logger.error(e.toString());
        return new ResponseEntity<>(e.getClass()+": "+e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request){
        logger.error(e.toString());
        return new ResponseEntity<>(e.getClass()+": "+e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException e, WebRequest request){
        logger.error(e.toString());
        return new ResponseEntity<>(e.getClass()+": "+e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleSQLException(AppException e, WebRequest request){
        logger.error(e.toString());
        return new ResponseEntity<>(e.getClass()+": "+e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
