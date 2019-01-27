package rs.mladost.transfer.util.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.mladost.transfer.exception.OperationNotAvailableException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class AccountOperationUtils {
    private static final Logger log = LoggerFactory.getLogger(AccountOperationUtils.class);
    private static final long LOCK_TIMEOUT = 1000;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    private static final Map<Long, Lock> lockMap = new ConcurrentHashMap<>();

    public static void executeSafely(long accountId, Consumer<Long> action) {
        Lock lock = lockMap.computeIfAbsent(accountId, k -> new ReentrantLock());
        try {
            if (lock.tryLock(LOCK_TIMEOUT, TIMEOUT_UNIT)) {
                try {
                    action.accept(accountId);
                } finally {
                    lock.unlock();
                    lockMap.remove(accountId);
                }
            } else {
                log.error("Operation for account id [{}] is not available at the moment", accountId);
                throw new OperationNotAvailableException("operation.not.available");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("internal.error");
        }
    }
}
