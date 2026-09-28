# Changelog

## [js/v0.1.1](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/js%2Fv0.1.1), [go/v0.1.1](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/go%2Fv0.1.1) - 2026-09-28

### Added
- Return usage.cost as a float USD amount on completed async Task query and webhook envelopes.

### Removed
- Remove the public Task billing object from Task envelopes.
  Migration: Read usage.cost on completed Task envelopes. Create, processing, and failed envelopes omit usage.


## [ruby/v0.1.2](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/ruby%2Fv0.1.2) - 2026-09-04

### Changed
- Update the `runapi-core` dependency range so this package remains installable with other current RunAPI Ruby SDKs.


## [ruby/v0.1.1](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/ruby%2Fv0.1.1) - 2026-08-18

### Changed
- Allow Ruby clients to install the core SDK release that adds persistent Files and multipart Uploads alongside this model SDK.


## [js/v0.1.0](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/js%2Fv0.1.0), [ruby/v0.1.0](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/ruby%2Fv0.1.0), [go/v0.1.0](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/go%2Fv0.1.0), [python/v0.1.0](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/python%2Fv0.1.0), [java/v0.1.0](https://github.com/runapi-ai/minimax-h3-sdk/releases/tag/java%2Fv0.1.0) - 2026-08-07

### Added
- Add text, reference-media, and first/last-frame video generation with MiniMax H3.
