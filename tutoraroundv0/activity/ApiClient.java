package com.example.panchal.tutoraroundv0.activity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiClient {


    @GET("test.php")
    Call<ResponseBody> test();

    @GET("university_list")
    Call<ResponseBody> getUniversityList();

    @GET("course_list")
    Call<ResponseBody> getCourseList(@Query("uid")  String uid);
    /* type = 1 for student
     type =  2 for tutor */

    @FormUrlEncoded
    @POST("student_register")
    Call<ResponseBody> register(@Field("fname") String fname,
                                @Field("lname") String lname,
                                @Field("email") String email,
                                @Field("aemail") String aemail,
                                @Field("uid") String uid,
                                @Field("type") String type,
                                @Field("password") String password,
                                @Field("referalcode") String referalCode);

    @FormUrlEncoded
    @POST("bank_register_detail")
    Call<ResponseBody> addBankDetails(@Field("user_id") String user_id,
                                      @Field("bank_name") String bank_name,
                                      @Field("bank_id") String bank_id,
                                      @Field("account_holder") String account_holder,
                                      @Field("account_number") String account_number,
                                      @Field("branch_number") String branch_number,
                                      @Field("login_user_id") String login_user_id,
                                      @Field("token") String token
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("email") String email,
                             @Field("password") String password,
                             @Field("token") String token,
                             @Field("type") String type
    );


    //Add Course
    @FormUrlEncoded
    @POST("course_register_detail")
    Call<ResponseBody> addCourseDetails(@Field("user_id") String user_id,
                                        @Field("data") String data
    );

    //forgot password
    @FormUrlEncoded
    @POST("forgot_password")
    Call<ResponseBody> forgotPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST("verify_code")
    Call<ResponseBody> verifyCode(@Field("email") String email,
                                  @Field("rs_code") String rs_code
    );

    @FormUrlEncoded
    @POST("reset_password")
    Call<ResponseBody> resetPassword(@Field("user_id") String user_id,
                                     @Field("new_password") String new_password
    );

    @FormUrlEncoded
    @POST("edit_tutor_profile")
    Call<ResponseBody> viewProfile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("update_tutor")
    Call<ResponseBody> updateTutorProfile(@Field("user_id") String user_id,
                                          @Field("fname") String fname,
                                          @Field("lname") String lname,
                                          @Field("data") String data,
                                          @Field("bank_name") String bank_name,
                                          @Field("bank_id") String bank_id,
                                          @Field("account_holder") String account_holder,
                                          @Field("account_number") String account_number,
                                          @Field("branch_number") String branch_number,
                                          @Field("login_user_id") String login_user_id,
                                          @Field("token") String token

    );

    @FormUrlEncoded
    @POST("ads_post")
    Call<ResponseBody> addPost(@Field("user_fk") String user_fk,
                               @Field("course_fk") String course_fk,
                               @Field("ccode") String ccode,
                               @Field("date") String date,
                               @Field("time") String time,
                               @Field("duration") String duration,
                               @Field("price") String price,
                               @Field("description") String description);

    @FormUrlEncoded
    @POST("update_post")
    Call<ResponseBody> updatePost(@Field("id") String id,
                                  @Field("user_fk") String user_fk,
                                  @Field("course_fk") String course_fk,
                                  @Field("ccode") String ccode,
                                  @Field("date") String date,
                                  @Field("time") String time,
                                  @Field("duration") String duration,
                                  @Field("price") String price,
                                  @Field("description") String description,
                                  @Field("login_user_id") String login_user_id,
                                  @Field("token") String token);

    //My Post List
    @GET("list_ads_post")
    Call<ResponseBody> getActivePost(@Query("user_id") String user_id);

    //MY History
    @GET("history_ads_post")
    Call<ResponseBody> getHistoryPost(@Query("user_id") String user_id);

    //Avail Oppor
    @GET("available_opportunities")
    Call<ResponseBody> getAvailablePost(@Query("user_id") String user_id);

    //My Tutorial Schedule
    @GET("mytutorial_schedule")
    Call<ResponseBody> getActiveTutoringSchedule(@Query("user_id") String user_id);

    //My History Schedule
    @GET("mytutorial_history")
    Call<ResponseBody> getHistoryTutoringSchedule(@Query("user_id") String user_id);

    //
    @GET("pending_opportunities")
    Call<ResponseBody> getPendingOpportunities(@Query("user_id") String user_id);

    //post details  id = postid
    @GET("post_details")
    Call<ResponseBody> getPostDetailTutorList(@Query("id") String id,
                                              @Query("user_fk") String user_id);

    @GET("offerSubmit")
    Call<ResponseBody> counterOfferCall(@Query("id") String id,
                                        @Query("course_fk") String course_fk,
                                        @Query("user_fk") String user_fk,
                                        @Query("offer_price") String offer_price);

    @GET("offer_cancel")
    Call<ResponseBody> cancelOfferCall(@Query("id") String id,
                                       @Query("user_id") String user_id,
                                       @Query("course_fk") String course_fk,
                                       @Query("login_user_id") String login_user_id,
                                       @Query("token") String token);

    @GET("accept_tutor")
    Call<ResponseBody> acceptOfferCall(@Query("id") String id,
                                       @Query("user_id") String user_id,
                                       @Query("tutor_id") String tutor_id);

    @GET("decline_tutor")
    Call<ResponseBody> declineOfferCall(@Query("id") String id,
                                        @Query("user_id") String user_id,
                                        @Query("tutor_id") String tutor_id);

    @GET("reschedule_update")
    Call<ResponseBody> rescheduleCall(@Query("id") String id,
                                      @Query("user_id") String user_id,
                                      @Query("date") String date,
                                      @Query("time") String time,
                                      @Query("duration") String duration,
                                      @Query("login_user_id") String login_user_id,
                                      @Query("token") String token);

    @GET("refreshToken")
    Call<ResponseBody> tokenRefreshCall(@Query("user_id") String user_id,
                                        @Query("token") String token,
                                        @Query("type") String type);

    @GET("reviews_list")
    Call<ResponseBody> getReviewsList(@Query("user_id") String user_id,
                                      @Query("login_user_id") String login_user_id,
                                      @Query("token") String token);

    @GET("wallet_history_earning")
    Call<ResponseBody> getEarningWallet(@Query("user_id") String user_id,
                                        @Query("login_user_id") String login_user_id,
                                        @Query("token") String token);

    @GET("WithDrawal")
    Call<ResponseBody> walletWithdrawal(@Query("user_id") String user_id,
                                        @Query("withdrawal_wallet") String withdrawal_wallet,
                                        @Query("withdrawal_type") String withdrawal_type,
                                        @Query("login_user_id") String login_user_id,
                                        @Query("token") String token);

    @GET("notification_list")
    Call<ResponseBody> getNotificationList(@Query("user_id") String user_id);

    @GET("wallet_history_spending")
    Call<ResponseBody> getSpendingWallet(@Query("user_id") String user_id,
                                         @Query("login_user_id") String login_user_id,
                                         @Query("token") String token);

    @GET("logout")
    Call<ResponseBody> logoutCall(@Query("user_id") String user_id,
                                  @Query("token") String token);

    @GET("message_all_new")
    Call<ResponseBody> getMessageUserList(@Query("user_id") String user_id,
                                          @Query("login_user_id") String login_user_id,
                                          @Query("token") String token);

    @GET("message_list_new")
    Call<ResponseBody> getMessageList(@Query("user_id") String user_id,
                                      @Query("post_id") String post_id,
                                      @Query("login_user_id") String login_user_id,
                                      @Query("token") String token);

    @GET("message_send_new")
    Call<ResponseBody> sendMessage(@Query("sender_id") String sender_id,
                                   @Query("receiver_id") String receiver_id,
                                   @Query("message") String message,
                                   @Query("post_id") String post_id,
                                   @Query("login_user_id") String login_user_id,
                                   @Query("token") String token);

    @GET("schedule_details")
    Call<ResponseBody> getScheduleDetails(@Query("post_id") String post_id,
                                          @Query("user_id") String user_id,
                                          @Query("login_user_id") String login_user_id,
                                          @Query("token") String token);

    @GET("session_check_in")
    Call<ResponseBody> startSession(@Query("id") String id,
                                     @Query("sender_id") String sender_id,
                                    @Query("received_id") String received_id);

    @GET("session_check_out")
    Call<ResponseBody> stopSession(@Query("id") String id,
                                   @Query("sender_id") String sender_id,
                                   @Query("received_id") String received_id);

    @GET("session_check_noshow")
    Call<ResponseBody> noShowSession(@Query("id") String id,
                                     @Query("sender_id") String sender_id,
                                      @Query("received_id") String received_id);

    @GET("reviews_submit")
    Call<ResponseBody> submitReview(@Query("sender_id") String sender_id,
                                    @Query("receiver_id") String receiver_id,
                                    @Query("post_id") String post_id,
                                    @Query("message") String message,
                                    @Query("rating") String rating);

    @GET("referal_earn")
    Call<ResponseBody> referalEarn(@Query("user_id") String user_id,
                                   @Query("login_user_id") String login_user_id,
                                   @Query("token") String token);

    // frequently asked question
    @GET("faq_list")
    Call<ResponseBody> faqList(@Query("user_type") String user_type);


    @GET("accept_reschedule")
    Call<ResponseBody> acceptRescheduleCall(@Query("id") String id,
                                            @Query("user_id") String user_id,
                                            @Query("login_user_id") String login_user_id,
                                            @Query("token") String token);

    @GET("rejected_reschedule")
    Call<ResponseBody> rejectRejectCall(@Query("id") String id,
                                        @Query("user_id") String user_id,
                                        @Query("login_user_id") String login_user_id,
                                        @Query("token") String token);


    @GET("switch_user")
    Call<ResponseBody> switchUser(@Query("user_id") String user_id);

    //change pass
    @GET("change_password")
    Call<ResponseBody> changePassword(@Query("user_id") String user_id,
                                      @Query("old_password") String old_password,
                                      @Query("new_password") String new_password);


    @GET("count_noti")
    Call<ResponseBody> getNotificationCount(@Query("user_id") String user_id);

    @GET("checkinnot")
    Call<ResponseBody> getNoShowStatus(@Query("post_id") String post_id,
                                       @Query("sender_id") String sender_id,
                                       @Query("received_id") String received_id,
                                        @Query("login_user_id") String login_user_id,
                                       @Query("token") String token);

    @GET("textbooking_list")
    Call<ResponseBody> getBookList(@Query("user_id") String user_id,
                                   @Query("sell_buy") String sell_buy,
                                   @Query("cate_id") String cate_id,
                                   @Query("login_user_id") String login_user_id,
                                   @Query("token") String token);

    @GET("textmessage_all")
    Call<ResponseBody> getBookMessageList(@Query("user_id") String user_id,
                                          @Query("login_user_id") String login_user_id,
                                          @Query("token") String token);

    @GET("textmessage_send")
    Call<ResponseBody> bookSendMessage(@Query("sender_id") String sender_id,
                                       @Query("receiver_id") String receiver_id,
                                       @Query("post_id") String postID,
                                       @Query("message") String message,
                                       @Query("login_user_id") String login_user_id,
                                       @Query("token") String token);

    @FormUrlEncoded
    @POST("textbooking_add")
    Call<ResponseBody> addBookPost(@Field("user_id") String user_id,
                                   @Field("course_id") String course_id,
                                   @Field("course_code") String course_code,
                                   @Field("price") String price,
                                   @Field("date") String date,
                                   @Field("description") String description,
                                   @Field("course_temp") String courseName,
                                   @Field("cate_id") String categoryID,
                                   @Query("login_user_id") String login_user_id,
                                   @Query("token") String token);


    @GET("textmessage_list")
    Call<ResponseBody> getBookMessageList(@Query("sender_id") String sender_id,
                                          @Query("receiver_id") String receiver_id,
                                          @Query("post_id") String postID,
                                          @Query("login_user_id") String login_user_id,
                                          @Query("token") String token);

    @GET("notification_count")
    Call<ResponseBody> getInboxCount(@Query("user_id") String user_id,
                                     @Query("login_user_id") String login_user_id,
                                     @Query("token") String token);

    @GET("notification_update")
    Call<ResponseBody> readUnreadNotification(@Query("user_id") String user_id,
                                              @Query("notification_id") String notification_id,
                                              @Query("login_user_id") String login_user_id,
                                              @Query("token") String token);

    @GET("getSessionComplete")
    Call<ResponseBody> getActiveSessionStatus(@Query("user_id") String user_id,
                                              @Query("login_user_id") String login_user_id,
                                              @Query("token") String token);

    @GET("deletedBookPost")
    Call<ResponseBody> deleteBookPost(@Query("id") String id,
                                      @Query("login_user_id") String login_user_id,
                                      @Query("token") String token);

    @GET("NotificationDelete")
    Call<ResponseBody> notificationDelete(@Query("id") String id,
                                          @Query("user_id") String user_id,
                                          @Query("login_user_id") String login_user_id,
                                          @Query("token") String token);

    @Multipart
    @POST("get_images")
    Call<ResponseBody> uploadImage(@Part("id") RequestBody id,
                                   @Part("user_id") RequestBody user_id,
                                   @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("updateBookingPost")
    Call<ResponseBody> updateBookPost(@Field("id") String id,
                                      @Field("user_id") String user_id,
                                      @Field("course_id") String course_id,
                                      @Field("course_code") String course_code,
                                      @Field("price") String price,
                                      @Field("date") String date,
                                      @Field("description") String description,
                                      @Field("course_temp") String courseName,
                                      @Field("images") String deleteImages,
                                      @Field("cate_id") String categoryID,
                                      @Field("login_user_id") String login_user_id,
                                      @Field("token") String token);


    @GET("StopGeneralNoti")
    Call<ResponseBody> switchGeneralNotification(@Query("type") String type,
                                                 @Query("user_id") String user_id,
                                                 @Query("login_user_id") String login_user_id,
                                                 @Query("token") String token);

    @GET("StopTextBookNoti")
    Call<ResponseBody> switchBookNotification(@Query("type") String type,
                                              @Query("user_id") String user_id,
                                              @Query("login_user_id") String login_user_id,
                                              @Query("token") String token);

    @GET("UserWiseNotiDelete")
    Call<ResponseBody> deleteAllNotification(@Query("user_id") String user_id,
                                             @Query("login_user_id") String login_user_id,
                                             @Query("token") String token);

    @GET("payment_from_wallet")
    Call<ResponseBody> payByWalletOnly(@Query("user_id") String user_id,
                                       @Query("pirce") String price,
                                       @Query("id") String id,
                                       @Query("tutor_id") String tutor_id,
                                       @Query("login_user_id") String login_user_id,
                                       @Query("token") String token);

    @GET("pending_opportunities_new")
    Call<ResponseBody> getHistoryPendingOpportunities(@Query("user_id") String user_id,
                                                      @Query("login_user_id") String login_user_id,
                                                      @Query("token") String token);

    @GET("notification_setting")
    Call<ResponseBody> getNotificationSetting(@Query("user_id") String user_id,
                                              @Query("login_user_id") String login_user_id,
                                              @Query("token") String token);

    @GET("email_list")
    Call<ResponseBody> email_list();

    @GET("textbookCategoryList")
    Call<ResponseBody> getTextbookCategoryList(@Query("login_user_id") String login_user_id,
                                               @Query("token") String token);

    @GET("submit_contact_us")
    Call<ResponseBody> submitContactUs(@Query("user_id") String user_id,
                                       @Query("subject") String subject,
                                       @Query("message") String message,
                                       @Query("login_user_id") String login_user_id,
                                       @Query("token") String token);

    @GET("user_review")
    Call<ResponseBody> getTutorsReviewList(@Query("user_id") String user_id,
                                           @Query("login_user_id") String login_user_id,
                                           @Query("token") String token);

    @FormUrlEncoded
    @POST("payment_by_moneris")
    Call<ResponseBody> paymentMonerise(@Field("user_id") String user_id,
                                       @Field("course_fk") String course_fk,
                                       @Field("ccode") String ccode,
                                       @Field("price") String price,
                                       @Field("date") String date,
                                       @Field("time") String time,
                                       @Field("duration") String duration,
                                       @Field("user_type") String user_type,
                                       @Field("wallet") String wallet,
                                       @Field("pamount") String pamount,
                                       @Field("card_no") String card_no,
                                       @Field("expiry_date") String expiry_date,
                                       @Field("login_user_id") String login_user_id,
                                       @Field("token") String token);

}
