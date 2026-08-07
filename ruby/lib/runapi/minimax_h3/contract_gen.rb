# frozen_string_literal: true

module RunApi
  module MiniMaxH3
    CONTRACT = {
      "image-to-video" => {
        "models" => ["minimax-h3"],
        "fields_by_model" => {
          "minimax-h3" => {
            "duration_seconds" => {
              "enum" => [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
              "type" => "integer"
            },
            "model" => {
              "required" => true
            },
            "output_resolution" => {
              "enum" => ["768p", "2k"]
            },
            "prompt" => {
              "required" => true,
              "min" => 1,
              "max" => 7000,
              "length" => true
            }
          }
        },
        "rules" => [{
          "required_any" => ["first_frame_image_url", "last_frame_image_url"]
        }]
      },
      "text-to-video" => {
        "models" => ["minimax-h3"],
        "fields_by_model" => {
          "minimax-h3" => {
            "aspect_ratio" => {
              "enum" => ["adaptive", "21:9", "16:9", "4:3", "1:1", "3:4", "9:16"]
            },
            "duration_seconds" => {
              "enum" => [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
              "type" => "integer"
            },
            "model" => {
              "required" => true
            },
            "output_resolution" => {
              "enum" => ["768p", "2k"]
            },
            "prompt" => {
              "required" => true,
              "min" => 1,
              "max" => 7000,
              "length" => true
            },
            "reference_audio_urls" => {
              "max_items" => 3
            },
            "reference_image_urls" => {
              "max_items" => 9
            },
            "reference_video_urls" => {
              "max_items" => 3
            }
          }
        },
        "rules" => [{
          "when" => {
            "reference_audio_urls" => {
              "present" => true
            }
          },
          "required_any" => ["reference_image_urls", "reference_video_urls"]
        }, {
          "enum" => {
            "aspect_ratio" => ["21:9", "16:9", "4:3", "1:1", "3:4", "9:16"]
          },
          "when" => {
            "reference_image_urls" => {
              "present" => false
            },
            "reference_video_urls" => {
              "present" => false
            }
          },
          "required" => ["aspect_ratio"]
        }]
      }
    }.freeze
  end
end
