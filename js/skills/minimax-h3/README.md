<p align="center">
  <a href="https://github.com/runapi-ai/minimax-h3">
    <h3 align="center">MiniMax H3 API Skill for RunAPI</h3>
  </a>
</p>

<p align="center">
  Install this agent skill to use MiniMax H3 text, reference-media, and first/last-frame video generation through RunAPI.
</p>

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/minimax-h3)](https://www.skills.sh/runapi-ai/minimax-h3/minimax-h3)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--minimax--h3-111827)](https://clawhub.ai/runapi-ai/runapi-minimax-h3)
[![License](https://img.shields.io/github/license/runapi-ai/minimax-h3)](https://github.com/runapi-ai/minimax-h3/blob/main/LICENSE)

</div>
<br/>

```bash
npx skills add runapi-ai/minimax-h3 -g
```

Or paste this prompt to your AI agent:

```text
Install the minimax-h3 skill for me:

1. Clone https://github.com/runapi-ai/minimax-h3
2. Copy the skills/minimax-h3/ directory into your
   user-level skills directory (e.g. ~/.claude/skills/
   for Claude Code, ~/.codex/skills/ for Codex, or the
   configured Gemini CLI skills directory).
3. Verify that SKILL.md is present.
4. Confirm the install path when done.
```

The canonical agent instructions are in `skills/minimax-h3/SKILL.md` and cover Claude Code, Codex, Gemini CLI, Cursor, and compatible skill hosts.

## Quick Example

```shell
runapi minimax-h3 text-to-video --input-file request.json
```

Use the target-language SDK for application integration. Use the CLI for one-off jobs and manual testing.

## Links

- [Model reference](https://runapi.ai/models/minimax-h3)
- [API reference](https://runapi.ai/docs/api/minimax-h3/text-to-video)
- [SDK repository](https://github.com/runapi-ai/minimax-h3-sdk)
- [Pricing and rate limits](https://runapi.ai/models/minimax-h3)

## License

Licensed under the Apache License, Version 2.0.
