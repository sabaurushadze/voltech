plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
}

android {
    namespace = "com.tbc.home.data"
}

dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}
