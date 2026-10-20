plugins { kotlin("jvm"); kotlin("plugin.serialization") ; application }

kotlin { jvmToolchain(17) }
application { mainClass.set("com.creator_surge.ApplicationKt") }
dependencies {
    implementation("io.ktor:ktor-server-core-jvm:3.0.3")
    implementation("io.ktor:ktor-server-netty-jvm:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:3.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:3.0.3")
    implementation("io.ktor:ktor-server-call-logging-jvm:3.0.3")
    implementation("ch.qos.logback:logback-classic:1.5.12")
}
