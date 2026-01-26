plugins {
    alias(libs.plugins.voltech.android.library.compose)
}

android {
    namespace = "com.tbc.core.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(libs.coil.compose)
}