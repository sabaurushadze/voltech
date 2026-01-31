plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
}

android {
    namespace = "com.tbc.profile.data"
}

dependencies {
    implementation(projects.feature.profile.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}
