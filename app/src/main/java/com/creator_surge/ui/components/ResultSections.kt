package com.creator_surge.ui.components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.creator_surge.model.CreationResult

@Composable fun ResultSections(result: CreationResult) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(result.overview.title, style = MaterialTheme.typography.titleLarge)
        Text(result.overview.summary)
        if (result.images.isNotEmpty()) { Text("Images", style = MaterialTheme.typography.titleMedium); result.images.forEach { AsyncImage(model = it.url, contentDescription = it.name, modifier = Modifier.fillMaxWidth().height(200.dp)) } }
        if (result.videos.isNotEmpty()) { Text("Video previews", style = MaterialTheme.typography.titleMedium); result.videos.forEach { Text(it.note); WebPreview(it.url) } }
        if (result.game.isNotEmpty()) { Text("Games", style = MaterialTheme.typography.titleMedium); result.game.forEach { WebPreview(it.url) } }
        if (result.code.isNotEmpty()) { Text("Code", style = MaterialTheme.typography.titleMedium); result.code.forEach { Text(it.name, style = MaterialTheme.typography.titleSmall); Surface(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.fillMaxWidth()) { Text(it.content, modifier = Modifier.padding(12.dp)) } } }
        if (result.docs.isNotEmpty()) { Text("Docs", style = MaterialTheme.typography.titleMedium); result.docs.forEach { Text(it.name, style = MaterialTheme.typography.titleSmall); Text(it.content) } }
    }
}

@Composable private fun WebPreview(url: String) {
    AndroidView(modifier = Modifier.fillMaxWidth().height(300.dp), factory = { context -> WebView(context).apply { layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); webViewClient = WebViewClient(); settings.javaScriptEnabled = true; loadUrl(url) } })
}
