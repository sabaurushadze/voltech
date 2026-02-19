plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
//    id("com.google.firebase.crashlytics") version "3.0.6" apply false
    alias(libs.plugins.firebase.crashlytics) apply false

}