package com.creator_surge.engines
import com.creator_surge.router.DocsArtefact
import java.io.File
class DocsEngine(private val root: File, private val base: String) {
 fun generate(prompt: String, id: String): List<DocsArtefact> {
  val dir=File(root,"docs/$id").apply{mkdirs()}; val file=File(dir,"README.md")
  val content="""# Creator Surge Project
## Brief
$prompt
## Generated output
This is a starter brief generated from your prompt. Review and expand the requirements before production use.
## Next steps
- Connect an AI provider for substantive code, image, or video generation.
- Add tests and validate generated files before publishing.
- Review security and licensing before distributing output.
"""
  file.writeText(content); val path="/outputs/docs/$id/${file.name}"
  return listOf(DocsArtefact(file.name,file.absolutePath,file.name,file.absolutePath,base+path,content,"Markdown project brief and next steps."))
 }
}