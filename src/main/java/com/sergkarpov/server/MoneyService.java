package com.sergkarpov.server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

/**
 * Created by serg on 05.05.2016.
 */
@Path("/")
public class MoneyService {
    private Accounts accounts = Accounts.getInstance();

    @GET
    @Path("/getbalance/{account}")
    public String getBalance(@PathParam("account") String account) {
        String amount = accounts.getBalance(account).toString();
        return amount;
    }

    @POST
    @Path("/transfer")
    public String transfer(@QueryParam("amount") String amount,
                           @QueryParam("fromAccount") String fromAccount,
                           @QueryParam("toAccount") String toAccount) {
        accounts.transfer(new BigDecimal(amount), fromAccount, toAccount);
        return "Tranferred";
    }
}
