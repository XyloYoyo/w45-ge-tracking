package com.w45GeTracking.priceApi;

import com.w45GeTracking.Price;
import com.w45GeTracking.TransactionInfo;

public interface PriceApi {
    public void addTransaction(TransactionInfo transactionInfo) throws Exception;
    public Price[] getPrices(int[] itemIds) throws Exception;
}
