package topjava.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.exception.ErrorInfo;
import topjava.graduation.exception.NotChangedException;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@ResponseBody
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private static final Map<String, String> BASE_EXCEPTION = Collections.unmodifiableMap(
            new HashMap<String, String>() {
                {
                    put("users_unique_email_idx", "User with this email already exists");
                    put("restaurants_unique_name_idx", "Restaraunt with this name already exists");
                    put("menus_unique_restaurant_date_idx", "Menu with this date for this restaurant already exists");
                }
            });

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = BASE_EXCEPTION.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
                return logAndGetErrorInfo(req, e, false, entry.get().getValue());
            }
        }
        return logAndGetErrorInfo(req, e, true, null);
    }


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String details = result.getFieldErrors().stream()
                         .map(fld->fld.getField()+' '+fld.getDefaultMessage())
                         .collect(Collectors.joining(System.lineSeparator()));
        return logAndGetErrorInfo(req, e, false, details);
    }


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({NotChangedException.class, IllegalArgumentException.class,
            HttpMessageNotReadableException.class, NotFoundException.class})
    public ErrorInfo validationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, null);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, null);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, String message) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error("at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("at request  {}: {}", req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), message==null?rootCause.getLocalizedMessage():message);
    }
}