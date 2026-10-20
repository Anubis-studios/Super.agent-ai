package com.creator_surge.engines

import com.creator_surge.router.GameArtefact
import java.io.File

class GameEngine(private val root: File, private val base: String) {
    fun generate(prompt: String, id: String): List<GameArtefact> {
        val dir = File(root, "games/$id").apply { mkdirs() }; val file = File(dir, "index.html")
        val safe = prompt.take(180).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
        file.writeText("""<!doctype html><html><meta name="viewport" content="width=device-width,initial-scale=1"><title>Mini Game</title><body style="font:20px sans-serif;background:#0f172a;color:#fff;text-align:center;padding:24px"><h1>✨ Creator Surge Mini Game</h1><p>$safe</p><p>Tap the crystal as many times as you can!</p><button id="b" style="font-size:24px;padding:24px;border-radius:18px">💎 Tap crystal</button><h2>Score: <span id="s">0</span></h2><script>let n=0;document.getElementById('b').onclick=()=>document.getElementById('s').textContent=++n;</script></body></html>""")
        val path = "/outputs/games/$id/index.html"; return listOf(GameArtefact(file.name, file.absolutePath, base + path, "text/html"))
    }
}
