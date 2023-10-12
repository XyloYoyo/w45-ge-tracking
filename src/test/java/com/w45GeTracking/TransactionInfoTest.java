package com.w45GeTracking;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionInfoTest {
    @Test
    public void jsonConversion(){
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.id = 123;
        transactionInfo.price = 456;
        transactionInfo.type = TransactionType.BUY;
        Gson gson = new Gson();
        String json = gson.toJson(transactionInfo);
        JsonElement actual = JsonParser.parseString(json);
        JsonElement expected = JsonParser.parseString("{ 'id': 123, 'price': 456, 'type':'BUY' }");
        Assertions.assertEquals(actual, expected);
    }
}
