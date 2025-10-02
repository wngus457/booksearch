plugins {
    id("android.library.convention")
}

android {
    namespace = "com.juhyeon.book.shared.ui.extension"
}

dependencies {
    implementation(libs.compose.navigation)
}