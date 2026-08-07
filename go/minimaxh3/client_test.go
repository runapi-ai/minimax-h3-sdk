package minimaxh3

import (
	"context"
	"encoding/json"
	"strings"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method, path string
	body         any
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method, s.path = method, path
	if opts != nil {
		s.body = opts.Body
	}
	return json.RawMessage(`{"id":"task_123","status":"processing"}`), nil
}

func TestTextToVideoCreateWithReferences(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{Model: ModelMiniMaxH3, Prompt: "Continue the performance", ReferenceImageURLs: []string{"https://cdn.runapi.ai/public/samples/image.jpg"}, ReferenceAudioURLs: []string{"https://cdn.runapi.ai/public/samples/voice.mp3"}, AspectRatio: "adaptive"})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["model"] != "minimax-h3" {
		t.Fatalf("unexpected model: %v", body["model"])
	}
}

func TestImageToVideoCreateWithLastFrameOnly(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.ImageToVideo.Create(context.Background(), ImageToVideoParams{Model: ModelMiniMaxH3, Prompt: "Animate the scene", LastFrameImageURL: "https://cdn.runapi.ai/public/samples/image.jpg"})
	if err != nil {
		t.Fatal(err)
	}
	if stub.path != imageToVideoPath {
		t.Fatalf("unexpected path: %s", stub.path)
	}
}

func TestTextToVideoRejectsAudioOnly(t *testing.T) {
	client := NewClientWithHTTP(&stubHTTPClient{})
	_, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{Model: ModelMiniMaxH3, Prompt: "Continue", ReferenceAudioURLs: []string{"https://cdn.runapi.ai/public/samples/voice.mp3"}})
	if err == nil || !strings.Contains(err.Error(), "one of reference_image_urls, reference_video_urls is required") {
		t.Fatalf("unexpected error: %v", err)
	}
}

func TestTextToVideoRequiresAspectRatioWithoutReferences(t *testing.T) {
	client := NewClientWithHTTP(&stubHTTPClient{})
	_, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{Model: ModelMiniMaxH3, Prompt: "Continue"})
	if err == nil || !strings.Contains(err.Error(), "aspect_ratio is required when reference_image_urls is absent and reference_video_urls is absent") {
		t.Fatalf("unexpected error: %v", err)
	}
}

func TestTextToVideoRejectsAdaptiveAspectRatioWithoutReferences(t *testing.T) {
	client := NewClientWithHTTP(&stubHTTPClient{})
	_, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{Model: ModelMiniMaxH3, Prompt: "Continue", AspectRatio: "adaptive"})
	if err == nil || !strings.Contains(err.Error(), "aspect_ratio must be one of:") {
		t.Fatalf("unexpected error: %v", err)
	}
}

func TestImageToVideoRequiresFrame(t *testing.T) {
	client := NewClientWithHTTP(&stubHTTPClient{})
	_, err := client.ImageToVideo.Create(context.Background(), ImageToVideoParams{Model: ModelMiniMaxH3, Prompt: "Animate"})
	if err == nil || !strings.Contains(err.Error(), "one of first_frame_image_url, last_frame_image_url is required") {
		t.Fatalf("unexpected error: %v", err)
	}
}
