package rs.mladost.transfer.api.service;

import rs.mladost.transfer.api.rest.dto.response.Response;
import rs.mladost.transfer.api.service.dto.BalanceRequestDto;
import rs.mladost.transfer.api.service.dto.TransferRequestDto;

public interface TransferService {
    Response processTransfer(TransferRequestDto dto);

    Response getBalance(BalanceRequestDto dto);
}
