package com.example.qrretrofit.api;

import com.example.qrretrofit.interfaces.GenericCallback;
import com.example.qrretrofit.interfaces.GenericService;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenericController {

    private GenericCallback genericCallback;
    private Retrofit retrofit;
    private GenericService genericService;

    private Callback<ResponseBody> createResponseBodyCallback(final GenericCallback genericCallback) {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Convert the successful response body to a string
                        String data = response.body().string();
                        genericCallback.success(data);
                    } catch (IOException e) {
                        // Handle the exception if reading the response fails
                        genericCallback.error("Error reading the response body: " + e.getMessage());
                    }
                } else {
                    try {
                        // If response is not successful, extract the error body
                        String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        genericCallback.error(error);
                    } catch (IOException e) {
                        // Handle any issues reading the error body
                        genericCallback.error("Error reading the error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                // Handle any errors that occur while making the request
                genericCallback.error("Network failure: " + throwable.getMessage());
            }
        };
    }


    Callback<Void> voidCallback =
            new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        genericCallback.success(response.message());
                    } else {
                        genericCallback.error(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    genericCallback.error(throwable.getMessage());
                }
            };

    public GenericController(String baseUrl, GenericCallback genericCallback) {
        this.genericCallback = genericCallback;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        genericService = retrofit.create(GenericService.class);
    }

    // New Methods for GenericService

    public void getAllDataImpl(String apiKey,final GenericCallback callback) {
        Call<ResponseBody> call = genericService.getAllData(apiKey);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void getDataByClientImpl(String apiKey, final GenericCallback callback) {
        Call<ResponseBody> call = genericService.getDataByClient(apiKey);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void deleteQrByIdImpl(String apiKey, Integer id, final GenericCallback callback) {
        Call<ResponseBody> call = genericService.deleteQrById(apiKey, id);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void deleteAllImpl(String apiKey,final GenericCallback callback) {
        Call<ResponseBody> call = genericService.deleteAll(apiKey);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void generateQRCodeImpl(String apiKey, Map<String, String> params,final GenericCallback callback) {
        Call<ResponseBody> call = genericService.generateQRCode(apiKey, params);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void updateQrByIdImpl(String apiKey, Map<String, String> params,final GenericCallback callback) {
        Call<ResponseBody> call = genericService.updateQrById(apiKey, params);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void scanQRCodeImpl(String apiKey, MultipartBody.Part file, final GenericCallback callback) {
        Call<ResponseBody> call = genericService.scanReadParams(apiKey, file);
        call.enqueue(createResponseBodyCallback(callback));
    }

    public void scanBarcodeImpl(String apiKey, MultipartBody.Part file, final GenericCallback callback) {
        Call<ResponseBody> call = genericService.scanBarcode(apiKey, file);
        call.enqueue(createResponseBodyCallback(callback));
    }
}