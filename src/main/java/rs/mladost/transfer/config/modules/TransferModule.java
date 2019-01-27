package rs.mladost.transfer.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import rs.mladost.transfer.api.service.TransferService;
import rs.mladost.transfer.api.service.TransferServiceImpl;
import rs.mladost.transfer.config.routing.Routing;
import rs.mladost.transfer.config.routing.TransferRouting;

public class TransferModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransferService.class).to(TransferServiceImpl.class);
        install(WebModule.create());
        Multibinder.newSetBinder(binder(), Routing.class).addBinding().to(TransferRouting.class);
    }
}
