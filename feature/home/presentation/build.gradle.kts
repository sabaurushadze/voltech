plugins {
    alias(libs.plugins.voltech.android.feature)
}

android {
    namespace = "com.tbc.home.presentation"
}

dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.core.domain)
    implementation(projects.feature.search.domain)

}