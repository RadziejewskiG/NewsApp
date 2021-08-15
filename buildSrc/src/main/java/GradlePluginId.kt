object GradlePluginId {
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val JAVA_LIBRARY = "java-library"
    const val KOTLIN = "kotlin"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val KOTLIN_KAPT = "org.jetbrains.kotlin.kapt"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val HILT = "dagger.hilt.android.plugin"
    const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
}

object GradlePluginDependency {
    const val ANDROID_GRADLE =
        "com.android.tools.build:gradle:${DependencyVersion.GRADLE}"
    const val KOTLIN_GRADLE =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${DependencyVersion.KOTLIN}"
    const val SAFE_ARGS =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${DependencyVersion.NAVIGATION}"
    const val HILT =
        "com.google.dagger:hilt-android-gradle-plugin:${DependencyVersion.HILT}"
}
