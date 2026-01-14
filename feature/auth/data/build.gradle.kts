plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.voltech.retrofit)
}

android {
    namespace = "com.tbc.auth.data"
}

dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.androidx.work.runtime.ktx)

}

dependencies {
    implementation(projects.feature.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}