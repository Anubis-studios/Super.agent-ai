# Creator Surge Super-Agent

Starter monorepo with a Jetpack Compose Android client and Ktor REST API. The engines generate example artefacts; they are not connected to external AI generation providers yet.

## Project tree

```text
creator-surge/
├── settings.gradle.kts
├── build.gradle.kts
├── Dockerfile
├── render.yaml
├── README.md
├── backend/
│   ├── build.gradle.kts
│   └── src/main/kotlin/com/creator_surge/
│       ├── Application.kt
│       ├── router/CreationRouter.kt
│       └── engines/{ImageEngine,VideoEngine,GameEngine,CodeEngine,DocsEngine}.kt
└── app/
    ├── build.gradle.kts
    └── src/main/
        ├── AndroidManifest.xml
        └── java/com/creator_surge/
            ├── MainActivity.kt
            ├── model/CreationResult.kt
            ├── network/ApiClient.kt
            └── ui/{CreatorSurgeApp.kt,components/PromptScreen.kt,components/ResultSections.kt}
```

## Run backend locally

Requires JDK 17 and Gradle. From the root:

```bash
gradle :backend:run
curl http://localhost:8080/health
curl -X POST http://localhost:8080/create -H 'Content-Type: application/json' -d '{"prompt":"A fantasy puzzle game","mediums":["image","game","code","docs"]}'
```

Outputs are written to `outputs/` by default and served from `/outputs/...`.

## Run Android app

Open this directory in Android Studio, sync Gradle, then run the `app` configuration on a device/emulator. Set `ApiClient.BASE_URL` in `app/src/main/java/com/creator_surge/network/ApiClient.kt` to the HTTPS URL of your deployed backend before building for a device. A local emulator can use `http://10.0.2.2:8080`, but Android's manifest currently requires HTTPS; use a local debug-only cleartext override for local HTTP testing.

## Deploy to Render

1. Push this project to a GitHub, GitLab, or Bitbucket repository.
2. In Render, create a Blueprint from that repository; Render reads `render.yaml` and builds the backend Docker image.
3. Verify `/health` on the deployed service, then update the Android `BASE_URL` and rebuild the app.

**Storage note:** generated files need persistent storage for durable URLs. Attach a Render persistent disk mounted at `/var/data` and ensure the selected service plan supports disks. The sample `render.yaml` documents the intended mount path but does not provision a disk automatically.

## Production notes

- ImageEngine creates an SVG placeholder, not a model-generated PNG.
- VideoEngine creates an HTML preview page, not an MP4. It intentionally labels this clearly.
- GameEngine creates a small interactive HTML tap game.
- CodeEngine and DocsEngine create actual Kotlin and Markdown files.
- Add authentication, request limits, output cleanup, content moderation, and real AI provider integrations before public launch.
