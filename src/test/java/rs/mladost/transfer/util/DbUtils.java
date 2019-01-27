package rs.mladost.transfer.util;

import rs.mladost.transfer.config.db.DataSource;
import rs.mladost.transfer.repository.AccountInfoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtils {
    private static final String CREATE_ACCOUNT_INFO_TABLE_QUERY = "  CREATE TABLE ACCOUNT_INFO(\n" +
            "  ID                    BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
            "  BALANCE               BIGINT,\n" +
            "  PASSWORD              VARCHAR (200)\n" +
            ")";
    private static final String DROP_ACCOUNT_INFO_TABLE_QUERY = "DROP TABLE ACCOUNT_INFO";
    private static final String DROP_TRANSFER_OPERATION_TABLE_QUERY = "DROP TABLE TRANSFER_OPERATION";
    private static final String CREATE_TRANSFER_OPERATION_QUERY = "CREATE TABLE TRANSFER_OPERATION(\n" +
            "  ID                    BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
            "  AMOUNT                BIGINT,\n" +
            "  SENDER_ID             BIGINT,\n" +
            "  RECEIVER_ID           BIGINT,\n" +
            "  OPERATION_DATE        TIMESTAMP\n" +
            ")";
    private static final String INSERT_INTO_ACCOUNT_INFO_TABLE_QUERY = "INSERT INTO ACCOUNT_INFO VALUES " +
            "(?,?,?)";

    public static void createAccountInfoTable() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ACCOUNT_INFO_TABLE_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }

    public static void createTransferOperationTable() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TRANSFER_OPERATION_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }

    public static void dropAccountInfoTable() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_ACCOUNT_INFO_TABLE_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }

    public static void dropTransferOperationTable() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TRANSFER_OPERATION_TABLE_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }

    public static void insertIntoAccountInfoTable(AccountInfoModel infoModel) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ACCOUNT_INFO_TABLE_QUERY)) {
            preparedStatement.setLong(1, infoModel.getId());
            preparedStatement.setLong(2, infoModel.getBalance());
            preparedStatement.setString(3, infoModel.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }
}
