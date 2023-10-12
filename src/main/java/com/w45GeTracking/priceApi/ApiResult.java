package com.w45GeTracking.priceApi;

import com.w45GeTracking.Price;

public class ApiResult {
    public Price[] value;
    public ApiResult(Price[] value){
        this.value = value;
    }
}
