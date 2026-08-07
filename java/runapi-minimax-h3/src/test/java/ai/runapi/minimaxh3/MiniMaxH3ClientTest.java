package ai.runapi.minimaxh3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.billing.TaskBillingFacts;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.minimaxh3.types.CompletedTextToVideoResponse;
import ai.runapi.minimaxh3.types.TextToVideoResponse;
import ai.runapi.minimaxh3.types.CompletedImageToVideoResponse;
import ai.runapi.minimaxh3.types.CompletedTextToVideoResponse;
import ai.runapi.minimaxh3.types.ImageToVideoModel;
import ai.runapi.minimaxh3.types.ImageToVideoParams;
import ai.runapi.minimaxh3.types.ImageToVideoResponse;
import ai.runapi.minimaxh3.types.TextToVideoModel;
import ai.runapi.minimaxh3.types.TextToVideoParams;
import ai.runapi.minimaxh3.types.TextToVideoResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MiniMaxH3ClientTest {
  @Test
  void rejectsAudioOnlyReferences() {
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(new CapturingTransport("{}" )).build();

    ValidationException error = assertThrows(ValidationException.class, () -> client.textToVideo().create(
        TextToVideoParams.builder()
            .model(TextToVideoModel.MINIMAX_H3)
            .prompt("Continue the performance")
            .referenceAudioUrls(Arrays.asList("https://cdn.runapi.ai/public/samples/voice.mp3"))
            .build()));

    assertEquals("one of reference_image_urls, reference_video_urls is required when reference_audio_urls is present", error.getMessage());
  }

  @Test
  void rejectsAdaptiveAspectRatioWithoutReferenceMedia() {
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(new CapturingTransport("{}" )).build();

    ValidationException error = assertThrows(ValidationException.class, () -> client.textToVideo().create(
        TextToVideoParams.builder()
            .model(TextToVideoModel.MINIMAX_H3)
            .prompt("A cinematic landscape")
            .aspectRatio("adaptive")
            .build()));

    assertEquals("aspect_ratio must be one of: 21:9, 16:9, 4:3, 1:1, 3:4, 9:16 when reference_image_urls is absent and reference_video_urls is absent", error.getMessage());
  }

  @Test
  void requiresAspectRatioWithoutReferenceMedia() {
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(new CapturingTransport("{}" )).build();

    ValidationException error = assertThrows(ValidationException.class, () -> client.textToVideo().create(
        TextToVideoParams.builder()
            .model(TextToVideoModel.MINIMAX_H3)
            .prompt("A cinematic landscape")
            .build()));

    assertEquals("aspect_ratio is required when reference_image_urls is absent and reference_video_urls is absent", error.getMessage());
  }

  @Test
  void requiresFirstOrLastFrame() {
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(new CapturingTransport("{}" )).build();

    ValidationException error = assertThrows(ValidationException.class, () -> client.imageToVideo().create(
        ImageToVideoParams.builder()
            .model(ImageToVideoModel.MINIMAX_H3)
            .prompt("Animate the scene")
            .build()));

    assertEquals("one of first_frame_image_url, last_frame_image_url is required", error.getMessage());
  }

  @Test
  void builderCreatesClientAndUniversalResources() {
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").build();

    assertNotNull(client.textToVideo());
    assertNotNull(client.files());
    assertNotNull(client.account());
    assertNotNull(client.pricing());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new TextToVideoModel("minimax-h3"));

    assertEquals("\"minimax-h3\"", json);
    assertEquals(new TextToVideoModel("minimax-h3"), Json.mapper().readValue(json, TextToVideoModel.class));
  }

  @Test
  void createSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_123\",\"status\":\"processing\"}");
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(transport).build();

    client.textToVideo().create(
        TextToVideoParams.builder()
            .model(TextToVideoModel.MINIMAX_H3)
            .prompt("A small red cube on a plain white table, studio product photo")
            .aspectRatio("16:9")
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/minimax_h3/text_to_video", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertNotNull(body);

  }

  @Test
  void getDecodesTaskResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_456\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"billing\":{\"reservation\":{\"amount_cents\":12},\"settlement\":{\"charged_amount_cents\":11,\"amount_micro_cents\":1050000},\"refund\":{\"refunded_at\":\"2026-07-23T12:00:00.000000Z\"}},\"custom\":\"kept\"}");
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(transport).build();

    TextToVideoResponse response = client.textToVideo().get("task_456");

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/minimax_h3/text_to_video/task_456", transport.request.getPath());
    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getVideos());
    assertEquals("kept", response.extraFields().get("custom").asText());
    TaskBillingFacts billing = response.getBilling();
    assertNotNull(billing);
    assertEquals(Long.valueOf(12), billing.getReservation().getAmountCents());
    assertEquals(Long.valueOf(11), billing.getSettlement().getChargedAmountCents());
    assertEquals(Long.valueOf(1050000), billing.getSettlement().getAmountMicroCents());
    assertEquals("2026-07-23T12:00:00.000000Z", billing.getRefund().getRefundedAt());
  }

  @Test
  void runPollsUntilCompletedAndKeepsExtraFields() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_789\",\"status\":\"processing\"}",
        "{\"id\":\"task_789\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(transport).build();

    CompletedTextToVideoResponse response = client.textToVideo().run(
        TextToVideoParams.builder()
            .model(TextToVideoModel.MINIMAX_H3)
            .prompt("A small red cube on a plain white table, studio product photo")
            .aspectRatio("16:9")
            .build(),
        RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());

    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getVideos());
    assertEquals("kept", response.extraFields().get("custom").asText());
    assertEquals(2, transport.calls);
  }

  @Test
  void runRejectsCompletedResponseMissingResultField() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_missing\",\"status\":\"processing\"}",
        "{\"id\":\"task_missing\",\"status\":\"completed\"}");
    MiniMaxH3Client client = MiniMaxH3Client.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () -> client.textToVideo().run(
                TextToVideoParams.builder()
                    .model(TextToVideoModel.MINIMAX_H3)
                    .prompt("A small red cube on a plain white table, studio product photo")
                    .aspectRatio("16:9")
                    .build(),
            RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
  }

    @Test
    void coversImagetovideoResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_image_to_video\",\"status\":\"processing\"}");
      MiniMaxH3Client createClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.imageToVideo().create(
              ImageToVideoParams.builder()
                  .model(ImageToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .firstFrameImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_image_to_video_options\",\"status\":\"processing\"}");
      MiniMaxH3Client createWithOptionsClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.imageToVideo().create(
              ImageToVideoParams.builder()
                  .model(ImageToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .firstFrameImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_image_to_video\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client getClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.imageToVideo().get("task_image_to_video"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_image_to_video_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client getWithOptionsClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.imageToVideo().get("task_image_to_video_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_image_to_video_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_image_to_video_run\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client runClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedImageToVideoResponse runResponse = runClient.imageToVideo().run(
              ImageToVideoParams.builder()
                  .model(ImageToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .firstFrameImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_image_to_video_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_image_to_video_run_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client runWithOptionsClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.imageToVideo().run(
              ImageToVideoParams.builder()
                  .model(ImageToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .firstFrameImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversTexttovideoResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_text_to_video\",\"status\":\"processing\"}");
      MiniMaxH3Client createClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.textToVideo().create(
              TextToVideoParams.builder()
                  .model(TextToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .aspectRatio("16:9")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_video_options\",\"status\":\"processing\"}");
      MiniMaxH3Client createWithOptionsClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.textToVideo().create(
              TextToVideoParams.builder()
                  .model(TextToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .aspectRatio("16:9")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_text_to_video\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client getClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.textToVideo().get("task_text_to_video"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_video_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client getWithOptionsClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.textToVideo().get("task_text_to_video_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_video_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_video_run\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client runClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedTextToVideoResponse runResponse = runClient.textToVideo().run(
              TextToVideoParams.builder()
                  .model(TextToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .aspectRatio("16:9")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_video_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_video_run_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      MiniMaxH3Client runWithOptionsClient = MiniMaxH3Client.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.textToVideo().run(
              TextToVideoParams.builder()
                  .model(TextToVideoModel.MINIMAX_H3)
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .aspectRatio("16:9")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }
}
