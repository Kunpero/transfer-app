package rs.mladost.transfer.api.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransferRequestDto {
    private long senderId;
    private String password;
    private long receiverId;
    private long amount;
}
