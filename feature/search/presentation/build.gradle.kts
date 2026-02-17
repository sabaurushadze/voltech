plugins {
    alias(libs.plugins.voltech.android.feature)
}

android {
    namespace = "com.tbc.search.presentation"
}

dependencies {
    implementation(projects.feature.search.domain)
    implementation(projects.core.domain)
    implementation(libs.androidx.paging.compose)
    implementation(libs.zoomable.image.coil)
}