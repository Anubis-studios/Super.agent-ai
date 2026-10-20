package com.creator_surge.model

import kotlinx.serialization.Serializable

@Serializable data class Overview(val title: String, val summary: String, val requestedMediums: List<String> = emptyList())
@Serializable data class ImageArtefact(val name: String, val path: String, val url: String, val mimeType: String)
@Serializable data class VideoArtefact(val name: String, val path: String, val url: String, val mimeType: String, val note: String = "")
@Serializable data class GameArtefact(val name: String, val path: String, val url: String, val mimeType: String)
@Serializable data class CodeArtefact(val name: String, val path: String, val url: String, val content: String)
@Serializable data class DocsArtefact(val name: String, val path: String, val url: String, val content: String)
@Serializable data class CreationResult(val overview: Overview, val images: List<ImageArtefact> = emptyList(), val videos: List<VideoArtefact> = emptyList(), val game: List<GameArtefact> = emptyList(), val code: List<CodeArtefact> = emptyList(), val docs: List<DocsArtefact> = emptyList())
