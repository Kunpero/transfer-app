package rs.mladost.transfer.config;

import com.google.inject.Inject;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.mladost.transfer.api.rest.dto.response.ErrorResponse;
import rs.mladost.transfer.config.routing.Routing;
import rs.mladost.transfer.exception.OperationNotAvailableException;
import rs.mladost.transfer.exception.TransferValidationException;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Set;

import static rs.mladost.transfer.exception.ErrorType.*;
import static rs.mladost.transfer.util.message.MessageUtils.getMessage;

@Singleton
public class WebEntryPoint implements AppEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(WebEntryPoint.class);

    private static final String APPLICATION_CONTEXT = "money";
    private static final int DEFAULT_PORT = 8080;

    private Javalin app;

    @Inject(optional = true)
    private Set<Routing> routes = Collections.emptySet();

    @Inject
    public WebEntryPoint(Javalin app) {
        this.app = app;
    }

    @Override
    public Javalin boot(String[] args) {
        bindRoutes();
        app.port(args.length != 0 ? Integer.valueOf(args[0]) : DEFAULT_PORT)
                .contextPath(APPLICATION_CONTEXT)
                .enableDebugLogging()
                .disableStartupBanner();
        setExceptionHandlers();
        return app.start();
    }

    private void setExceptionHandlers() {
        app.exception(TransferValidationException.class, (e, ctx) ->
                ctx.json(new ErrorResponse(VALIDATION_ERROR.getValue(), e.getMessage()))
        );
        app.exception(OperationNotAvailableException.class, (e, ctx) ->
                ctx.json(new ErrorResponse(OPERATION_ERROR.getValue(), e.getMessage()))
        );
        app.exception(Exception.class, (e, ctx) -> {
                    log.error("Internal system error [{}]", e.getMessage());
                    ctx.json(new ErrorResponse(INTERNAL_ERROR.getValue(), getMessage("internal.error")));
                }
        );
    }

    private void bindRoutes() {
        routes.forEach(Routing::bindRoutes);
    }
}
