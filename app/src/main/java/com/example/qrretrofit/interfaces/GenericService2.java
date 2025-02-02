package com.example.qrretrofit.interfaces;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GenericService2 {
    @GET("/api/firebase/getAllData")
    Call<ResponseBody> getAllData(@Header("x-api-key") String apiKey);

    @GET("/api/firebase/getDataByClient")
    Call<ResponseBody> getDataByClient(@Header("x-api-key") String apiKey);

    @DELETE("/api/firebase/deleteQrById")
    Call<ResponseBody> deleteQrById(@Header("x-api-key") String apiKey, @Query("id") Integer id);

    @DELETE("/api/firebase/deleteAll")
    Call<ResponseBody> deleteAll(@Header("x-api-key") String apiKey);

    @POST("/api/barcodes/generateQRCode")
    Call<ResponseBody> generateQRCode(@Header("x-api-key") String apiKey, @QueryMap Map<String, String> params);

    @POST("/api/barcodes/generateBarcode")
    Call<ResponseBody> generateBarcode(@Header("x-api-key") String apiKey, @QueryMap Map<String, String> params);

    @PUT("/api/barcodes/updateQrById")
    Call<ResponseBody> updateQrById(@Header("x-api-key") String apiKey,  @QueryMap Map<String, String> params);
    @Multipart
    @POST("/api/barcodes/read")
    Call<ResponseBody> scanReadParams(@Header("x-api-key") String apiKey, @Part MultipartBody.Part file);

    @Multipart
    @POST("/api/barcodes/qrcode/check")
    Call<ResponseBody> scanBarcode(@Header("x-api-key") String apiKey, @Part MultipartBody.Part file);
}

