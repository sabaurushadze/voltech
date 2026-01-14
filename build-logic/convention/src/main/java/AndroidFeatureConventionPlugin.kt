import com.tbc.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.tbc.android.library.compose")
            apply(plugin = "com.tbc.dagger.hilt")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")


            dependencies {
                add("implementation",libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation",libs.findLibrary("androidx.navigation.compose").get())
                add("implementation",libs.findLibrary("serialization.json").get())

                add("implementation",project(":core:presentation"))
                add("implementation",project(":core:designsystem"))
            }
        }
    }
}