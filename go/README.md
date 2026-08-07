# MiniMax H3 API Go SDK for RunAPI

Use the MiniMax H3 Go module for typed video tasks.

```bash
go get github.com/runapi-ai/minimax-h3-sdk/go@latest
```

```go
import (
  "context"
  "github.com/runapi-ai/minimax-h3-sdk/go/minimaxh3"
)

client, err := minimaxh3.NewClient()
result, err := client.TextToVideo.Run(context.Background(), minimaxh3.TextToVideoParams{
  Model: minimaxh3.ModelMiniMaxH3,
  Prompt: "A cinematic tracking shot through a rain-soaked city",
  AspectRatio: "16:9",
})
```

Use `TextToVideo` for prompt-only or reference-media generation. Use `ImageToVideo` with a first frame, last frame, or both. Each resource provides `Create`, `Get`, and `Run`.

- [Model reference](https://runapi.ai/models/minimax-h3)
- [API reference](https://runapi.ai/docs/api/minimax-h3/text-to-video)
- [Pricing and rate limits](https://runapi.ai/models/minimax-h3)

Licensed under the Apache License, Version 2.0.
