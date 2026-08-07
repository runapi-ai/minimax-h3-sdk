# MiniMax H3 API Java SDK for RunAPI

Use `ai.runapi:runapi-minimax-h3` for typed MiniMax H3 video tasks.

```kotlin
dependencies {
  implementation("ai.runapi:runapi-minimax-h3:0.1.0")
}
```

```java
import ai.runapi.minimaxh3.MiniMaxH3Client;
import ai.runapi.minimaxh3.types.TextToVideoModel;
import ai.runapi.minimaxh3.types.TextToVideoParams;

MiniMaxH3Client client = MiniMaxH3Client.builder().build();
var result = client.textToVideo().run(
    TextToVideoParams.builder()
        .model(TextToVideoModel.MINIMAX_H3)
        .prompt("A cinematic tracking shot through a rain-soaked city")
        .aspectRatio("16:9")
        .build());
```

Use `textToVideo()` for prompt-only or reference-media generation. Use `imageToVideo()` with a first frame, last frame, or both. Each resource provides `create`, `get`, and `run`.

- [Model reference](https://runapi.ai/models/minimax-h3)
- [API reference](https://runapi.ai/docs/api/minimax-h3/text-to-video)
- [Pricing and rate limits](https://runapi.ai/models/minimax-h3)

Licensed under the Apache License, Version 2.0.
