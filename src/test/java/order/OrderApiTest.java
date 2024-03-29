package order;

import org.example.core.api.BaseDefaultHttpRestAssuredApiImpl;
import org.example.core.api.order.OrderApiFactory;
import org.example.core.db.PostgresClient;
import org.example.core.framework.RetryTestSuiteExtension;
import org.example.dto.req.OrderRequestDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RetryTestSuiteExtension.class)
public class OrderApiTest {

    @BeforeEach
    public void setUp() {
        PostgresClient.getInstance().getConnection();
    }

    @Test
    public void testCreateOrder() {
        OrderRequestDTO requestDTO = OrderRequestDTO.builder().productCode("123").productDescription("test").amount(100).quantity(1).build();
        BaseDefaultHttpRestAssuredApiImpl createOrderApi = OrderApiFactory.createOrder(requestDTO);
        createOrderApi.doCall();
        createOrderApi.assertStatusCode(201);
        createOrderApi.doResponseSchemaValidation("order-response-schema.json");
    }

    @Test
    public void testGetOrder() {
        BaseDefaultHttpRestAssuredApiImpl getOrderApi = OrderApiFactory.getOrders("123");
        getOrderApi.doCall();
        getOrderApi.assertStatusCode(200);
        getOrderApi.doResponseSchemaValidation("get-orders-response-schema.json");
    }

    @Test
    public void testPatchOrder() {
        OrderRequestDTO requestDTO = OrderRequestDTO.builder().productCode("123").productDescription("test").amount(100).quantity(1).build();
        BaseDefaultHttpRestAssuredApiImpl patchOrderApi = OrderApiFactory.patchOrder(requestDTO);
        patchOrderApi.doCall();
        patchOrderApi.assertStatusCode(200);
        patchOrderApi.doResponseSchemaValidation("patch-order-response-schema.json");
    }

    @AfterAll
    public static void tearDown() {
        PostgresClient.getInstance().close();
    }

}
