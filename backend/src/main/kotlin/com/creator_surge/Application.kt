package com.creator_surge

import com.creator_surge.router.CreationRequest
import com.creator_surge.router.CreationRouter
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import java.io.File

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0") { module() }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json(Json { prettyPrint = true; ignoreUnknownKeys = true }) }
    val outputRoot = File(System.getenv("OUTPUT_DIR") ?: "outputs").absoluteFile
    outputRoot.mkdirs()
    routing {
        get("/health") { call.respond(mapOf("status" to "ok")) }
        post("/create") {
            val request = runCatching { call.receive<CreationRequest>() }.getOrElse {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Expected JSON with a non-empty prompt and optional mediums")); return@post
            }
            if (request.prompt.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "prompt must not be blank")); return@post
            }
            call.respond(CreationRouter(outputRoot, "https://${call.request.host()}" ).routeCreation(request))
        }
        staticFiles("/outputs", outputRoot) { default("index.html") }
    }
}
