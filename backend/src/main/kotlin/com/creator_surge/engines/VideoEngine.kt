package com.creator_surge.engines
import com.creator_surge.router.VideoArtefact
import java.io.File
class VideoEngine(private val root: File, private val base: String) {
 fun generate(prompt: String, id: String): List<VideoArtefact> {
  val dir=File(root,"videos").apply{mkdirs()}; val file=File(dir,"$id-video.html")
  val safe=prompt.take(200).replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
  file.writeText("""<!doctype html><html><meta name="viewport" content="width=device-width"><body style="margin:0;background:#111827;color:white;font:24px sans-serif;display:grid;place-items:center;height:100vh;text-align:center"><div><div style="font-size:64px">▶</div><h2>Video generation placeholder</h2><p>$safe</p><small>Connect a video-generation/rendering provider to produce an MP4.</small></div></body></html>""")
  val path="/outputs/videos/${file.name}"
  return listOf(VideoArtefact(file.name,file.absolutePath,file.name,file.absolutePath,base+path,"text/html","Preview placeholder, not an MP4. Add a video provider or renderer for playable clips."))
 }
}