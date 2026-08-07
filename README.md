<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/minimax-h3-sdk">MiniMax H3 API SDK for RunAPI</a>
</h3>

<p align="center">
  MiniMax H3 API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/minimax-h3)](https://www.npmjs.com/package/@runapi.ai/minimax-h3)
[![PyPI](https://img.shields.io/pypi/v/runapi-minimax-h3)](https://pypi.org/project/runapi-minimax-h3/)
[![RubyGems](https://img.shields.io/gem/v/runapi-minimax-h3)](https://rubygems.org/gems/runapi-minimax-h3)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/minimax-h3-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/minimax-h3-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-minimax-h3)](https://central.sonatype.com/artifact/ai.runapi/runapi-minimax-h3)
[![License](https://img.shields.io/github/license/runapi-ai/minimax-h3-sdk)](https://github.com/runapi-ai/minimax-h3-sdk/blob/main/LICENSE)

</div>
<br/>

Typed SDKs for MiniMax H3 video generation through RunAPI. The public `minimax-h3-sdk` repository groups the JavaScript, Python, Ruby, Go, and Java packages. PHP is released from the split `minimax-h3-php` Composer repository.

MiniMax H3 exposes two resources:

- `textToVideo` / `text_to_video` for prompt-only requests and requests with reference images, videos, or audio.
- `imageToVideo` / `image_to_video` for first-frame, last-frame, or first-and-last-frame requests.

## Packages

| Language | Package |
| --- | --- |
| JavaScript / TypeScript | `@runapi.ai/minimax-h3` |
| Python | `runapi-minimax-h3` |
| Ruby | `runapi-minimax-h3` |
| Go | `github.com/runapi-ai/minimax-h3-sdk/go` |
| Java | `ai.runapi:runapi-minimax-h3` |

For PHP, use `runapi-ai/minimax-h3` from the [split Composer repository](https://github.com/runapi-ai/minimax-h3-php).

## JavaScript Quick Start

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

Reference media uses the same `textToVideo` resource:

```typescript
const task = await client.textToVideo.create({
  model: 'minimax-h3',
  prompt: 'Keep the character and voice consistent in a new scene',
  reference_image_urls: ['https://cdn.runapi.ai/public/samples/image.jpg'],
  reference_audio_urls: ['https://cdn.runapi.ai/public/samples/voice.mp3'],
  aspect_ratio: 'adaptive',
});
```

Use `create` to submit and return a task id, `get` to read current status, or `run` to submit and poll to completion. In request handlers, prefer `create` with a callback or later polling.

Returned file URLs are temporary. Download generated files into durable storage within the retention window.

## Links

- [Model reference](https://runapi.ai/models/minimax-h3)
- [API reference](https://runapi.ai/docs/api/minimax-h3/text-to-video)
- [SDK documentation](https://runapi.ai/docs/resources/sdks)
- [Pricing and rate limits](https://runapi.ai/models/minimax-h3)
- [Model catalog](https://runapi.ai/models)

## License

Licensed under the Apache License, Version 2.0.
