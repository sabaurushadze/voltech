import com.tbc.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class DaggerHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                add("ksp",libs.findLibrary("hilt.compiler").get())
            }


            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                dependencies {
                    add("implementation",libs.findLibrary("hilt.core").get())
                }
            }


            pluginManager.withPlugin("com.android.base") {
                apply(plugin = "dagger.hilt.android.plugin")
                dependencies {
                    add("implementation",libs.findLibrary("hilt.android").get())
                }
            }
        }

    }
}