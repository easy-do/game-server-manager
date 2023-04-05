package plus.easydo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "Hello from RESTEasy Reactive";
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String post(@Context String param) {
        return param;
    }
}
