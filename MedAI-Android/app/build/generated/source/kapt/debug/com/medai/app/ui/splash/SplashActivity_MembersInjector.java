package com.medai.app.ui.splash;

import com.medai.app.utils.PrefsManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SplashActivity_MembersInjector implements MembersInjector<SplashActivity> {
  private final Provider<PrefsManager> prefsProvider;

  public SplashActivity_MembersInjector(Provider<PrefsManager> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<SplashActivity> create(Provider<PrefsManager> prefsProvider) {
    return new SplashActivity_MembersInjector(prefsProvider);
  }

  @Override
  public void injectMembers(SplashActivity instance) {
    injectPrefs(instance, prefsProvider.get());
  }

  @InjectedFieldSignature("com.medai.app.ui.splash.SplashActivity.prefs")
  public static void injectPrefs(SplashActivity instance, PrefsManager prefs) {
    instance.prefs = prefs;
  }
}
