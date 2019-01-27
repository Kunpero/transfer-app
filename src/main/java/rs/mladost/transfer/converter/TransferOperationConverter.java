package rs.mladost.transfer.converter;

import rs.mladost.transfer.api.service.dto.TransferRequestDto;
import rs.mladost.transfer.repository.TransferOperationModel;


public class TransferOperationConverter {
    public static TransferOperationModel convert(TransferRequestDto dto) {
        return new TransferOperationModel()
                .setAmount(dto.getAmount())
                .setSenderId(dto.getSenderId())
                .setReceiverId(dto.getReceiverId());
    }
}
