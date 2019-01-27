package rs.mladost.transfer.api.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BalanceRequestDto {
    private long id;
    private String password;
}
