package com.w45GeTracking.priceApi;

import com.w45GeTracking.TransactionInfo;

public class AddTransactionRequestBody {
    public AddTransactionRequestBody(TransactionInfo transactionInfo){
        args[0] = transactionInfo;
    }

    public final String name = "addTransaction";
    public final TransactionInfo[] args = new TransactionInfo[1];
}
