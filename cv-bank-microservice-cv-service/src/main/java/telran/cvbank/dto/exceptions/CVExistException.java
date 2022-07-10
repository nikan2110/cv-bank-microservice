package telran.cvbank.dto.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
@NoArgsConstructor
public class CVExistException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -6878819084308767014L;

}
