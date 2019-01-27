package rs.mladost.transfer.repository;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.mladost.transfer.config.db.DataSource;
import rs.mladost.transfer.exception.TransferSqlException;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static rs.mladost.transfer.util.message.MessageUtils.getMessage;

@Singleton
public class AccountInfoRepository {
    private static final Logger log = LoggerFactory.getLogger(AccountInfoRepository.class);

    private static final String FIND_ACCOUNT_INFO_QUERY = "SELECT * FROM ACCOUNT_INFO WHERE ID = ?";
    private static final String INSERT_ACCOUNT_INFO = "UPDATE ACCOUNT_INFO SET BALANCE = ? WHERE ID = ?";

    public void updateAccountsInfo(long accountId, long newBalance) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_INFO)) {
            preparedStatement.setLong(1, newBalance);
            preparedStatement.setLong(2, accountId);

            if (preparedStatement.executeUpdate() != 1) {
                log.error("Record update into ACCOUNT_INFO table was failed");
                throw new TransferSqlException(getMessage("internal.error"));
            }
        } catch (SQLException e) {
            throw new TransferSqlException(e.getMessage());
        }
    }

    @Nullable
    public AccountInfoModel findAccountInfo(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACCOUNT_INFO_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new AccountInfoModel()
                        .setId(resultSet.getLong(1))
                        .setBalance(resultSet.getLong(2))
                        .setPassword(resultSet.getString(3));
            } else {
                log.warn("account with id [{}] not found", id);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
