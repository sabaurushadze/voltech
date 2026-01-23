plugins {
    alias(libs.plugins.voltech.android.library.compose)
}

android {
    namespace = "com.tbc.core.designsystem"
}

dependencies{
    implementation(libs.androidx.ui.text.google.fonts)
}