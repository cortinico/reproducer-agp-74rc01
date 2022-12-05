plugins {
    id("com.ncorti.kotlin.gradle.template.plugin")
    id("com.android.application")
}

android {
    compileSdk = 33
    namespace = "com.ncorti.kotlin.template.app"
}

templateExampleConfig {
    message.set("Just trying this gradle plugin...")
}
