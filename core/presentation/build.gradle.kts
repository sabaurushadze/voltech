plugins {
    alias(libs.plugins.voltech.android.library.compose)
}

android {
    namespace = "com.tbc.presentation"
}

dependencies {
    implementation(projects.core.domain)
}