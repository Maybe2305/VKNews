
// Top-level build file where you can add configuration options common to all sub-projects/modules.
private val vkClientId = 53458866
private val vkClientSecret = "SZSjMJhDyfbW2kYFoGWp"

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("vkid.manifest.placeholders") version "1.1.0" apply true
    alias(libs.plugins.ksp) apply false
}