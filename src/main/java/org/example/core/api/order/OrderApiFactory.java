package org.example.core.api.order;

import org.example.core.api.BaseDefaultHttpRestAssuredApiImpl;
import org.example.core.common.ObjectMapperProvider;
import org.example.core.config.ConfigProvider;
import org.example.dto.req.OrderRequestDTO;
import org.example.model.HttpMethod;

public class OrderApiFactory {

    public static BaseDefaultHttpRestAssuredApiImpl createOrder(OrderRequestDTO orderRequestDTO) {
        String reqBody = ObjectMapperProvider.writeValueAsString(orderRequestDTO);
        return BaseDefaultHttpRestAssuredApiImpl.builder().initialize(ConfigProvider.getOrderApiBaseUri(), HttpMethod.POST).withReqBody(reqBody);
    }

    public static BaseDefaultHttpRestAssuredApiImpl getOrders(String orderId) {
        return BaseDefaultHttpRestAssuredApiImpl.builder().initialize(ConfigProvider.getOrderApiBaseUri(), HttpMethod.GET).withQueryParam("orderId", orderId);
    }

    public static BaseDefaultHttpRestAssuredApiImpl patchOrder(OrderRequestDTO orderRequestDTO) {
        String reqBody = ObjectMapperProvider.writeValueAsString(orderRequestDTO);
        return BaseDefaultHttpRestAssuredApiImpl.builder().initialize(ConfigProvider.getOrderApiBaseUri(), HttpMethod.PATCH).withReqBody(reqBody);
    }

}
