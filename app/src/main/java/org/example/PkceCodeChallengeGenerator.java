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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PkceCodeChallengeGenerator {

  private static final Pattern VALID_CODE_VERIFIER_PATTERN =
      Pattern.compile("^[0-9a-zA-Z\\-\\.~_]+$");

  void validateArguments(String[] args) {
    // Validation 1
    if (args.length != 1) {
      throw new IllegalArgumentException("Error: Please provide a single string as an argument.");
    }

    // Validation 2
    if (args[0].length() > 128 || args[0].length() < 43) {
      throw new IllegalArgumentException(
          "Error: Input string must be between 43 and 128 characters.\n"
              + "See: https://datatracker.ietf.org/doc/html/rfc7636#section-4.1");
    }

    // Validation 3
    String codeVerifier = args[0];
    Matcher m = VALID_CODE_VERIFIER_PATTERN.matcher(codeVerifier);
    if (!m.matches()) {
      throw new IllegalArgumentException(
          "Error: Input string contains invalid characters.\n"
              + "Only characters from the unreserved set are allowed: ALPHA / DIGIT / \"-\" / \".\""
              + " / \"_\" / \"~\"\n"
              + "See: https://datatracker.ietf.org/doc/html/rfc7636#section-4.1");
    }
  }

  private byte[] convertToBytes(String str) {
    return str.getBytes(StandardCharsets.ISO_8859_1);
  }

  private byte[] generateSha256Hash(byte[] input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(input);
      return md.digest();
    } catch (Exception e) {
      throw new RuntimeException("Error: Generating SHA-256 hash failed", e);
    }
  }

  private String encodeBase64Url(byte[] input) {
    try {
      Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
      return encoder.encodeToString(input);
    } catch (Exception e) {
      throw new RuntimeException("Error: Base64 URL encoding failed", e);
    }
  }

  String generateCodeChallenge(String codeVerifier) {
    byte[] verifierBytes = convertToBytes(codeVerifier);
    byte[] sha256Hash = generateSha256Hash(verifierBytes);
    return encodeBase64Url(sha256Hash);
  }
}
