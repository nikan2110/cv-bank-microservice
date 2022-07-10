package telran.cvbank.dto.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class CVNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 5635370307230854544L;


}
