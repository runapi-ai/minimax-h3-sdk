package minimaxh3

import "github.com/runapi-ai/core-sdk/go/core"

type Model string

const ModelMiniMaxH3 Model = "minimax-h3"

type Video struct {
	URL string `json:"url"`
}

type AsyncTaskResponse struct {
	Usage *core.TaskUsage `json:"usage,omitempty"`
	ID     string `json:"id"`
	Status string `json:"status"`
	Error  string `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return r.Status }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

type VideoTaskResponse struct {
	AsyncTaskResponse
	Videos []Video `json:"videos,omitempty"`
}

type TextToVideoParams struct {
	Model              Model    `json:"model" help:"required; model slug"`
	Prompt             string   `json:"prompt" help:"required; 1 to 7000 characters"`
	DurationSeconds    int      `json:"duration_seconds,omitempty" help:"optional; 4 to 15 seconds; default 6"`
	OutputResolution   string   `json:"output_resolution,omitempty" help:"optional; 768p or 2k; default 2k"`
	AspectRatio        string   `json:"aspect_ratio,omitempty" help:"optional; adaptive requires reference images or videos"`
	ReferenceImageURLs []string `json:"reference_image_urls,omitempty" help:"optional; up to 9 public HTTP(S) image URLs"`
	ReferenceVideoURLs []string `json:"reference_video_urls,omitempty" help:"optional; up to 3 public HTTP(S) video URLs"`
	ReferenceAudioURLs []string `json:"reference_audio_urls,omitempty" help:"optional; up to 3 public HTTP(S) audio URLs; requires reference images or videos"`
	CallbackURL        string   `json:"callback_url,omitempty" help:"optional; URL that receives completion callback"`
}

type ImageToVideoParams struct {
	Model              Model  `json:"model" help:"required; model slug"`
	Prompt             string `json:"prompt" help:"required; 1 to 7000 characters"`
	FirstFrameImageURL string `json:"first_frame_image_url,omitempty" help:"optional; public HTTP(S) first-frame image URL; first or last frame required"`
	LastFrameImageURL  string `json:"last_frame_image_url,omitempty" help:"optional; public HTTP(S) last-frame image URL; first or last frame required"`
	DurationSeconds    int    `json:"duration_seconds,omitempty" help:"optional; 4 to 15 seconds; default 6"`
	OutputResolution   string `json:"output_resolution,omitempty" help:"optional; 768p or 2k; default 2k"`
	CallbackURL        string `json:"callback_url,omitempty" help:"optional; URL that receives completion callback"`
}
