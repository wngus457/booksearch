import java.io.FileInputStream
import java.util.Properties
import kotlin.toString

plugins {
    id("android.data.convention")
}
android {
    namespace = "com.juhyeon.book.data.remote"

    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "KAKAO_BOOK_KEY", localProperties["KAKAO_BOOK_KEY"].toString())
        }
    }
}

dependencies {
    implementation(projects.data.repository)
    implementation(projects.shared.util.common)
    implementation(projects.shared.util.extension)

    implementation(libs.bundles.retrofit)
}