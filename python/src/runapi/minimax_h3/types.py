"""MiniMax H3 model lists, enums, and response models."""

from __future__ import annotations

from runapi.core import BaseModel, TaskResponse, optional, required


class MediaUrl(BaseModel):
    url = optional(str)


class VideoTaskResponse(TaskResponse):
    """Response for a video generation task."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    videos = optional([lambda: MediaUrl])
    error = optional(str)


class CompletedVideoTaskResponse(VideoTaskResponse):
    """Narrowed response from ``run()`` once polling observes completion."""

    videos = required([lambda: MediaUrl])
