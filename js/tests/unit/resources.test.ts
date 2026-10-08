import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { TextToVideo } from '../../src/resources/text-to-video';
import { ImageToVideo } from '../../src/resources/image-to-video';

describe('MiniMax H3 resources', () => {
  const mockHttp: HttpClient = { request: vi.fn() };

  beforeEach(() => vi.clearAllMocks());

  it('creates reference-to-video through the text-to-video endpoint', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-1' });

    await new TextToVideo(mockHttp).create({
      model: 'minimax-h3',
      prompt: 'Carry the character and voice into a new scene',
      reference_image_urls: ['https://cdn.runapi.ai/public/samples/image.jpg'],
      reference_audio_urls: ['https://cdn.runapi.ai/public/samples/voice.mp3'],
      aspect_ratio: 'adaptive',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/minimax_h3/text_to_video', {
      body: {
        model: 'minimax-h3',
        prompt: 'Carry the character and voice into a new scene',
        reference_image_urls: ['https://cdn.runapi.ai/public/samples/image.jpg'],
        reference_audio_urls: ['https://cdn.runapi.ai/public/samples/voice.mp3'],
        aspect_ratio: 'adaptive',
      },
    });
  });

  it('creates image-to-video with first and last frames', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-2' });

    await new ImageToVideo(mockHttp).create({
      model: 'minimax-h3',
      prompt: 'Move smoothly between the supplied frames',
      first_frame_image_url: 'https://cdn.runapi.ai/public/samples/image.jpg',
      last_frame_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
      duration_seconds: 8,
      output_resolution: '2k',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/minimax_h3/image_to_video', {
      body: {
        model: 'minimax-h3',
        prompt: 'Move smoothly between the supplied frames',
        first_frame_image_url: 'https://cdn.runapi.ai/public/samples/image.jpg',
        last_frame_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
        duration_seconds: 8,
        output_resolution: '2k',
      },
    });
  });
});
