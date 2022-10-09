rootProject.name = "KNest"

val submodules = mutableSetOf(":framework")

if (System.getenv("JITPACK") == null) {
    submodules.add(":preview")
}

include(submodules)