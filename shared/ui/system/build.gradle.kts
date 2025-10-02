plugins {
    id("android.library.convention")
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.juhyeon.book.shared.ui.system"
}

dependencies {
    implementation(projects.shared.coreMvi)
    implementation(projects.shared.util.common)
    implementation(projects.shared.ui.extension)
    implementation(projects.shared.navigation)

    implementation(libs.compose.navigation)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.accompainst.permission)

    implementation(libs.serialization)
}