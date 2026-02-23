plugins {
    alias(libs.plugins.voltech.application.compose)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.firebase.crashlytics)
//    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.tbc.voltech"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)


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

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.serialization.json)

    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.work.compiler)
    implementation(libs.splash.screen)

    implementation(libs.accompanist.permissions)

    implementation(projects.feature.auth.data)
    implementation(projects.feature.auth.domain)
    implementation(projects.feature.auth.presentation)

    implementation(projects.feature.home.data)
    implementation(projects.feature.home.domain)
    implementation(projects.feature.home.presentation)

    implementation(projects.feature.search.data)
    implementation(projects.feature.search.domain)
    implementation(projects.feature.search.presentation)

    implementation(projects.feature.profile.data)
    implementation(projects.feature.profile.domain)
    implementation(projects.feature.profile.presentation)

    implementation(projects.feature.selling.data)
    implementation(projects.feature.selling.domain)
    implementation(projects.feature.selling.presentation)

    implementation(projects.core.presentation)
    implementation(projects.core.data)
    implementation(projects.core.domain)

    implementation(projects.coreUi)

    implementation(projects.resource)
    implementation(projects.coreTesting)
}