plugins {
    alias(libs.plugins.voltech.jvm.library)
    alias(libs.plugins.voltech.dagger.hilt)
}

dependencies{
    implementation(libs.kotlinx.coroutines.core)
}

dependencies{
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)
    implementation(projects.core.domain)
    implementation(projects.feature.search.domain)
    implementation(projects.feature.selling.domain)
}
