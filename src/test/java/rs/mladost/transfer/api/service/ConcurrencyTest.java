package rs.mladost.transfer.api.service;

import org.junit.Assert;
import org.junit.Test;
import rs.mladost.transfer.api.rest.dto.response.Response;
import rs.mladost.transfer.api.service.dto.TransferRequestDto;
import rs.mladost.transfer.repository.AccountInfoModel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static rs.mladost.transfer.util.DbUtils.insertIntoAccountInfoTable;

public class ConcurrencyTest extends AbstractTest {
    @Test
    public void testMultipleTransferOperationRequests() throws InterruptedException {
        long firstAccountId = SENDER_ID;
        long secondAccountId = RECEIVER_ID;
        long firstTransferAmount = 30;
        long secondTransferAmount = 70;
        AccountInfoModel senderAccount = new AccountInfoModel()
                .setId(SENDER_ID)
                .setBalance(ACCOUNT_BALANCE)
                .setPassword(HASHED_PASSWORD);
        AccountInfoModel receiverAccount = new AccountInfoModel()
                .setId(RECEIVER_ID)
                .setBalance(ACCOUNT_BALANCE)
                .setPassword(HASHED_PASSWORD);
        insertIntoAccountInfoTable(senderAccount);
        insertIntoAccountInfoTable(receiverAccount);

        TransferRequestDto firstToSecondTransferRequest = new TransferRequestDto()
                .setSenderId(firstAccountId)
                .setPassword(CORRECT_PASSWORD)
                .setReceiverId(secondAccountId)
                .setAmount(firstTransferAmount);
        TransferRequestDto secondToFirstTransferRequest = new TransferRequestDto()
                .setSenderId(secondAccountId)
                .setPassword(CORRECT_PASSWORD)
                .setReceiverId(firstAccountId)
                .setAmount(secondTransferAmount);

        List<Callable<Response>> taskList = Arrays.asList(() -> transferService.processTransfer(firstToSecondTransferRequest),
                () -> transferService.processTransfer(secondToFirstTransferRequest));
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.invokeAll(taskList);

        AccountInfoModel firstAccount = accountInfoRepository.findAccountInfo(firstAccountId);
        AccountInfoModel secondAccount = accountInfoRepository.findAccountInfo(secondAccountId);

        Assert.assertEquals(140, firstAccount.getBalance());
        Assert.assertEquals(60, secondAccount.getBalance());
    }
}
