package rs.mladost.transfer.api.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mindrot.jbcrypt.BCrypt;
import rs.mladost.transfer.config.modules.TestModule;
import rs.mladost.transfer.repository.AccountInfoRepository;
import rs.mladost.transfer.repository.TransferOperationRepository;

import static rs.mladost.transfer.util.DbUtils.*;

@RunWith(BlockJUnit4ClassRunner.class)
public abstract class AbstractTest {
    static TransferService transferService;
    static AccountInfoRepository accountInfoRepository;
    static TransferOperationRepository transferOperationRepository;

    static final long SENDER_ID = 1;
    static final long RECEIVER_ID = 2;
    static final long ACCOUNT_BALANCE = 100;
    static final long TRANSFER_AMOUNT = 100;

    static final String CORRECT_PASSWORD = "test";
    static final String HASHED_PASSWORD = BCrypt.hashpw(CORRECT_PASSWORD, BCrypt.gensalt());
    static final String WRONG_PASSWORD = "wrong_password";

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(new TestModule());
        transferService = injector.getInstance(TransferServiceImpl.class);
        accountInfoRepository = injector.getInstance(AccountInfoRepository.class);
        transferOperationRepository = injector.getInstance(TransferOperationRepository.class);
    }

    @Before
    public void initSchema() {
        createAccountInfoTable();
        createTransferOperationTable();
    }

    @After
    public void tearDownSchema() {
        dropAccountInfoTable();
        dropTransferOperationTable();
    }
}
