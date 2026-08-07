# MiniMax H3 API Python SDK for RunAPI

Use `runapi-minimax-h3` for MiniMax H3 video tasks in Python.

```bash
pip install runapi-minimax-h3
```

```python
from runapi.minimax_h3 import MiniMaxH3Client

client = MiniMaxH3Client()
result = client.text_to_video.run(
    model="minimax-h3",
    prompt="A cinematic tracking shot through a rain-soaked city",
    aspect_ratio="16:9",
)
print(result.videos[0].url)
```

Use `text_to_video` for prompt-only or reference-media generation. Use `image_to_video` with a first frame, last frame, or both. Each resource provides `create`, `get`, and `run`.

- [Model reference](https://runapi.ai/models/minimax-h3)
- [API reference](https://runapi.ai/docs/api/minimax-h3/text-to-video)
- [Pricing and rate limits](https://runapi.ai/models/minimax-h3)

Licensed under the Apache License, Version 2.0.
