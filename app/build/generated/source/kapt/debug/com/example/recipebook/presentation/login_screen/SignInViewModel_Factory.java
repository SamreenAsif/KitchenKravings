package com.example.recipebook.presentation.login_screen;

import com.example.recipebook.data.AuthRepository;
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
    "rawtypes"
})
public final class SignInViewModel_Factory implements Factory<SignInViewModel> {
  private final Provider<AuthRepository> repositoryProvider;

  public SignInViewModel_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignInViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignInViewModel_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new SignInViewModel_Factory(repositoryProvider);
  }

  public static SignInViewModel newInstance(AuthRepository repository) {
    return new SignInViewModel(repository);
  }
}
