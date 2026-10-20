package com.creator_surge.router

import com.creator_surge.engines.*
import kotlinx.serialization.Serializable
import java.io.File

@Serializable data class CreationRequest(val prompt: String, val mediums: List<String> = listOf("image", "video", "game", "code", "docs"))
@Serializable data class Overview(val title: String, val summary: String, val requestedMediums: List<String>, val prompt: String, val status: String, val totalArtefacts: Int, val timestamp: Long)
@Serializable data class ImageArtefact(val title: String, val filePath: String, val name: String, val path: String, val url: String, val mimeType: String)
@Serializable data class VideoArtefact(val title: String, val filePath: String, val name: String, val path: String, val url: String, val mimeType: String, val note: String, val description: String)
@Serializable data class GameArtefact(val title: String, val filePath: String, val name: String, val path: String, val url: String, val mimeType: String, val description: String)
@Serializable data class CodeArtefact(val title: String, val filePath: String, val name: String, val path: String, val url: String, val content: String, val description: String)
@Serializable data class DocsArtefact(val title: String, val filePath: String, val name: String, val path: String, val url: String, val content: String, val description: String)
@Serializable data class CreationResult(val overview: Overview, val images: List<ImageArtefact>, val videos: List<VideoArtefact>, val game: List<GameArtefact>, val code: List<CodeArtefact>, val docs: List<DocsArtefact>)

class CreationRouter(private val root: File, private val baseUrl: String) {
 fun routeCreation(request: CreationRequest): CreationResult {
  val id=java.util.UUID.randomUUID().toString().take(8)
  val selected=request.mediums.map{it.lowercase()}.toSet()
  val images=if("image" in selected) ImageEngine(root,baseUrl).generate(request.prompt,id) else emptyList()
  val videos=if("video" in selected) VideoEngine(root,baseUrl).generate(request.prompt,id) else emptyList()
  val games=if("game" in selected) GameEngine(root,baseUrl).generate(request.prompt,id) else emptyList()
  val code=if("code" in selected) CodeEngine(root,baseUrl).generate(request.prompt,id) else emptyList()
  val docs=if("docs" in selected) DocsEngine(root,baseUrl).generate(request.prompt,id) else emptyList()
  val total=images.size+videos.size+games.size+code.size+docs.size
  val overview=Overview("Creator Surge result","Generated starter artefacts for: ${request.prompt.take(240)}",selected.toList().sorted(),request.prompt,"success",total,System.currentTimeMillis())
  return CreationResult(overview,images,videos,games,code,docs)
 }
}
