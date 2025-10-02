package com.juhyeon.book.convention

import com.android.build.api.dsl.LibraryExtension
import com.juhyeon.book.convention.config.composeCompiler
import com.juhyeon.book.convention.config.configureCommonExtension
import com.juhyeon.book.convention.config.debug
import com.juhyeon.book.convention.config.kotlin
import com.juhyeon.book.convention.config.release
import com.juhyeon.book.convention.config.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("kotlin-android")
                apply("com.google.devtools.ksp")
                apply("kotlin-parcelize")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            extensions.configure<LibraryExtension> {
                configureCommonExtension(this)

                buildFeatures {
                    compose = true
                }

                kotlin {
                    sourceSets {
                        debug {
                            kotlin.srcDir("build/generated/ksp/debug/kotlin")
                        }
                        release {
                            kotlin.srcDir("build/generated/ksp/release/kotlin")
                        }
                    }
                }

                composeCompiler {
                    includeSourceInformation.set(true)
                }

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

                dependencies {
                    add("implementation", project(":shared:core-mvi"))
                    add("implementation", project(":shared:ui:extension"))
                    add("implementation", project(":shared:ui:system"))
                    add("implementation", project(":shared:util:common"))
                    add("implementation", project(":shared:util:extension"))
                    add("implementation", project(":shared:navigation"))

                    add("implementation", project(":domain"))

                    add("implementation", platform(libs.findLibrary("compose-bom").get()))
                    add("implementation", libs.findBundle("compose").get())

                    add("implementation", libs.findLibrary("coil-core").get())
                    add("implementation", libs.findLibrary("coil-compose").get())

                    add("implementation", libs.findLibrary("hilt-android").get())
                    add("ksp", libs.findLibrary("hilt-android-compiler").get())
                }
            }
        }
    }
}