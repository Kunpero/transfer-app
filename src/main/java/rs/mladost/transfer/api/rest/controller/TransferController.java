package rs.mladost.transfer.api.rest.controller;

import io.javalin.Context;

public interface TransferController {
    void processTransfer(Context context);

    void getBalance(Context context);
}
