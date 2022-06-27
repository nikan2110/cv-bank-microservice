package telran.cvbank.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeAlreadyExistException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2197271392473526853L;

}
