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

rootProject.name = "book"
include(":app")
include(":data:remote")
include(":domain")
include(":shared:core-mvi")
include(":shared:util:common")
include(":shared:util:extension")
include(":shared:ui:extension")
include(":shared:ui:system")
include(":shared:navigation")
include(":data:repository")
include(":feature:home")
include(":feature:splash")
include(":feature:search")
include(":feature:bookmark")
include(":feature:detail")
include(":data:local")
