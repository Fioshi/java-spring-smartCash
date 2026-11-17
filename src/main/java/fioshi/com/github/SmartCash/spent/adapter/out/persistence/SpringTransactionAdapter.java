package fioshi.com.github.SmartCash.spent.adapter.out.persistence;

import fioshi.com.github.SmartCash.spent.application.port.out.TransactionPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

@Component
public class SpringTransactionAdapter implements TransactionPort {

    private final TransactionTemplate transactionTemplate;

    public SpringTransactionAdapter(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public <T> T execute(Supplier<T> action) {
        return transactionTemplate.execute(status -> action.get());
    }

    @Override
    public void execute(Runnable action) {
        transactionTemplate.executeWithoutResult(status -> action.run());
    }
}
