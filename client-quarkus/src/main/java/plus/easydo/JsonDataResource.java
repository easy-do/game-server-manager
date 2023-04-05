package plus.easydo;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.vertx.ext.web.Router;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2023-04-05 11:15
 * @Description 传输json格式数据
 */
@Path("/json_data")
public class JsonDataResource {


    @Inject
    JacksonObjectMapper jacksonObjectMapper;

    private static final List<ServerMessage> serverMessageList = new ArrayList<>();


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add (ServerMessage serverMessage){
        serverMessageList.add(serverMessage);
       return Response.ok(serverMessageList).build();
    }

    public void get (@Observes Router router){
        router.get("/json_data/get")
                //❸返回配置值
                .handler(rc-> {
                    try {
                        rc.response().send(jacksonObjectMapper.writeValueAsString(serverMessageList));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


}
