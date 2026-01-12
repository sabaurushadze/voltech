import com.tbc.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                add("implementation",libs.findLibrary("retrofit.serialization").get())
                add("implementation",libs.findLibrary("serialization.json").get())
                add("implementation",libs.findLibrary("okhttp").get())
                add("implementation",libs.findLibrary("logging.interceptor").get())
            }
        }
    }
}