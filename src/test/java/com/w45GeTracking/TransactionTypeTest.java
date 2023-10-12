package com.w45GeTracking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTypeTest {

    @Test
    public void testStringConversion(){
        Assertions.assertEquals("BUY", TransactionType.BUY.toString());
        Assertions.assertEquals("SELL", TransactionType.SELL.toString());
    }
}
