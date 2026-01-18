plugins {
    alias(libs.plugins.voltech.android.library)
    alias(libs.plugins.voltech.dagger.hilt)
    alias(libs.plugins.voltech.retrofit)
}

android{
    namespace = "com.tbc.data"

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"http://10.0.2.2:3000/\""
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"http://10.0.2.2:3000/\""
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies{
    implementation(projects.core.domain)
    implementation(libs.preferences.datastore)
    implementation(libs.firebase.auth)
}