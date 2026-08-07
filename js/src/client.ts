import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { ImageToVideo } from './resources/image-to-video';
import { TextToVideo } from './resources/text-to-video';

export class MiniMaxH3Client extends BaseClient {
  public readonly textToVideo: TextToVideo;
  public readonly imageToVideo: ImageToVideo;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.textToVideo = new TextToVideo(this.http);
    this.imageToVideo = new ImageToVideo(this.http);
  }
}
