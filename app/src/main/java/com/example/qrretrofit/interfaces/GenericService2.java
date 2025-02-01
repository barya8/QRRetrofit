package com.example.qrretrofit.interfaces;

import com.example.qrretrofit.model.ApiResponse;
import com.example.qrretrofit.model.ServiceResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GenericService2 {
    @GET("/api/firebase/getAllData")
    Call<ApiResponse> getAllData(@Header("x-api-key") String apiKey);

    @GET("/api/firebase/getDataByClient")
    Call<ApiResponse> getDataByClient(@Header("x-api-key") String apiKey);

    @DELETE("/api/firebase/deleteQrById")
    Call<ServiceResult> deleteQrById(@Header("x-api-key") String apiKey, @Query("id") Integer id);

    @DELETE("/api/firebase/deleteAll")
    Call<ServiceResult> deleteAll(@Header("x-api-key") String apiKey);

    @POST("/api/barcodes/generateQRCode")
    Call<ResponseBody> generateQRCode(@Header("x-api-key") String apiKey, @QueryMap Map<String, String> params);

    @POST("/api/barcodes/generateBarcode")
    Call<ResponseBody> generateBarcode(@Header("x-api-key") String apiKey, @QueryMap Map<String, String> params);

    @PUT("/api/barcodes/updateQrById")
    Call<ServiceResult> updateQrById(@Header("x-api-key") String apiKey, @Query("id") Integer id);

    @POST("/api/barcodes/read")
    Call<ServiceResult> scanQRCode(@Header("x-api-key") String apiKey, @Part MultipartBody.Part file);

    @POST("/api/barcodes/qrcode/chcek")
    Call<ServiceResult> scanBarcode(@Header("x-api-key") String apiKey, @Part MultipartBody.Part file);
}

