# frozen_string_literal: true

module RunApi
  module MiniMaxH3
    # Type definitions and constants for MiniMax H3 video generation.
    module Types
      # A generated video file with a download URL.
      class MediaUrl < RunApi::Core::BaseModel
        optional :url, String
      end

      # Video generation task status and results. Poll until status reaches a terminal state.
      class VideoTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :videos, [-> { MediaUrl }]
        optional :error, String
      end

      # Narrowed response returned after successful completion, guaranteeing videos is present.
      class CompletedVideoTaskResponse < VideoTaskResponse
        required :videos, [-> { MediaUrl }]
      end
    end
  end
end
