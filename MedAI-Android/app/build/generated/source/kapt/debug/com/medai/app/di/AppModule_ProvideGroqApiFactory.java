package com.medai.app.di;

import com.medai.app.data.api.GroqApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AppModule_ProvideGroqApiFactory implements Factory<GroqApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideGroqApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GroqApiService get() {
    return provideGroqApi(retrofitProvider.get());
  }

  public static AppModule_ProvideGroqApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideGroqApiFactory(retrofitProvider);
  }

  public static GroqApiService provideGroqApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGroqApi(retrofit));
  }
}
