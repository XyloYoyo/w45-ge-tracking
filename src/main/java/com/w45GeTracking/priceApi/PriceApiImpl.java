package com.w45GeTracking.priceApi;

import com.google.gson.Gson;
import com.w45GeTracking.Price;
import com.w45GeTracking.TransactionInfo;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PriceApiImpl implements PriceApi {

    private final String apiUrl = "http://51.15.241.60/w45-ge-api";
    private final String apiPipelineUrl = "http://51.15.241.60/w45-ge-api/pipeline";
    @Inject
    private Gson gson;
    @Override
    public void addTransaction(TransactionInfo transactionInfo) throws Exception {
        String requestData = gson.toJson(new AddTransactionRequestBody(transactionInfo));
        post(apiUrl, requestData);
    }
    @Override
    public Price[] getPrices(int[] itemIds) throws Exception {
        GetPricesRequestBody priceBody = new GetPricesRequestBody(itemIds);
        String requestData = gson.toJson(priceBody);
        String responseData = post(apiUrl, requestData);
        ApiResult result = gson.fromJson(responseData, ApiResult.class);
        return result.value;
    }
    private String post(String url, String body) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if(statusCode != 200){
            throw new IOException("post request error " + response + " body " + response.body());
        }
        return response.body();
    }
}
