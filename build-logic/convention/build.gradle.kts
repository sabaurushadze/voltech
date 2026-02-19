plugins {
    `kotlin-dsl`
}

group = "com.tbc.buildLogic"

dependencies {
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.tbc.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "com.tbc.android.compose.application"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.tbc.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.tbc.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("daggerHilt") {
            id = "com.tbc.dagger.hilt"
            implementationClass = "DaggerHiltConventionPlugin"
        }

        register("androidFeature") {
            id = "com.tbc.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "com.tbc.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("retrofit"){
            id = "com.tbc.retrofit"
            implementationClass = "RetrofitConventionPlugin"
        }
    }
}