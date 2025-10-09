plugins {
    // Apply the Android and Kotlin plugins using the version catalog aliases
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
//    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.hilt.plugin) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
