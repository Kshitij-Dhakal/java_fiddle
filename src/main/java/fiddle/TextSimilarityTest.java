package fiddle;

import org.apache.commons.text.similarity.JaccardSimilarity;

public class TextSimilarityTest {
  public static void main(String[] args) {
    // Example texts
    String generatedText = "This is the AI-generated text.";
    String referenceText = "This is the reference text.";

    var apply = new JaccardSimilarity().apply(generatedText, referenceText);
    System.out.println(apply);
  }
}
