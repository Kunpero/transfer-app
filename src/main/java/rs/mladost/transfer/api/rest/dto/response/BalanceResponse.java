package rs.mladost.transfer.api.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BalanceResponse extends SuccessfulResponse {
    @JsonProperty
    private long id;
    @JsonProperty
    private long balance;
}
