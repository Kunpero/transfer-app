package rs.mladost.transfer.api.service;

import org.junit.Test;
import rs.mladost.transfer.api.rest.dto.response.BalanceResponse;
import rs.mladost.transfer.api.service.dto.BalanceRequestDto;
import rs.mladost.transfer.exception.TransferValidationException;
import rs.mladost.transfer.repository.AccountInfoModel;

import static org.junit.Assert.assertEquals;
import static rs.mladost.transfer.util.DbUtils.insertIntoAccountInfoTable;
import static rs.mladost.transfer.util.message.MessageUtils.getMessage;

public class BalanceValidationTest extends AbstractTest {
    @Test
    public void testValidationAccountDoesNotExist() {
        BalanceRequestDto requestDto = new BalanceRequestDto()
                .setId(SENDER_ID)
                .setPassword(CORRECT_PASSWORD);
        try {
            transferService.getBalance(requestDto);
        } catch (TransferValidationException e) {
            assertEquals(getMessage("account.does.not.exist"), e.getMessage());
        }
    }

    @Test
    public void testValidationPasswordFailed() {
        AccountInfoModel account = new AccountInfoModel()
                .setId(SENDER_ID)
                .setBalance(ACCOUNT_BALANCE)
                .setPassword(HASHED_PASSWORD);
        insertIntoAccountInfoTable(account);
        BalanceRequestDto requestDto = new BalanceRequestDto()
                .setId(SENDER_ID)
                .setPassword(WRONG_PASSWORD);
        try {
            transferService.getBalance(requestDto);
        } catch (TransferValidationException e) {
            assertEquals(getMessage("wrong.password"), e.getMessage());
        }
    }

    @Test
    public void testValidationBalanceOperationSuccessful() {
        AccountInfoModel account = new AccountInfoModel()
                .setId(SENDER_ID)
                .setBalance(ACCOUNT_BALANCE)
                .setPassword(HASHED_PASSWORD);
        insertIntoAccountInfoTable(account);
        BalanceRequestDto requestDto = new BalanceRequestDto()
                .setId(SENDER_ID)
                .setPassword(CORRECT_PASSWORD);
        BalanceResponse balanceResponse = (BalanceResponse) transferService.getBalance(requestDto);
        assertEquals(ACCOUNT_BALANCE, balanceResponse.getBalance());
        assertEquals(SENDER_ID, balanceResponse.getId());
    }
}
