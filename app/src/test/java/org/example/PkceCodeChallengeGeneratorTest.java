/*
 * Copyright 2025 kota2and3kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PkceCodeChallengeGeneratorTest {

  private PkceCodeChallengeGenerator generator;

  @BeforeEach
  void setUp() {
    generator = new PkceCodeChallengeGenerator();
  }

  @Test
  void validateArguments_ValidSingleArgument_NoException() {
    String[] validArgs = {"dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"};
    assertDoesNotThrow(() -> generator.validateArguments(validArgs));
  }

  @Test
  void validateArguments_NoArguments_ThrowsException() {
    String[] emptyArgs = {};
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> generator.validateArguments(emptyArgs));
    assertEquals("Error: Please provide a single string as an argument.", exception.getMessage());
  }

  @Test
  void validateArguments_MultipleArguments_ThrowsException() {
    String[] multipleArgs = {"arg1", "arg2"};
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> generator.validateArguments(multipleArgs));
    assertEquals("Error: Please provide a single string as an argument.", exception.getMessage());
  }

  @Test
  void validateArguments_TooShortString_ThrowsException() {
    String[] shortArgs = {"a".repeat(42)}; // 42 characters (less than 43)
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> generator.validateArguments(shortArgs));
    assertTrue(
        exception.getMessage().contains("Input string must be between 43 and 128 characters"));
  }

  @Test
  void validateArguments_TooLongString_ThrowsException() {
    String[] longArgs = {"a".repeat(129)}; // 129 characters (more than 128)
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> generator.validateArguments(longArgs));
    assertTrue(
        exception.getMessage().contains("Input string must be between 43 and 128 characters"));
  }

  @Test
  void validateArguments_ExactlyMinLength_NoException() {
    String[] minArgs = {"a".repeat(43)}; // exactly 43 characters
    assertDoesNotThrow(() -> generator.validateArguments(minArgs));
  }

  @Test
  void validateArguments_ExactlyMaxLength_NoException() {
    String[] maxArgs = {"a".repeat(128)}; // exactly 128 characters
    assertDoesNotThrow(() -> generator.validateArguments(maxArgs));
  }

  @Test
  void validateArguments_InvalidCharacter1_ThrowsException() {
    String[] invalidArgs = {
      "invalid_verifier!invalid_verifier!invalid_verifier"
    }; // contains invalid characters
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> generator.validateArguments(invalidArgs));
    assertTrue(exception.getMessage().contains("Input string contains invalid characters"));
  }

  @Test
  void validateArguments_InvalidCharacter2_ThrowsException() {
    String[] invalidArgs = {
      "invalid_verifier@invalid_verifier@invalid_verifier"
    }; // contains invalid characters
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> generator.validateArguments(invalidArgs));
    assertTrue(exception.getMessage().contains("Input string contains invalid characters"));
  }

  @Test
  void validateArguments_InvalidCharacter3_ThrowsException() {
    String[] invalidArgs = {
      "invalid_verifier#invalid_verifier#invalid_verifier"
    }; // contains invalid characters
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> generator.validateArguments(invalidArgs));
    assertTrue(exception.getMessage().contains("Input string contains invalid characters"));
  }

  @Test
  void generateCodeChallenge_ValidInput_ReturnsExpectedResult() {
    String codeVerifier = "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"; // Example from RFC 7636
    String expected = "E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM"; // Example from RFC 7636

    String result = generator.generateCodeChallenge(codeVerifier);

    assertEquals(expected, result);
  }

  @Test
  void generateCodeChallenge_SampleValidInput1_ReturnsExpectedResult() {
    String codeVerifier = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    String expected = "FgtOQz44TgXlN9xZtGf3yyQD8CFNsVxdtYhio_EVbS4";

    String result = generator.generateCodeChallenge(codeVerifier);

    assertEquals(expected, result);
  }

  @Test
  void generateCodeChallenge_SampleValidInput2_ReturnsExpectedResult() {
    String codeVerifier = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
    String expected = "wBqbqg9rWaWlI-ub2ADfzVaBX5Jmql-SPpvBUg9nEvI";

    String result = generator.generateCodeChallenge(codeVerifier);

    assertEquals(expected, result);
  }

  @Test
  void generateCodeChallenge_SampleValidInput3_ReturnsExpectedResult() {
    String codeVerifier = "cccccccccccccccccccccccccccccccccccccccccccccccccc";
    String expected = "Xea_f3PjTKBQFpBtUKTzztcpv_2f0b7vsODGoLXBNuQ";

    String result = generator.generateCodeChallenge(codeVerifier);

    assertEquals(expected, result);
  }

  @Test
  void generateCodeChallenge_ConsistentResults_SameInputProducesSameOutput() {
    String codeVerifier = "consistent-test-verifier-consistent-test-verifier";

    String result1 = generator.generateCodeChallenge(codeVerifier);
    String result2 = generator.generateCodeChallenge(codeVerifier);

    assertEquals(result1, result2);
  }

  @Test
  void generateCodeChallenge_DifferentInputs_ProduceDifferentOutputs() {
    String codeVerifier1 = "different-test-verifier-different-test-verifier-1";
    String codeVerifier2 = "different-test-verifier-different-test-verifier-2";

    String result1 = generator.generateCodeChallenge(codeVerifier1);
    String result2 = generator.generateCodeChallenge(codeVerifier2);

    assertNotEquals(result1, result2);
  }

  @Test
  void generateCodeChallenge_ValidBase64UrlEncoding_NoPaddingCharacters() {
    String codeVerifier = "test-verifier-for-base64url-encoding";

    String result = generator.generateCodeChallenge(codeVerifier);

    assertFalse(result.contains("="));
  }
}
