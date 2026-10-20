package com.creator_surge.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creator_surge.model.CreationResult
import com.creator_surge.network.ApiClient
import kotlinx.coroutines.launch

@Composable fun PromptScreen() {
    var prompt by remember { mutableStateOf("") }; var result by remember { mutableStateOf<CreationResult?>(null) }
    var error by remember { mutableStateOf<String?>(null) }; var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Creator Surge", style = MaterialTheme.typography.headlineMedium)
        Text("Describe what you want to create.")
        OutlinedTextField(value = prompt, onValueChange = { prompt = it }, label = { Text("Your creation prompt") }, modifier = Modifier.fillMaxWidth(), minLines = 4)
        Button(onClick = { scope.launch { loading = true; error = null; result = null; try { result = ApiClient.create(prompt) } catch (t: Throwable) { error = t.message ?: "Request failed" }; loading = false } }, enabled = prompt.isNotBlank() && !loading, modifier = Modifier.fillMaxWidth()) { Text("Create") }
        if (loading) CircularProgressIndicator()
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        result?.let { ResultSections(it) }
    }
}
