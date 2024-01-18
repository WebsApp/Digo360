package com.wapss.digo360.apiServices;

import com.wapss.digo360.response.AreaResponse;
import com.wapss.digo360.response.BannerResponse;
import com.wapss.digo360.response.CityResponse;
import com.wapss.digo360.response.Degree_Response;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.HelpResponse;
import com.wapss.digo360.response.LoginResponse;
import com.wapss.digo360.response.NotificationResponse;
import com.wapss.digo360.response.OTP_Response;
import com.wapss.digo360.response.PagesResponse;
import com.wapss.digo360.response.Patient_Check_Response;
import com.wapss.digo360.response.Profile_Response;
import com.wapss.digo360.response.RegistrationResponse;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.SettingHomeResponse;
import com.wapss.digo360.response.SpecializationResponse;
import com.wapss.digo360.response.StateResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHolder {
    @GET("settings/default")
    Call<SettingHomeResponse> homeAPi(@Header("Authorization") String Token);
    /*-----------------*/
    @POST("auth/doctor/mobile/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("loginId") String loginId,
                              @Field("deviceId") String deviceId);

    @POST("auth/verify")
    @FormUrlEncoded
    Call<OTP_Response> OTP_Verify(@Field("otp") String otp,
                                  @Field("loginId") String loginId);

    @GET("degree/list")
    Call<Degree_Response> getDegreeData(@Header("Authorization") String Token);

    @GET("degree-specialization/{degreeId}")
    Call<SpecializationResponse> getSpecData(@Path("degreeId") String degreeId,
                                 @Header("Authorization") String Token);

    @GET("state/list")
    Call<StateResponse> getStateData(@Header("Authorization") String Token);
    @GET("city/list/{stateId}")
    Call<CityResponse> getCityData(@Path("stateId") String id,
                                   @Header("Authorization") String Token);
    @GET("area/list/{cityId}")
    Call<AreaResponse> getAreaData(@Path("cityId") String id,
                                   @Header("Authorization") String Token);

    @POST("doctor-detail")
    @FormUrlEncoded
    Call<RegistrationResponse> Registration(@Header("Authorization") String Token,
                                            @Field("title") String title,
                                            @Field("name") String name,
                                            @Field("gender") String gender,
                                            @Field("dob") String dob,
                                            @Field("email") String email,
                                            @Field("experienceLevel") String experienceLevel,
                                            @Field("degreeId") String degreeId,
                                            @Field("studyYear") String studyYear,
                                            @Field("specializationId") String specializationId,
                                            @Field("address") String address,
                                            @Field("stateId") String stateId,
                                            @Field("cityId") String cityId,
                                            @Field("areaId") String areaId,
                                            @Field("pincode") String pincode,
                                            @Field("tnc") String tnc);


    @GET("faqs")
    Call<FaqResponse> helpAPi(@Header("Authorization") String Token);

    @GET("faqs/page")
    Call<FaqResponse> faqAPi(@Header("Authorization") String Token,
                             @Query("typeFor") String typeFor);

    @GET("Pages/{Id}")
    Call<PagesResponse> getPage(@Path("Id") String ID,
                                @Header("Authorization") String Token);

    @GET("doctor-detail/profile/doctor")
    Call<Profile_Response> get_profile(@Header("Authorization") String Token);

    @GET("patient-details/list")
    Call<Patient_Check_Response> Patient_check(@Header("Authorization") String Token,
                                                  @Query("limit") int limit,
                                                  @Query("offset") int offset,
                                                  @Query("keyword")String keyword);



    @GET("notifications")
    Call<NotificationResponse> notificationAPi(@Header("Authorization") String Token);

    @GET("search-history/top-ten")
    Call<TopDiseaseResponse> DiseaseAPi(@Header("Authorization") String Token,
                                     @Query("limit") int limit,
                                     @Query("offset") int offset);

    @GET("diseases/list")
    Call<SearchResponse> SearchADiseasePi(@Header("Authorization") String Token,
                                          @Query("limit") int limit,
                                          @Query("offset") int offset,
                                          @Query("keyword")String keyword);
}
