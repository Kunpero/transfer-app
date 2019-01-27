package rs.mladost.transfer.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;
import rs.mladost.transfer.config.AppEntryPoint;
import rs.mladost.transfer.config.EntryPointType;
import rs.mladost.transfer.config.WebEntryPoint;

class WebModule extends AbstractModule {
    private Javalin app;

    private WebModule(Javalin app) {
        this.app = app;
    }

    @NotNull
    static WebModule create() {
        return new WebModule(Javalin.create());
    }

    @Override
    protected void configure() {
        bind(Javalin.class).toInstance(app);
        MapBinder.newMapBinder(binder(), EntryPointType.class, AppEntryPoint.class)
                .addBinding(EntryPointType.REST).to(WebEntryPoint.class);
    }
}
