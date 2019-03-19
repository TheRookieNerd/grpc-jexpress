package com.flipkart.grpc.jexpress.service;

import com.flipkart.gjex.core.filter.MethodFilters;
import com.flipkart.gjex.core.logging.Logging;
import com.flipkart.grpc.jexpress.*;
import com.flipkart.grpc.jexpress.filter.CreateLoggingFilter;
import com.flipkart.grpc.jexpress.filter.GetLoggingFilter;
import io.grpc.stub.StreamObserver;
import org.apache.commons.configuration.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Named("SampleService")
public class SampleService extends UserServiceGrpc.UserServiceImplBase implements Logging {

    private ConcurrentHashMap<Integer, String> userIdToUserNameMap = new ConcurrentHashMap<>();
    private AtomicInteger lastId = new AtomicInteger(0);

    private final SampleConfiguration sampleConfiguration;
    private final Configuration flattenedConfig;
    private final Map mapConfig;

    @Inject
    public SampleService(SampleConfiguration sampleConfiguration,
                         @Named("GlobalFlattenedConfig") Configuration flattenedConfig,
                         @Named("GlobalMapConfig") Map mapConfig) {
        this.sampleConfiguration = sampleConfiguration;
        this.flattenedConfig = flattenedConfig;
        this.mapConfig = mapConfig;
    }

    @Override
    @MethodFilters({GetLoggingFilter.class})
    public void getUser(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        GetResponse response = GetResponse.newBuilder()
                .setId(request.getId())
                .setUserName(userIdToUserNameMap.getOrDefault(request.getId(), "Guest")).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        info(sampleConfiguration.toString());
        info(mapConfig.toString());

        // Read values from Flattened config
        info("FlattenedConfig has employee.name = " + flattenedConfig.getString("employee.name"));
        info("FlattenedConfig has Grpc.server.port = " + flattenedConfig.getInt("Grpc.server.port"));
        info("FlattenedConfig has employee.properties.toys = " +flattenedConfig.getStringArray("employee.properties.toys").toString());
        info("FlattenedConfig has employee.properties.foo = " + flattenedConfig.getString("employee.properties.foo"));
        info("FlattenedConfig has employee.properties.bar = " + flattenedConfig.getStringArray("employee.properties.bar").toString());

        // Read values from plain map
        info("MapConfig of Dashboard = " + mapConfig.get("Dashboard").toString());
        info("MapConfig of employee = " + mapConfig.get("employee").toString());
        Object studentProperties = ((Map<String, Object>) mapConfig.get("employee")).get("properties");
        info("MapConfig -> properties in employee = " + studentProperties);
    }

    @Override
    @MethodFilters({CreateLoggingFilter.class})
    public void createUser(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        int id = lastId.incrementAndGet();
        userIdToUserNameMap.put(id, request.getUserName());
        CreateResponse response = CreateResponse.newBuilder()
                .setId(id)
                .setIsCreated(true).
                        build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
