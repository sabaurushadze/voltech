plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.voltech.retrofit)
}

android {
    namespace = "com.tbc.search.data"
}

dependencies {
    implementation(projects.feature.search.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.paging)
}
