package ai.runapi.minimaxh3.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to video operations. */
public final class TextToVideoParams {
  private final String model;
  private final String prompt;
  private final Integer durationSeconds;
  private final String outputResolution;
  private final String aspectRatio;
  private final List<String> referenceImageUrls;
  private final List<String> referenceVideoUrls;
  private final List<String> referenceAudioUrls;
  private final String callbackUrl;

  private TextToVideoParams(Builder builder) {
    this.model = Minimaxh3ParamUtils.requireNonBlankTrim(builder.model, "model");
    this.prompt = Minimaxh3ParamUtils.requireNonBlank(builder.prompt, "prompt");
    this.durationSeconds = builder.durationSeconds;
    this.outputResolution = builder.outputResolution;
    this.aspectRatio = builder.aspectRatio;
    this.referenceImageUrls = Minimaxh3ParamUtils.strings(builder.referenceImageUrls);
    this.referenceVideoUrls = Minimaxh3ParamUtils.strings(builder.referenceVideoUrls);
    this.referenceAudioUrls = Minimaxh3ParamUtils.strings(builder.referenceAudioUrls);
    this.callbackUrl = builder.callbackUrl;
  }

  /** Creates a new TextToVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "minimax-h3/text-to-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", Minimaxh3ParamUtils.wireValue(model));
    raw.put("prompt", Minimaxh3ParamUtils.wireValue(prompt));
    raw.put("duration_seconds", Minimaxh3ParamUtils.wireValue(durationSeconds));
    raw.put("output_resolution", Minimaxh3ParamUtils.wireValue(outputResolution));
    raw.put("aspect_ratio", Minimaxh3ParamUtils.wireValue(aspectRatio));
    raw.put("reference_image_urls", Minimaxh3ParamUtils.wireValue(referenceImageUrls));
    raw.put("reference_video_urls", Minimaxh3ParamUtils.wireValue(referenceVideoUrls));
    raw.put("reference_audio_urls", Minimaxh3ParamUtils.wireValue(referenceAudioUrls));
    raw.put("callback_url", Minimaxh3ParamUtils.wireValue(callbackUrl));
    return Minimaxh3ParamUtils.compact(raw);
  }



  /** Builder for {@link TextToVideoParams}. */
  public static final class Builder {
    private String model;
    private String prompt;
    private Integer durationSeconds;
    private String outputResolution;
    private String aspectRatio;
    private List<String> referenceImageUrls;
    private List<String> referenceVideoUrls;
    private List<String> referenceAudioUrls;
    private String callbackUrl;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToVideoModel value) {
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

    /** Sets the output aspect ratio. */
    public Builder aspectRatio(String value) {
      this.aspectRatio = Minimaxh3ParamUtils.requireNonBlank(value, "aspectRatio");
      return this;
    }

    /** Sets the reference image URLs. */
    public Builder referenceImageUrls(List<String> value) {
      this.referenceImageUrls = value;
      return this;
    }

    /** Sets the reference video URLs. */
    public Builder referenceVideoUrls(List<String> value) {
      this.referenceVideoUrls = value;
      return this;
    }

    /** Sets the reference audio URLs. */
    public Builder referenceAudioUrls(List<String> value) {
      this.referenceAudioUrls = value;
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = Minimaxh3ParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Builds immutable text to video parameters. */
    public TextToVideoParams build() {
      return new TextToVideoParams(this);
    }
  }
}
