package rs.mladost.transfer.repository;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.mladost.transfer.config.db.DataSource;
import rs.mladost.transfer.exception.TransferSqlException;

import javax.inject.Singleton;
import java.sql.*;
import java.time.LocalDateTime;

import static rs.mladost.transfer.util.message.MessageUtils.getMessage;

@Singleton
public class TransferOperationRepository {
    private static final Logger log = LoggerFactory.getLogger(TransferOperationRepository.class);

    private static final String INSERT_TRANSFER_OPERATION_QUERY = "INSERT INTO TRANSFER_OPERATION " +
            "(AMOUNT, SENDER_ID, RECEIVER_ID, OPERATION_DATE)" +
            "VALUES (?, ?, ?, ?)";
    private static final String FIND_TRANSFER_OPERATION_QUERY = "SELECT * FROM TRANSFER_OPERATION WHERE ID = ?";

    public void saveTransferOperation(TransferOperationModel transferOperation, LocalDateTime operationDate) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSFER_OPERATION_QUERY)) {
            preparedStatement.setLong(1, transferOperation.getAmount());
            preparedStatement.setLong(2, transferOperation.getSenderId());
            preparedStatement.setLong(3, transferOperation.getReceiverId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(operationDate));

            if (preparedStatement.executeUpdate() != 1) {
                log.error("Record insert into TRANSFER_OPERATION table was failed");
                throw new TransferSqlException(getMessage("internal.error"));
            }
        } catch (SQLException e) {
            throw new TransferSqlException(e.getMessage());
        }
    }

    @Nullable
    public TransferOperationModel findTransferOperation(long operationId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TRANSFER_OPERATION_QUERY)) {
            preparedStatement.setLong(1, operationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new TransferOperationModel()
                        .setId(resultSet.getLong(1))
                        .setAmount(resultSet.getLong(2))
                        .setSenderId(resultSet.getLong(3))
                        .setReceiverId(resultSet.getLong(4))
                        .setOperationDate(resultSet.getTimestamp(5).toLocalDateTime());
            } else {
                log.warn("operation with id [{}] not found", operationId);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
