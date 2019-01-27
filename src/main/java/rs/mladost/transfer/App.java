package rs.mladost.transfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import rs.mladost.transfer.config.modules.AppModule;

import static rs.mladost.transfer.config.EntryPointType.REST;

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        injector.getInstance(Startup.class).boot(REST, args);
    }
}