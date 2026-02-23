plugins {
    alias(libs.plugins.voltech.android.feature)
}

android {
    namespace = "com.tbc.auth.presentation"
}

dependencies {
    implementation(projects.feature.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.feature.profile.domain)
    implementation(projects.coreTesting)
}