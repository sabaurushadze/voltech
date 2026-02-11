plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.voltech.retrofit)
}

android {
    namespace = "com.tbc.selling.data"
}

dependencies {
    implementation(projects.feature.selling.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}