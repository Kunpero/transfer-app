package rs.mladost.transfer.config.modules;

import com.google.inject.AbstractModule;
import rs.mladost.transfer.Startup;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Startup.class);
        install(new TransferModule());
    }
}
