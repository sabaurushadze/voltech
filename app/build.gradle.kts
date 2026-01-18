plugins {
    alias(libs.plugins.voltech.application.compose)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.tbc.voltech"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.credentials)

    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.kotlinx.coroutines.test)

    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // serialization
    implementation(libs.serialization.json)

    //worker
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.work.compiler)
    implementation(libs.splash.screen)

    implementation(libs.accompanist.permissions)

    implementation(projects.feature.auth.data)
    implementation(projects.feature.auth.domain)
    implementation(projects.feature.auth.presentation)

    implementation(projects.core.designsystem)
    implementation(projects.core.presentation)
    implementation(projects.core.data)
    implementation(projects.core.domain)
}