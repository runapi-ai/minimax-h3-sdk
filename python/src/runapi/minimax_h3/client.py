"""MiniMax H3 client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ProviderClient

from .resources.image_to_video import ImageToVideo
from .resources.text_to_video import TextToVideo


class MiniMaxH3Client(ProviderClient):
    """MiniMax H3 text-to-video and image-to-video client.

    Example::

        client = MiniMaxH3Client(api_key="sk-...")
        result = client.text_to_video.run(
            model="minimax-h3", prompt="A neon city street"
        )
    """

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        super().__init__(api_key, **options)
        http = self._http
        self.text_to_video = TextToVideo(http)
        self.image_to_video = ImageToVideo(http)
