package rs.mladost.transfer.api.service;

import org.junit.Test;
import rs.mladost.transfer.api.service.dto.TransferRequestDto;
import rs.mladost.transfer.exception.TransferValidationException;
import rs.mladost.transfer.repository.AccountInfoModel;
import rs.mladost.transfer.repository.TransferOperationModel;

import static org.junit.Assert.assertEquals;
import static rs.mladost.transfer.util.DbUtils.insertIntoAccountInfoTable;
import static rs.mladost.transfer.util.message.MessageUtils.getMessage;

public class TransferValidationTest extends AbstractTest {

    @Test
    public void testValidateSenderPasswordFailed() {
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

        TransferRequestDto requestDto = new TransferRequestDto()
                .setSenderId(SENDER_ID)
                .setPassword(WRONG_PASSWORD)
                .setReceiverId(RECEIVER_ID)
                .setAmount(TRANSFER_AMOUNT);
        try {
            transferService.processTransfer(requestDto);
        } catch (TransferValidationException e) {
            assertEquals(getMessage("wrong.password"), e.getMessage());
        }
    }

    @Test
    public void testValidateSenderAccountDoesNotExist() {
        TransferRequestDto requestDto = new TransferRequestDto()
                .setSenderId(SENDER_ID)
                .setPassword(CORRECT_PASSWORD)
                .setReceiverId(RECEIVER_ID)
                .setAmount(TRANSFER_AMOUNT);
        try {
            transferService.processTransfer(requestDto);
        } catch (TransferValidationException e) {
            assertEquals(getMessage("account.does.not.exist"), e.getMessage());
        }
    }

    @Test
    public void testValidateReceiverAccountDoesNotExist() {
        AccountInfoModel senderAccount = new AccountInfoModel()
                .setId(SENDER_ID)
                .setBalance(ACCOUNT_BALANCE)
                .setPassword(HASHED_PASSWORD);
        insertIntoAccountInfoTable(senderAccount);

        TransferRequestDto requestDto = new TransferRequestDto()
                .setSenderId(SENDER_ID)
                .setPassword(CORRECT_PASSWORD)
                .setReceiverId(RECEIVER_ID)
                .setAmount(TRANSFER_AMOUNT);
        try {
            transferService.processTransfer(requestDto);
        } catch (TransferValidationException e) {
            assertEquals(getMessage("account.does.not.exist"), e.getMessage());
        }
    }

    @Test
    public void testValidateTransferAmountExceeded() {
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

        TransferRequestDto requestDto = new TransferRequestDto()
                .setSenderId(SENDER_ID)
                .setPassword(CORRECT_PASSWORD)
                .setReceiverId(RECEIVER_ID)
                .setAmount(TRANSFER_AMOUNT * 2);
        try {
            transferService.processTransfer(requestDto);
        } catch (TransferValidationException e) {
            assertEquals(getMessage("money.amount.exceeded"), e.getMessage());
        }
    }

    @Test
    public void testSuccessfulTransferOperation() {
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

        TransferRequestDto requestDto = new TransferRequestDto()
                .setSenderId(SENDER_ID)
                .setPassword(CORRECT_PASSWORD)
                .setReceiverId(RECEIVER_ID)
                .setAmount(TRANSFER_AMOUNT);
        transferService.processTransfer(requestDto);

        AccountInfoModel resultSenderModel = accountInfoRepository.findAccountInfo(SENDER_ID);
        AccountInfoModel resultReceiverModel = accountInfoRepository.findAccountInfo(RECEIVER_ID);
        assertEquals(0, resultSenderModel.getBalance());
        assertEquals(200, resultReceiverModel.getBalance());
        TransferOperationModel operationModel = transferOperationRepository.findTransferOperation(1);

        assertEquals(SENDER_ID, operationModel.getSenderId());
        assertEquals(RECEIVER_ID, operationModel.getReceiverId());
        assertEquals(TRANSFER_AMOUNT, operationModel.getAmount());
    }
}
