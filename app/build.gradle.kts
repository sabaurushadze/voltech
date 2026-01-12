plugins {
    alias(libs.plugins.voltech.android.application)
    alias(libs.plugins.voltech.android.application.compose)
    alias(libs.plugins.voltech.android.application.firebase)
    alias(libs.plugins.voltech.hilt)
    alias(libs.plugins.google.osslicenses)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.tbc.voltech"

    defaultConfig {
        applicationId = "com.tbc.voltech"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
//    implementation(projects.feature.interests.api)
//    implementation(projects.feature.interests.impl)
//    implementation(projects.feature.foryou.api)
//    implementation(projects.feature.foryou.impl)
//    implementation(projects.feature.bookmarks.api)
//    implementation(projects.feature.bookmarks.impl)
//    implementation(projects.feature.topic.api)
//    implementation(projects.feature.topic.impl)
//    implementation(projects.feature.search.api)
//    implementation(projects.feature.search.impl)
//    implementation(projects.feature.settings.impl)
//
//    implementation(projects.core.common)
//    implementation(projects.core.ui)
//    implementation(projects.core.designsystem)
//    implementation(projects.core.data)
//    implementation(projects.core.model)
//    implementation(projects.core.analytics)
//    implementation(projects.sync.work)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.adaptive.navigation3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.window.core)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.serialization.json)

    ksp(libs.hilt.compiler)
}