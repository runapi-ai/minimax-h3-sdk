import type { ActionSchema, HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
import type {
  CompletedMiniMaxH3VideoResponse,
  MiniMaxH3TextToVideoParams,
  MiniMaxH3VideoResponse,
  TaskCreateResponse,
} from '../types';

const ENDPOINT = '/api/v1/minimax_h3/text_to_video';

export class TextToVideo {
  constructor(private readonly http: HttpClient) {}

  async run(
    params: MiniMaxH3TextToVideoParams,
    options?: RequestOptions & PollingOptions
  ): Promise<CompletedMiniMaxH3VideoResponse> {
    const { id } = await this.create(params, options);
    return pollUntilComplete<MiniMaxH3VideoResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    }) as Promise<CompletedMiniMaxH3VideoResponse>;
  }

  async create(params: MiniMaxH3TextToVideoParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    validateParams(contract['text-to-video'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, { body, ...options });
  }

  async get(id: string, options?: RequestOptions): Promise<MiniMaxH3VideoResponse> {
    return this.http.request<MiniMaxH3VideoResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
