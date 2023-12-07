package com.wapss.digo360.apiServices;

import com.wapss.digo360.response.AreaResponse;
import com.wapss.digo360.response.BannerResponse;
import com.wapss.digo360.response.CityResponse;
import com.wapss.digo360.response.LoginResponse;
import com.wapss.digo360.response.OTP_Response;
import com.wapss.digo360.response.StateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHolder {
    @GET("banners")
    Call<BannerResponse> banner(@Query("token") String token);

    @POST("auth/doctor/mobile/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("loginId") String loginId,
                              @Field("deviceId") String deviceId);

    @POST("auth/verify")
    @FormUrlEncoded
    Call<OTP_Response> OTP_Verify(@Field("otp") String otp,
                                  @Field("loginId") String loginId);

    @GET("state/list")
    Call<StateResponse> getStateData(@Query("limit") int limit,
                                     @Query("offset") int offset,
                                     @Query("status") String status);

    @GET("city/list/{stateId}")
    Call<CityResponse> getCityData(@Path("stateId") String id,
                                   @Query("limit") int limit,
                                   @Query("offset") int offset,
                                   @Query("status") String status);

    @GET("area/list/{cityId}")
    Call<AreaResponse> getAreaData(@Path("cityId") String id,
                                   @Query("limit") int limit,
                                   @Query("offset") int offset,
                                   @Query("status") String status);
}
