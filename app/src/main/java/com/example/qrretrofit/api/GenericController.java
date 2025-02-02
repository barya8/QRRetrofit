package com.example.qrretrofit.api;

import android.util.Log;

import com.example.qrretrofit.interfaces.GenericCallback;
import com.example.qrretrofit.interfaces.GenericService;
import com.example.qrretrofit.interfaces.GenericService2;
import com.example.qrretrofit.model.Item;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
    private GenericService2 genericService2;

    Callback<ResponseBody> responseBodyCallback = new Callback<ResponseBody>() {
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
                    throw new RuntimeException(e);
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
        genericService2 = retrofit.create(GenericService2.class);
    }


    public void getAllObjectsImpl() {
        Call<ResponseBody> call = genericService.getAllObjects();
        call.enqueue(responseBodyCallback);

    }

    public void getSingleObjectImpl() {
        Call<ResponseBody> call = genericService.getSingleObject("4");
        call.enqueue(responseBodyCallback);

    }

    public void getPluralObjectImpl() {
        Call<ResponseBody> call = genericService.getPluralObject(Arrays.asList("3", "5", "10"));
        call.enqueue(responseBodyCallback);

    }

    public void createItemImpl() {
        Map<String, Object> data = new HashMap<>();
        data.put("year", 2019);
        data.put("price", 1849.99);
        data.put("CPU model", "Intel Core i9");
        data.put("Hard disk size", "1 TB");
        Item item = new Item();
        item.setName("Apple MacBook Pro 16")
                .setData(data);

        Call<Void> call = genericService.createItem(item);
        call.enqueue(voidCallback);

    }

    // New Methods for GenericService2

    public void getAllDataImpl(String apiKey) {
        Call<ResponseBody> call = genericService2.getAllData(apiKey);
        call.enqueue(responseBodyCallback);
    }

    public void getDataByClientImpl(String apiKey) {
        Call<ResponseBody> call = genericService2.getDataByClient(apiKey);
        call.enqueue(responseBodyCallback);
    }

    public void deleteQrByIdImpl(String apiKey, Integer id, GenericCallback callback) {
        Call<ResponseBody> call = genericService2.deleteQrById(apiKey, id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.success(response.toString());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        callback.error("Server error: " + response.code() + " - " + errorBody);
                    } catch (IOException e) {
                        callback.error("Server error: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.error("Network error: " + t.getMessage()); // Call onError for network errors
            }
        });
    }

    public void deleteAllImpl(String apiKey) {
        Call<ResponseBody> call = genericService2.deleteAll(apiKey);
        call.enqueue(responseBodyCallback);
    }

    public void generateQRCodeImpl(String apiKey, Map<String, String> params) {
        Call<ResponseBody> call = genericService2.generateQRCode(apiKey, params);
        call.enqueue(responseBodyCallback);
    }

    public void generateBarcodeImpl(String apiKey, Map<String, String> params) {
        Call<ResponseBody> call = genericService2.generateBarcode(apiKey, params);
        call.enqueue(responseBodyCallback);
    }

    public void updateQrByIdImpl(String apiKey, Map<String, String> params) {
        Call<ResponseBody> call = genericService2.updateQrById(apiKey, params);
        call.enqueue(responseBodyCallback);
    }

    public void scanQRCodeImpl(String apiKey, MultipartBody.Part file) {
        Call<ResponseBody> call = genericService2.scanReadParams(apiKey, file);
        call.enqueue(responseBodyCallback);
    }

    public void scanBarcodeImpl(String apiKey, MultipartBody.Part file) {
        Call<ResponseBody> call = genericService2.scanBarcode(apiKey, file);
        call.enqueue(responseBodyCallback);
    }

    // General method to handle API call for both barcode and QR code
    public void scanBarcodeOrQRCode(String apiKey, MultipartBody.Part file, boolean isValid, final GenericCallback callback) {
        if (isValid) {
            // First, call the barcode scan if isValid is true
            Call<ResponseBody> call = genericService2.scanBarcode(apiKey, file);

            // Enqueue the call for async handling
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Log for valid barcode scan success
                        Log.d("API CALL", "Barcode scan successful: " + response.body().toString());

                        // Barcode scan was successful, now proceed to code in table
                        callback.success(response.body().toString());

                        // Now do the QR scan (using the same file)
                        proceedWithQRCode(apiKey, file, callback); // QR code is scanned after barcode is successful

                    } else {
                        // Log for barcode scan failure
                        Log.d("API CALL", "Barcode scan failed: " + response.code());
                        callback.error("Request failed with status code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Log for network failure
                    Log.e("API CALL", "Error during barcode scan: " + t.getMessage());
                    callback.error("Error: " + t.getMessage());
                }
            });
        }
    }

    // Method to handle QR code scanning after barcode scan is successful
    private void proceedWithQRCode(String apiKey, MultipartBody.Part file, final GenericCallback callback) {
        Call<ResponseBody> qrCall = genericService2.scanReadParams(apiKey, file);

        qrCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Log for QR code scan success
                    Log.d("API CALL", "QR Code scan successful: " + response.body().toString());
                    callback.success("QR Code Scan Successful: " + response.body().toString());
                } else {
                    // Log for QR code scan failure
                    Log.d("API CALL", "QR Code scan failed: " + response.code());
                    callback.error("QR Code scan failed with status code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log for network failure
                Log.e("API CALL", "Error during QR Code scan: " + t.getMessage());
                callback.error("QR Code Scan Error: " + t.getMessage());
            }
        });
    }

}