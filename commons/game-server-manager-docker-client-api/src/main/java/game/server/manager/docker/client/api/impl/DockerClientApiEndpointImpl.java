package game.server.manager.docker.client.api.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import game.server.manager.docker.client.api.DockerClientApi;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerContainerApi;
import game.server.manager.docker.client.api.DockerImageApi;
import game.server.manager.docker.client.api.config.DockerClientApiConfiguration;
import feign.jackson.JacksonEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author laoyu
 * @version 1.0
 * @description docker client api 端点
 * @date 2022/11/19
 */
public class DockerClientApiEndpointImpl implements DockerClientApiEndpoint {


    private DockerClientApiConfiguration dockerClientApiConfiguration;


    private Client client() {
        return new OkHttpClient();
    }

    public DockerClientApiEndpointImpl( ) {
        this.dockerClientApiConfiguration = new DockerClientApiConfiguration();
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat(new SimpleDateFormat(dockerClientApiConfiguration.getDateTimeFormat()));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dockerClientApiConfiguration.getDateTimeFormat())));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(dockerClientApiConfiguration.getTimeFormat())));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(dockerClientApiConfiguration.getDateFormat())));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dockerClientApiConfiguration.getDateTimeFormat())));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(dockerClientApiConfiguration.getTimeFormat())));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(dockerClientApiConfiguration.getDateFormat())));
        objectMapper.registerModule(timeModule);
        return objectMapper;
    }

    private Feign.Builder feignBuilder(String secret) {
        int connectTimeoutMillis = dockerClientApiConfiguration.getConnectTimeoutMillis();
        int readTimeoutMillis = dockerClientApiConfiguration.getReadTimeoutMillis();
        long period = dockerClientApiConfiguration.getRetryPeriod();
        long maxPeriod = dockerClientApiConfiguration.getRetryMaxPeriod();
        int mxAttempts = dockerClientApiConfiguration.getRetryMaxAttempts();
            ObjectMapper objectMapper = this.objectMapper();
        return Feign.builder()
                    .client(this.client())
                    .encoder(new JacksonEncoder(objectMapper))
                    .decoder(new JacksonDecoder(objectMapper))
                    .options(new Request.Options(connectTimeoutMillis, TimeUnit.MILLISECONDS, readTimeoutMillis, TimeUnit.MILLISECONDS, true))
                    .retryer(new Retryer.Default(period, maxPeriod, mxAttempts))
                    //请求头添加通信密钥
                    .requestInterceptor(requestTemplate -> requestTemplate.header("secret",secret));
    }

    @Override
    public DockerClientApi dockerClientApi(String serverUrl,String secret) {
        return feignBuilder(secret).target(DockerClientApi.class, serverUrl);
    }

    @Override
    public DockerImageApi dockerImageApi(String serverUrl, String secret) {
        return feignBuilder(secret).target(DockerImageApi.class, serverUrl);
    }

    @Override
    public DockerContainerApi dockerContainerApi(String serverUrl, String secret) {
        return feignBuilder(secret).target(DockerContainerApi.class, serverUrl);
    }

}
