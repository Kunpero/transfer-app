package rs.mladost.transfer.converter;

import rs.mladost.transfer.api.rest.dto.request.TransferRequest;
import rs.mladost.transfer.api.service.dto.TransferRequestDto;

public class TransferRequestConverter {
    public static TransferRequestDto convert(TransferRequest request) {
        return new TransferRequestDto()
                .setAmount(request.getAmount())
                .setPassword(request.getPassword())
                .setSenderId(request.getSenderId())
                .setReceiverId(request.getReceiverId());
    }
}
