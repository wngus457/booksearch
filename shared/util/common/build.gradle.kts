plugins {
    id("android.library.convention")
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.juhyeon.book.shared.util.common"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.shared.coreMvi)
    implementation(projects.shared.ui.extension)

    implementation(libs.java.inject)
    implementation(libs.serialization)
}