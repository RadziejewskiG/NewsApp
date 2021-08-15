rootProject.name = "NewsApp"

include(":recruitment")

include(":uimatches")
include(":uinews")
include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":sharedui")

project(":uinews").projectDir = File("feature/uinews")
project(":uimatches").projectDir = File("feature/uimatches")
