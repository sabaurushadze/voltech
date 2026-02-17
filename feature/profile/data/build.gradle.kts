plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.voltech.retrofit)
}

android {
    namespace = "com.tbc.profile.data"
}

dependencies {
    implementation(projects.feature.profile.domain)

    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.exifinterface)
}
