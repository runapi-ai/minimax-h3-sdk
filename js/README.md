# MiniMax H3 API JavaScript SDK for RunAPI

Use `@runapi.ai/minimax-h3` for typed MiniMax H3 video tasks in JavaScript and TypeScript.

```bash
npm install @runapi.ai/minimax-h3
```

```typescript
import { MiniMaxH3Client } from '@runapi.ai/minimax-h3';

const client = new MiniMaxH3Client();
const result = await client.textToVideo.run({
  model: 'minimax-h3',
  prompt: 'A cinematic tracking shot through a rain-soaked city',
  aspect_ratio: '16:9',
});
console.log(result.videos[0].url);
```

Use `textToVideo` for prompt-only or reference-media generation. Use `imageToVideo` with a first frame, last frame, or both. Each resource provides `create`, `get`, and `run`.

- [Model reference](https://runapi.ai/models/minimax-h3)
- [API reference](https://runapi.ai/docs/api/minimax-h3/text-to-video)
- [Pricing and rate limits](https://runapi.ai/models/minimax-h3)

Licensed under the Apache License, Version 2.0.
