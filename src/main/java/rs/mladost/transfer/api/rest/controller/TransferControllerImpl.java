package rs.mladost.transfer.api.rest.controller;

import io.javalin.Context;
import rs.mladost.transfer.api.rest.dto.request.BalanceRequest;
import rs.mladost.transfer.api.rest.dto.request.TransferRequest;
import rs.mladost.transfer.api.service.TransferService;

import javax.inject.Inject;
import javax.inject.Singleton;

import static rs.mladost.transfer.converter.BalanceConverter.convert;
import static rs.mladost.transfer.converter.TransferRequestConverter.convert;

@Singleton
public class TransferControllerImpl implements TransferController {
    private TransferService transferService;

    @Inject
    public TransferControllerImpl(TransferService transferService) {
        this.transferService = transferService;
    }

    @Override
    public void processTransfer(Context context) {
        TransferRequest request = context.bodyAsClass(TransferRequest.class);
        context.json(transferService.processTransfer(convert(request)));
    }

    @Override
    public void getBalance(Context context) {
        BalanceRequest request = context.bodyAsClass(BalanceRequest.class);
        context.json(transferService.getBalance(convert(request)));
    }
}
