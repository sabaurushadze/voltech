pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "voltech"
include(":app")

include(":core:presentation")
include(":core:domain")
include(":core:data")

include(":feature:auth")
include(":feature:auth:data")
include(":feature:auth:domain")
include(":feature:auth:presentation")

include(":feature:home")
include(":feature:home:data")
include(":feature:home:domain")
include(":feature:home:presentation")

include(":feature:search")
include(":feature:search:data")
include(":feature:search:domain")
include(":feature:search:presentation")

include(":feature:profile")

include(":feature:profile:data")
include(":feature:profile:presentation")
include(":feature:profile:domain")
include(":core-ui")
include(":resource")
