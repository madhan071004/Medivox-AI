package com.medai.app.data.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.medai.app.data.api.GroqApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<GroqApiService> apiProvider;

  private final Provider<Context> contextProvider;

  private final Provider<Gson> gsonProvider;

  public ChatRepository_Factory(Provider<GroqApiService> apiProvider,
      Provider<Context> contextProvider, Provider<Gson> gsonProvider) {
    this.apiProvider = apiProvider;
    this.contextProvider = contextProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(apiProvider.get(), contextProvider.get(), gsonProvider.get());
  }

  public static ChatRepository_Factory create(Provider<GroqApiService> apiProvider,
      Provider<Context> contextProvider, Provider<Gson> gsonProvider) {
    return new ChatRepository_Factory(apiProvider, contextProvider, gsonProvider);
  }

  public static ChatRepository newInstance(GroqApiService api, Context context, Gson gson) {
    return new ChatRepository(api, context, gson);
  }
}
