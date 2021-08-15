import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {

    val kotlin = arrayListOf(
        LibraryDependency.KOTLIN,
        LibraryDependency.KOTLIN_REFLECT,
        LibraryDependency.COROUTINES_CORE
    )

    val coroutinesAndroid = arrayListOf(
        LibraryDependency.COROUTINES_ANDROID
    )

    val androidLibraries = arrayListOf(
        LibraryDependency.APP_COMPAT,
        LibraryDependency.LIFECYCLE_RUNTIME,
        LibraryDependency.LIFECYCLE_RUNTIME_KTX,
        LibraryDependency.LIFECYCLE_JAVA_8,
        LibraryDependency.CORE_KTX,
        LibraryDependency.TRANSITION_KTX,
        LibraryDependency.ACTIVITY_KTX,
        LibraryDependency.FRAGMENT_KTX
    )

    val viewModel = arrayListOf(
        LibraryDependency.LIFECYCLE_VIEW_MODEL_KTX,
        LibraryDependency.LIFECYCLE_VIEW_MODEL_SAVED_STATE
    )

    val ui = arrayListOf(
        LibraryDependency.CONSTRAINT_LAYOUT,
        LibraryDependency.COORDINATOR_LAYOUT,
        LibraryDependency.RECYCLER_VIEW,
        LibraryDependency.MATERIAL
    )

    val swipeToRefreshLayout = arrayListOf(
        LibraryDependency.SWIPE_TO_REFRESH_LAYOUT
    )

    val navigation = arrayListOf(
        LibraryDependency.NAVIGATION_FRAGMENT_KTX,
        LibraryDependency.NAVIGATION_UI_KTX,
        LibraryDependency.NAVIGATION_FEATURES
    )

    val lottie = arrayListOf(
        LibraryDependency.LOTTIE
    )

    val shimmer = arrayListOf(
        LibraryDependency.SHIMMER
    )

    val glide = arrayListOf(
        LibraryDependency.GLIDE
    )

    val glideCodegen = arrayListOf(
        LibraryDependency.GLIDE_COMPILER
    )

    val networking = arrayListOf(
        LibraryDependency.OK_HTTP,
        LibraryDependency.OK_HTTP_LOGGING,
        LibraryDependency.RETROFIT,
        LibraryDependency.RETROFIT_MOSHI
    )

    val room = arrayListOf(
        LibraryDependency.ROOM,
        LibraryDependency.ROOM_KTX
    )

    val paging = arrayListOf(
        LibraryDependency.PAGING
    )

    val roomCodegen = arrayListOf(
        LibraryDependency.ROOM_COMPILER
    )

    val moshi = arrayListOf(
        LibraryDependency.MOSHI
    )

    val moshiCodegen = arrayListOf(
        LibraryDependency.MOSHI_CODEGEN
    )

    val hiltAndroid = arrayListOf(
        LibraryDependency.HILT_ANDROID
    )

    val hiltAndroidCodegen = arrayListOf(
        LibraryDependency.HILT_ANDROID_COMPILER
    )

    val javaXInject = arrayListOf(
        LibraryDependency.JAVAX_INJECT
    )

    val testLibraries = arrayListOf(
        LibraryDependency.JUNIT,
        LibraryDependency.MOCKITO,
        LibraryDependency.MOCKITO_INLINE,
        LibraryDependency.COROUTINES_TEST,
        LibraryDependency.ARCH_CORE,
        LibraryDependency.TRUTH
    )

    val androidTestLibraries = arrayListOf(
        LibraryDependency.JUNIT_EXT,
        LibraryDependency.ESPRESSO,
        LibraryDependency.MOCKITO_ANDROID
    )

}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.api(list: List<String>) {
    list.forEach { dependency ->
        add("api", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
