package com.example.recipebook.di;

import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvidesFirebaseAuthFactory implements Factory<FirebaseAuth> {
  @Override
  public FirebaseAuth get() {
    return providesFirebaseAuth();
  }

  public static AppModule_ProvidesFirebaseAuthFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAuth providesFirebaseAuth() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providesFirebaseAuth());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvidesFirebaseAuthFactory INSTANCE = new AppModule_ProvidesFirebaseAuthFactory();
  }
}
