package com.sergkarpov.client;

import com.sergkarpov.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;


/**
 * Created by serg on 05.05.2016.
 */
public class MoneyClient {
    private Client client = Client.create();

    public String request(String path, String method) {
        WebResource.Builder builder = client.resource(Constants.URL + path).accept(MediaType.TEXT_PLAIN);

        ClientResponse response = null;
        switch (method) {
            case HttpMethod.GET  : response = builder.get (ClientResponse.class); break;
            case HttpMethod.POST : response = builder.post(ClientResponse.class);
        }

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response.getEntity(String.class);
    }


}
