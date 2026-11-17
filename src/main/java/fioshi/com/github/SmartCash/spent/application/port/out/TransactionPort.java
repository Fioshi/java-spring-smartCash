package fioshi.com.github.SmartCash.spent.application.port.out;

import java.util.function.Supplier;

public interface TransactionPort {

    <T> T execute(Supplier<T> action);

    void execute(Runnable action);
}
