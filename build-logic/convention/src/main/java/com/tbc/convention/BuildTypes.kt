//package com.tbc.convention
//
//import com.android.build.api.dsl.ApplicationExtension
//import com.android.build.api.dsl.BuildType
//import com.android.build.api.dsl.CommonExtension
//import com.android.build.api.dsl.LibraryExtension
//import com.tbc.convention.AndroidProject.BASE_URL
//import org.gradle.api.Project
//import org.gradle.kotlin.dsl.configure
//
//internal fun Project.configureBuildTypes(
//    commonExtension: CommonExtension<*, *, *, *, *, *>,
//    extensionType: ExtensionType
//) {
//    commonExtension.run {
//        buildFeatures.buildConfig = true
//        when (extensionType) {
//            ExtensionType.APPLICATION -> {
//                extensions.configure<ApplicationExtension> {
//                    buildTypes {
//                        debug {
//                            configureDebugBuildType()
//                        }
//                        release {
//                            configureReleaseBuildType(commonExtension)
//
//                        }
//                    }
//                }
//            }
//
//            ExtensionType.LIBRARY -> {
//                extensions.configure<LibraryExtension> {
//                    buildTypes {
//                        debug {
//                            configureDebugBuildType()
//
//                        }
//                        release {
//                            configureReleaseBuildType(commonExtension)
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//enum class ExtensionType {
//    APPLICATION,
//    LIBRARY
//}
//
//
//private fun BuildType.configureDebugBuildType() {
//    buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
//}
//
//private fun BuildType.configureReleaseBuildType(
//    commonExtension: CommonExtension<*, *, *, *, *, *>
//) {
//    buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
//
//    isMinifyEnabled = true
//    proguardFiles(
//        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
//        "proguard-rules.pro"
//    )
//
//}