package rs.mladost.transfer.api.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;
import rs.mladost.transfer.api.rest.dto.ResponseStatus;

import static rs.mladost.transfer.api.rest.dto.ResponseStatus.FAILED;

@AllArgsConstructor
@Setter
public class ErrorResponse implements Response {
    @JsonProperty
    private int errorCode;
    @JsonProperty
    private String errorDescription;

    @Override
    public ResponseStatus getStatus() {
        return FAILED;
    }
}
