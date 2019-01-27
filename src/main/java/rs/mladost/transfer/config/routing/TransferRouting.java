package rs.mladost.transfer.config.routing;

import io.javalin.Javalin;
import rs.mladost.transfer.api.rest.controller.TransferControllerImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

@Singleton
public class TransferRouting extends Routing<TransferControllerImpl> {
    private Javalin javalin;

    @Inject
    public TransferRouting(Javalin javalin) {
        this.javalin = javalin;
    }

    @Override
    public void bindRoutes() {
        javalin.routes(() -> {
            path("money/rest/transfer.do", () ->
                    post(context -> getController().processTransfer(context)));
            path("money/rest/balance.do", () ->
                    post(context -> getController().getBalance(context))
            );
        });
    }
}
