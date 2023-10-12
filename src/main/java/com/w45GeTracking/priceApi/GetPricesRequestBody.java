package com.w45GeTracking.priceApi;

public class GetPricesRequestBody {
    public GetPricesRequestBody(int[] id){
        args[0] = id.clone();
    }
    public final String name = "getPrices";
    public final int[][] args = new int[1][1];

}
