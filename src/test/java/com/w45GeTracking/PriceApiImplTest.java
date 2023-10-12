package com.w45GeTracking;

import com.w45GeTracking.priceApi.PriceApi;
import com.w45GeTracking.priceApi.PriceApiImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceApiImplTest {
    @Test
    public void apiTest() throws Exception{
        PriceApi priceApi = new PriceApiImpl();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.type = TransactionType.BUY;
        transactionInfo.price = 123;
        transactionInfo.id = 1;
        priceApi.addTransaction(transactionInfo);
        Price[] result = priceApi.getPrices(new int[]{ 1 });
        Assertions.assertEquals(result[0].buy.price, 123);
    }
}
