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

public class Main {
  public static void main(String[] args) {

    PkceCodeChallengeGenerator generator = new PkceCodeChallengeGenerator();

    try {
      // Validate input arguments
      generator.validateArguments(args);

      // Generate code challenge from the provided code verifier
      String codeVerifier = args[0];
      String codeChallenge = generator.generateCodeChallenge(codeVerifier);

      // Output the code verifier and code challenge into the console
      System.out.println("Code Verifier  : " + codeVerifier);
      System.out.println("Code Challenge : " + codeChallenge);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
