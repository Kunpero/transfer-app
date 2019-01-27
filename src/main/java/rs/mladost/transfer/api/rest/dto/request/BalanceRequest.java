package rs.mladost.transfer.api.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BalanceRequest {
    @JsonProperty
    private long id;
    @JsonProperty
    private String password;
}
