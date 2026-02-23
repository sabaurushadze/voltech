plugins {
    alias(libs.plugins.voltech.android.feature)
}

android {
    namespace = "com.tbc.profile.presentation"

}

dependencies {
    implementation(projects.feature.profile.domain)
    implementation(projects.core.domain)

    implementation(projects.feature.search.domain)
    implementation(projects.feature.selling.domain)
    implementation(projects.coreTesting)
}