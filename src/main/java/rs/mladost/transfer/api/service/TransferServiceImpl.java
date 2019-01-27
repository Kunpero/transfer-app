package rs.mladost.transfer.api.service;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.mladost.transfer.api.rest.dto.response.Response;
import rs.mladost.transfer.api.rest.dto.response.TransferResponse;
import rs.mladost.transfer.api.service.dto.BalanceRequestDto;
import rs.mladost.transfer.api.service.dto.TransferRequestDto;
import rs.mladost.transfer.exception.TransferValidationException;
import rs.mladost.transfer.repository.AccountInfoModel;
import rs.mladost.transfer.repository.AccountInfoRepository;
import rs.mladost.transfer.repository.TransferOperationRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;

import static rs.mladost.transfer.converter.BalanceConverter.convert;
import static rs.mladost.transfer.converter.TransferOperationConverter.convert;
import static rs.mladost.transfer.util.concurrency.AccountOperationUtils.executeSafely;
import static rs.mladost.transfer.util.message.MessageUtils.getMessage;

@Singleton
public class TransferServiceImpl implements TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    private AccountInfoRepository accountInfoRepository;
    private TransferOperationRepository transferOperationRepository;

    @Inject
    public TransferServiceImpl(AccountInfoRepository accountInfoRepository, TransferOperationRepository transferOperationModel) {
        this.accountInfoRepository = accountInfoRepository;
        this.transferOperationRepository = transferOperationModel;
    }

    public Response processTransfer(TransferRequestDto dto) {
        AccountInfoModel senderAccount = accountInfoRepository.findAccountInfo(dto.getSenderId());
        AccountInfoModel receiverAccount = accountInfoRepository.findAccountInfo(dto.getReceiverId());
        validateTransferOperation(dto, senderAccount, receiverAccount);
        updateBalance(dto, senderAccount.getId(), receiverAccount.getId());
        return new TransferResponse();

    }

    public Response getBalance(BalanceRequestDto dto) {
        AccountInfoModel accountInfo = accountInfoRepository.findAccountInfo(dto.getId());
        validateAccountInfo(accountInfo, dto.getId());
        validatePassword(dto.getPassword(), accountInfo.getPassword());
        return convert(accountInfo);
    }

    private void updateBalance(TransferRequestDto dto, long senderId, long receiverId) {
        LocalDateTime operationDate = LocalDateTime.now();
        executeSafely(senderId, id -> {
            long actualBalance = accountInfoRepository.findAccountInfo(id).getBalance();
            validateBalance(actualBalance, dto.getAmount());
            accountInfoRepository.updateAccountsInfo(dto.getSenderId(), actualBalance - dto.getAmount());
        });
        executeSafely(receiverId, id -> {
            long actualBalance = accountInfoRepository.findAccountInfo(id).getBalance();
            accountInfoRepository.updateAccountsInfo(dto.getReceiverId(), actualBalance + dto.getAmount());
        });
        transferOperationRepository.saveTransferOperation(convert(dto), operationDate);
    }

    private void validateTransferOperation(TransferRequestDto dto, AccountInfoModel senderAccount,
                                           AccountInfoModel receiverAccount) {
        validateAccountInfo(senderAccount, dto.getSenderId());
        validateAccountInfo(receiverAccount, dto.getReceiverId());
        validatePassword(dto.getPassword(), senderAccount.getPassword());
    }

    private void validateAccountInfo(AccountInfoModel accountInfo, long requestedId) {
        if (accountInfo == null) {
            log.error("account with id [{}], doesn't exist", requestedId);
            throw new TransferValidationException(getMessage("account.does.not.exist"));
        }
    }

    private void validatePassword(String requestPassword, String hashedPassword) {
        if (!BCrypt.checkpw(requestPassword, hashedPassword)) {
            log.error("wrong password");
            throw new TransferValidationException(getMessage("wrong.password"));
        }
    }

    private void validateBalance(long actualBalance, long amount) {
        if (actualBalance < amount) {
            log.error("money amount exceeded: actual balance is [{}], but transfer amount is [{}]",
                    actualBalance, amount);
            throw new TransferValidationException(getMessage("money.amount.exceeded"));
        }
    }
}
