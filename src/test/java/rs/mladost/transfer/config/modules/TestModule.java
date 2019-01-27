package rs.mladost.transfer.config.modules;

import com.google.inject.AbstractModule;
import rs.mladost.transfer.Startup;
import rs.mladost.transfer.api.service.TransferService;
import rs.mladost.transfer.api.service.TransferServiceImpl;

public class TestModule extends AbstractModule {
    protected void configure() {
        bind(Startup.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
        install(new TransferModule());
    }
}
