import type { AsyncTaskStatus, TaskResponse } from '@runapi.ai/core';

export type MiniMaxH3Model = 'minimax-h3';
export type MiniMaxH3DurationSeconds = 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15;
export type MiniMaxH3OutputResolution = '768p' | '2k';
export type MiniMaxH3AspectRatio = 'adaptive' | '21:9' | '16:9' | '4:3' | '1:1' | '3:4' | '9:16';

interface TaskCommonParams {
  model: MiniMaxH3Model;
  prompt: string;
  duration_seconds?: MiniMaxH3DurationSeconds;
  output_resolution?: MiniMaxH3OutputResolution;
  callback_url?: string;
}

export interface MiniMaxH3TextToVideoParams extends TaskCommonParams {
  aspect_ratio?: MiniMaxH3AspectRatio;
  reference_image_urls?: string[];
  reference_video_urls?: string[];
  reference_audio_urls?: string[];
}

export interface MiniMaxH3ImageToVideoParams extends TaskCommonParams {
  first_frame_image_url?: string;
  last_frame_image_url?: string;
}

export interface TaskCreateResponse {
  id: string;
}

export interface MediaUrl {
  url: string;
}

export interface MiniMaxH3VideoResponse extends TaskResponse {
  id: string;
  status: AsyncTaskStatus;
  videos?: MediaUrl[];
  error?: string;
  [key: string]: unknown;
}

export type CompletedMiniMaxH3VideoResponse = MiniMaxH3VideoResponse & {
  status: 'completed';
  videos: MediaUrl[];
};
