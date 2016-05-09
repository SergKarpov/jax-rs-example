package com.sergkarpov.server;



import com.sergkarpov.Constants;
import com.sergkarpov.client.MoneyClient;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


/**
 * Unit test
 */
public class AppTest
{
    private static HttpServer server;

    @BeforeClass
    public static void createServer() throws IOException {
        ResourceConfig config = new DefaultResourceConfig(MoneyService.class);
        server = HttpServerFactory.create(Constants.URL, config);
        server.start();
    }

    @Test
    public void testMoneyTransfer(){
        MoneyClient client = new MoneyClient();
        System.out.println("Initial balances:");
        String balance0001 = client.request("getbalance/0001", HttpMethod.GET);
        System.out.println("Balance in account 0001 is $" + balance0001);
        String balance0002 = client.request("getbalance/0002", HttpMethod.GET);
        System.out.println("Balance in account 0002 is $" + balance0002);

        String delta = "40.17";
        System.out.println("Trasferring $" + delta + " from 0001 to 0002");
        client.request("transfer?amount=" + delta + "&fromAccount=0001&toAccount=0002", HttpMethod.POST);

        String newBalance0001 = client.request("getbalance/0001", HttpMethod.GET);
        System.out.println("New balance in account 0001 is $" + newBalance0001);
        String newBalance0002 = client.request("getbalance/0002", HttpMethod.GET);
        System.out.println("New balance in account 0002 is $" + newBalance0002);
        assertEquals("New balance in account 0001 is wrong!", new BigDecimal(balance0001).subtract(new BigDecimal(delta)), new BigDecimal(newBalance0001));
        assertEquals("New balance in account 0002 is wrong!", new BigDecimal(balance0002).add     (new BigDecimal(delta)), new BigDecimal(newBalance0002));
    }

    @AfterClass
    public static void stopServer() throws IOException {
        server.stop(0);
    }
}
