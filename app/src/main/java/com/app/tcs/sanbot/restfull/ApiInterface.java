package com.app.tcs.sanbot.restfull;

import com.app.tcs.sanbot.appconstant.AppConstant;
import com.app.tcs.sanbot.bean.BotMsgResponse;
import com.app.tcs.sanbot.bean.ConversationRequest;
import com.app.tcs.sanbot.bean.GenerateToken;
import com.app.tcs.sanbot.bean.SendConversationResponse;
import com.app.tcs.sanbot.bean.StartConversation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface ApiInterface {

    @POST(AppConstant.GENERATE_TOKEN)
    Call<GenerateToken> getBotToken(@Header("Authorization") String authorization);

    @POST(AppConstant.START_CONVERSATION)
    Call<StartConversation> startConversation(@Header("Authorization") String authorization);

    @POST(AppConstant.CONVERSATION)
    Call<SendConversationResponse> sendMessage(@Path("conversation_id") String conversationId,
                                               @Header("Authorization") String authorization,
                                               @Body ConversationRequest conversationRequest);

    @GET(AppConstant.CONVERSATION)
    Call<BotMsgResponse> getConversationMessage(@Path("conversation_id") String conversationId,
                                                @Header("Authorization") String authorization);

    @GET
    Call<String> callINet(@Url String url);


}
