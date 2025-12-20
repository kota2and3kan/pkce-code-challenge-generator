# PKCE Code Challenge Generator

This is a simple Java application that generates a PKCE (Proof Key for Code Exchange) code challenge. For testing purposes only. Not for production use.

## Usage

### Download naive image (executable file) built by GraalVM and run

```shell
# Linux AMD64
$ curl -OL https://github.com/kota2and3kan/pkce-code-challenge-generator/releases/download/<VERSION>/pkce-code-challenge-generator-linux-amd64.tar.gz

# MacOS ARM64
$ curl -OL https://github.com/kota2and3kan/pkce-code-challenge-generator/releases/download/<VERSION>/pkce-code-challenge-generator-macos-arm64.tar.gz
```
```shell
# Linux AMD64
$ tar xvf pkce-code-challenge-generator-linux-amd64.tar.gz

# MacOS ARM64
$ tar xvf pkce-code-challenge-generator-macos-arm64.tar.gz
```
```shell
$ ./pkce-code-challenge-generator <CODE_VERIFIER>
```

- Example

  ```shell
  $ ./pkce-code-challenge-generator dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
  Code Verifier  : dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
  Code Challenge : E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM
    ```

### Build and run in your local by using `./gradlew run` command

```shell
$ git clone https://github.com/kota2and3kan/pkce-code-challenge-generator.git
```
```shell
$ cd pkce-code-challenge-generator/
```
```shell
$ ./gradlew run -q --arge "<CODE_VERIFIER>"
```

- Example

  ```shell
  $ ./gradlew run -q --args "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"
  Code Verifier  : dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
  Code Challenge : E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM
  ```

### Build native image by using GraalVM and run

```shell
$ git clone https://github.com/kota2and3kan/pkce-code-challenge-generator.git
```
```shell
$ cd pkce-code-challenge-generator/
```
```shell
$ docker run --rm -v $(pwd):/build -w /build \
  --entrypoint /bin/bash \
  ghcr.io/graalvm/native-image-community:21-muslib \
  -c "microdnf install -y findutils && ./gradlew nativeCompile"
```
```shell
$ ./app/build/native/nativeCompile/pkce-code-challenge-generator <CODE_VERIFIER>
```

- Example

  ```shell
  $ ./app/build/native/nativeCompile/pkce-code-challenge-generator dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
  Code Verifier  : dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
  Code Challenge : E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM
  ```

## Reference

- [RFC 7636 - Proof Key for Code Exchange by OAuth Public Clients](https://datatracker.ietf.org/doc/html/rfc7636)

- [Keycloak](https://github.com/keycloak/keycloak) PKCE Implementation:
  - [PkceUtils](https://github.com/keycloak/keycloak/blob/26.4.7/services/src/main/java/org/keycloak/protocol/oidc/utils/PkceUtils.java) 
  - [HashUtils](https://github.com/keycloak/keycloak/blob/26.4.7/core/src/main/java/org/keycloak/jose/jws/crypto/HashUtils.java)
  - [Base64Url](https://github.com/keycloak/keycloak/blob/26.4.7/common/src/main/java/org/keycloak/common/util/Base64Url.java)
