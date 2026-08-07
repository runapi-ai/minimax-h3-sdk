# frozen_string_literal: true

require "runapi/core"
require_relative "minimax_h3/types"
require_relative "minimax_h3/contract_gen"
require_relative "minimax_h3/resources/text_to_video"
require_relative "minimax_h3/resources/image_to_video"
require_relative "minimax_h3/client"

module RunApi
  module MiniMaxH3
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
    TaskFailedError = RunApi::Core::TaskFailedError
    TaskTimeoutError = RunApi::Core::TaskTimeoutError
  end
end
