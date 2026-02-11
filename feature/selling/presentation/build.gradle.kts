plugins {
    alias(libs.plugins.voltech.android.feature)
}
android {
    namespace = "com.tbc.selling.presentation"
}

dependencies {
    implementation(projects.feature.selling.domain)
    implementation(projects.core.domain)

    implementation(projects.feature.search.domain)
    implementation(projects.feature.profile.domain)
    implementation(libs.androidx.compose.foundation.layout)
}