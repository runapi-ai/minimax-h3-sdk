# frozen_string_literal: true

module RunApi
  module MiniMaxH3
    # MiniMax H3 text-to-video and image-to-video generation API client.
    #
    # @example
    #   client = RunApi::MiniMaxH3::Client.new(api_key: "your-api-key")
    #   result = client.text_to_video.run(
    #     model: "minimax-h3",
    #     prompt: "A timelapse of cherry blossoms blooming in a Japanese garden"
    #   )
    class Client < RunApi::Core::Client
      # @return [Resources::TextToVideo] Text-to-video generation operations.
      attr_reader :text_to_video
      # @return [Resources::ImageToVideo] Image-to-video generation operations.
      attr_reader :image_to_video

      def initialize(api_key: nil, **options)
        super

        @text_to_video = Resources::TextToVideo.new(http)
        @image_to_video = Resources::ImageToVideo.new(http)
      end
    end
  end
end
