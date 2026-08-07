package ai.runapi.minimaxh3.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to video operations. */
public final class TextToVideoModel extends Minimaxh3Value {
  /** minimax-h3 model slug. */
  public static final TextToVideoModel MINIMAX_H3 = new TextToVideoModel("minimax-h3");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToVideoModel(String value) {
    super(value);
  }
}
