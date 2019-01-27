package rs.mladost.transfer.api.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferRequest {
    @JsonProperty
    private long senderId;
    @JsonProperty
    private String password;
    @JsonProperty
    private long receiverId;
    @JsonProperty
    private long amount;
}
