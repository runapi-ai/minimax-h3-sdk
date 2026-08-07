package ai.runapi.minimaxh3.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for image to video operations. */
public final class ImageToVideoModel extends Minimaxh3Value {
  /** minimax-h3 model slug. */
  public static final ImageToVideoModel MINIMAX_H3 = new ImageToVideoModel("minimax-h3");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public ImageToVideoModel(String value) {
    super(value);
  }
}
