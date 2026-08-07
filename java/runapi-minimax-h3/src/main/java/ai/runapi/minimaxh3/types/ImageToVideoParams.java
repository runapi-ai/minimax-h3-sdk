package ai.runapi.minimaxh3.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for image to video operations. */
public final class ImageToVideoParams {
  private final String model;
  private final String prompt;
  private final String firstFrameImageUrl;
  private final String lastFrameImageUrl;
  private final Integer durationSeconds;
  private final String outputResolution;
  private final String callbackUrl;

  private ImageToVideoParams(Builder builder) {
    this.model = Minimaxh3ParamUtils.requireNonBlankTrim(builder.model, "model");
    this.prompt = Minimaxh3ParamUtils.requireNonBlank(builder.prompt, "prompt");
    this.firstFrameImageUrl = builder.firstFrameImageUrl;
    this.lastFrameImageUrl = builder.lastFrameImageUrl;
    this.durationSeconds = builder.durationSeconds;
    this.outputResolution = builder.outputResolution;
    this.callbackUrl = builder.callbackUrl;
  }

  /** Creates a new ImageToVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "minimax-h3/image-to-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", Minimaxh3ParamUtils.wireValue(model));
    raw.put("prompt", Minimaxh3ParamUtils.wireValue(prompt));
    raw.put("first_frame_image_url", Minimaxh3ParamUtils.wireValue(firstFrameImageUrl));
    raw.put("last_frame_image_url", Minimaxh3ParamUtils.wireValue(lastFrameImageUrl));
    raw.put("duration_seconds", Minimaxh3ParamUtils.wireValue(durationSeconds));
    raw.put("output_resolution", Minimaxh3ParamUtils.wireValue(outputResolution));
    raw.put("callback_url", Minimaxh3ParamUtils.wireValue(callbackUrl));
    return Minimaxh3ParamUtils.compact(raw);
  }



  /** Builder for {@link ImageToVideoParams}. */
  public static final class Builder {
    private String model;
    private String prompt;
    private String firstFrameImageUrl;
    private String lastFrameImageUrl;
    private Integer durationSeconds;
    private String outputResolution;
    private String callbackUrl;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(ImageToVideoModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = Minimaxh3ParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }


    /** Sets the text prompt. */
    public Builder prompt(String value) {
      this.prompt = Minimaxh3ParamUtils.requireNonBlank(value, "prompt");
      return this;
    }

    /** Sets the first frame image URL. */
    public Builder firstFrameImageUrl(String value) {
      this.firstFrameImageUrl = Minimaxh3ParamUtils.requireNonBlank(value, "firstFrameImageUrl");
      return this;
    }

    /** Sets the last frame image URL. */
    public Builder lastFrameImageUrl(String value) {
      this.lastFrameImageUrl = Minimaxh3ParamUtils.requireNonBlank(value, "lastFrameImageUrl");
      return this;
    }

    /** Sets the duration in seconds. */
    public Builder durationSeconds(int value) {
      this.durationSeconds = value;
      return this;
    }

    /** Sets the output resolution. */
    public Builder outputResolution(String value) {
      this.outputResolution = Minimaxh3ParamUtils.requireNonBlank(value, "outputResolution");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = Minimaxh3ParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Builds immutable image to video parameters. */
    public ImageToVideoParams build() {
      return new ImageToVideoParams(this);
    }
  }
}
