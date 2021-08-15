import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePluginDependency.ANDROID_GRADLE)
        classpath(GradlePluginDependency.KOTLIN_GRADLE)
        classpath(GradlePluginDependency.SAFE_ARGS)
        classpath(GradlePluginDependency.HILT)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
