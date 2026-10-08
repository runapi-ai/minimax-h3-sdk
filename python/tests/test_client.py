from runapi.minimax_h3 import MiniMaxH3Client


class FakeHttp:
    def __init__(self, response=None):
        self.response = response or {"id": "task_1", "status": "pending"}
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        return self.response


def test_posts_reference_media_through_text_to_video():
    fake = FakeHttp()
    client = MiniMaxH3Client(api_key="k", http_client=fake)
    client.text_to_video.create(
        model="minimax-h3",
        prompt="Continue the performance",
        reference_image_urls=["https://cdn.runapi.ai/public/samples/image.jpg"],
        reference_audio_urls=["https://cdn.runapi.ai/public/samples/voice.mp3"],
        aspect_ratio="adaptive",
    )
    assert fake.calls == [("post", "/api/v1/minimax_h3/text_to_video", {
        "model": "minimax-h3",
        "prompt": "Continue the performance",
        "reference_image_urls": ["https://cdn.runapi.ai/public/samples/image.jpg"],
        "reference_audio_urls": ["https://cdn.runapi.ai/public/samples/voice.mp3"],
        "aspect_ratio": "adaptive",
    })]


def test_posts_last_frame_only_through_image_to_video():
    fake = FakeHttp()
    client = MiniMaxH3Client(api_key="k", http_client=fake)
    client.image_to_video.create(
        model="minimax-h3", prompt="Animate", last_frame_image_url="https://cdn.runapi.ai/public/samples/image.jpg"
    )
    assert fake.calls[0][1] == "/api/v1/minimax_h3/image_to_video"
