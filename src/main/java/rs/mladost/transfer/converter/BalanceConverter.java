package rs.mladost.transfer.converter;

import rs.mladost.transfer.api.rest.dto.request.BalanceRequest;
import rs.mladost.transfer.api.rest.dto.response.BalanceResponse;
import rs.mladost.transfer.api.service.dto.BalanceRequestDto;
import rs.mladost.transfer.repository.AccountInfoModel;

public class BalanceConverter {
    public static BalanceRequestDto convert(BalanceRequest request) {
        return new BalanceRequestDto()
                .setId(request.getId())
                .setPassword(request.getPassword());
    }

    public static BalanceResponse convert(AccountInfoModel accountInfo) {
        return new BalanceResponse()
                .setId(accountInfo.getId())
                .setBalance(accountInfo.getBalance());
    }
}
