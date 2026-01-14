plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.voltech.retrofit)
}

android{
    namespace = "com.tbc.data"
}

dependencies{
    implementation(projects.core.domain)
    implementation(libs.preferences.datastore)
    implementation(libs.firebase.auth)
}