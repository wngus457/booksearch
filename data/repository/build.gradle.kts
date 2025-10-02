plugins {
    id("android.data.convention")
}

android {
    namespace = "com.juhyeon.book.data.repository"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.shared.util.extension)
}