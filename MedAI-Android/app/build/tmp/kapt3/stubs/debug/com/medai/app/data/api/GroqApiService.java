package com.medai.app.data.api;

import com.medai.app.data.model.GroqRequest;
import com.medai.app.data.model.GroqResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/medai/app/data/api/GroqApiService;", "", "chat", "Lretrofit2/Response;", "Lcom/medai/app/data/model/GroqResponse;", "authHeader", "", "request", "Lcom/medai/app/data/model/GroqRequest;", "(Ljava/lang/String;Lcom/medai/app/data/model/GroqRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "chatStream", "Lokhttp3/ResponseBody;", "validateKey", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface GroqApiService {
    
    @retrofit2.http.POST(value = "openai/v1/chat/completions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object chat(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authHeader, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.medai.app.data.model.GroqRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.medai.app.data.model.GroqResponse>> $completion);
    
    @retrofit2.http.Streaming()
    @retrofit2.http.POST(value = "openai/v1/chat/completions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object chatStream(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authHeader, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.medai.app.data.model.GroqRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<okhttp3.ResponseBody>> $completion);
    
    @retrofit2.http.GET(value = "openai/v1/models")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object validateKey(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authHeader, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<okhttp3.ResponseBody>> $completion);
}