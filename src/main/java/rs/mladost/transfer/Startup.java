package rs.mladost.transfer;

import com.google.inject.Inject;
import io.javalin.Javalin;
import rs.mladost.transfer.config.AppEntryPoint;
import rs.mladost.transfer.config.EntryPointType;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Map;

@Singleton
public class Startup {
    @Inject(optional = true)
    private Map<EntryPointType, AppEntryPoint> entryPoints = Collections.emptyMap();

    Javalin boot(EntryPointType entryPointType, String[] args) {
        AppEntryPoint entryPoint = entryPoints.get(entryPointType);
        return entryPoint.boot(args);
    }
}
