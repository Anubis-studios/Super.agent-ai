package com.creator_surge.engines

import com.creator_surge.router.ImageArtefact
import java.io.File

class ImageEngine(private val root: File, private val base: String) {
    fun generate(prompt: String, id: String): List<ImageArtefact> {
        val dir = File(root, "images").apply { mkdirs() }; val file = File(dir, "$id.svg")
        val safe = prompt.take(100).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
        file.writeText("""<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="576" viewBox="0 0 1024 576"><defs><linearGradient id="g"><stop stop-color="#172554"/><stop offset="1" stop-color="#7c3aed"/></linearGradient></defs><rect width="1024" height="576" fill="url(#g)"/><circle cx="820" cy="110" r="75" fill="#fef3c7"/><text x="64" y="240" font-family="sans-serif" font-size="38" fill="white">Creator Surge</text><text x="64" y="300" font-family="sans-serif" font-size="24" fill="#ede9fe">$safe</text><text x="64" y="520" font-family="sans-serif" font-size="18" fill="#ddd6fe">Generated placeholder artwork — connect an image model for real generation</text></svg>""")
        val path = "/outputs/images/${file.name}"; return listOf(ImageArtefact(file.name, file.absolutePath, base + path, "image/svg+xml"))
    }
}
