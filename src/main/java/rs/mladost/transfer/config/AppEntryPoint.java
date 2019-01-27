package rs.mladost.transfer.config;

import io.javalin.Javalin;

public interface AppEntryPoint {
    Javalin boot(String[] args);
}
