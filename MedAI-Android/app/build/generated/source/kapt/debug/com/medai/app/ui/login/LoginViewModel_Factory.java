package com.medai.app.ui.login;

import com.medai.app.data.repository.ChatRepository;
import com.medai.app.utils.PrefsManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<ChatRepository> repoProvider;

  private final Provider<PrefsManager> prefsProvider;

  public LoginViewModel_Factory(Provider<ChatRepository> repoProvider,
      Provider<PrefsManager> prefsProvider) {
    this.repoProvider = repoProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(repoProvider.get(), prefsProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<ChatRepository> repoProvider,
      Provider<PrefsManager> prefsProvider) {
    return new LoginViewModel_Factory(repoProvider, prefsProvider);
  }

  public static LoginViewModel newInstance(ChatRepository repo, PrefsManager prefs) {
    return new LoginViewModel(repo, prefs);
  }
}
