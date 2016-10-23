package resources;

/**
 * Created by danie on 10/19/2016.
 */
import api.RhinoResult;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;
import org.mozilla.javascript.*;

@Path("/Rhino")
@Produces(MediaType.APPLICATION_JSON)
public class RhinoResource {
    private final String defaultResult;
    private final AtomicLong counter;

    public RhinoResource( String defaultResult) {
        this.defaultResult = defaultResult;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public RhinoResult sayHello(@QueryParam("JavaScript") String Javascript) {
        String value;
        value = defaultResult;
        Context cx = Context.enter();
        try {
            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed. Returns
            // a scope object that we use in later calls.
            Scriptable scope = cx.initStandardObjects();

            // Collect the arguments into a single string.
            String s = Javascript;


            // Now evaluate the string we've colected.
            Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);

            // Convert the result to a string and print it.
            value = Context.toString(result);

        } finally {
            // Exit from the context.
            Context.exit();
        }
        return new RhinoResult(counter.incrementAndGet(), value);
    }
}
