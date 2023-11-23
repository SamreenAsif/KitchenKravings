package com.example.recipebook.di;

import com.example.recipebook.data.AuthRepository;
import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvidesRepositoryImplFactory implements Factory<AuthRepository> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public AppModule_ProvidesRepositoryImplFactory(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public AuthRepository get() {
    return providesRepositoryImpl(firebaseAuthProvider.get());
  }

  public static AppModule_ProvidesRepositoryImplFactory create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new AppModule_ProvidesRepositoryImplFactory(firebaseAuthProvider);
  }

  public static AuthRepository providesRepositoryImpl(FirebaseAuth firebaseAuth) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providesRepositoryImpl(firebaseAuth));
  }
}
