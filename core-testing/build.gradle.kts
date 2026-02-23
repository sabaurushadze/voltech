plugins {
    alias(libs.plugins.voltech.jvm.library)
    alias(libs.plugins.voltech.dagger.hilt)
}

dependencies{
    implementation(libs.kotlinx.coroutines.core)
}

dependencies{
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)}
