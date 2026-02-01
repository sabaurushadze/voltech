plugins {
    alias(libs.plugins.voltech.android.feature)
}

android {
    namespace = "com.tbc.profile.presentation"
}

dependencies {
    implementation(projects.feature.profile.domain)
    implementation(projects.core.domain)
}