# PKCE Code Challenge Generator

This is a simple Java application that generates a PKCE (Proof Key for Code Exchange) code challenge. For testing purposes only. Not for production use.

## Usage

```shell
$ git clone https://github.com/kota2and3kan/pkce-code-challenge-generator.git
$ cd pkce-code-challenge-generator/
$ ./gradlew run -q --arge "<CODE_VERIFIER>"
```

Replace `<CODE_VERIFIER>` with your preferred code verifier string.

## Example

```shell
$ ./gradlew run -q --args "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"
Code Verifier  : dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
Code Challenge : E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM
```

## Reference

- [RFC 7636 - Proof Key for Code Exchange by OAuth Public Clients](https://datatracker.ietf.org/doc/html/rfc7636)

- [Keycloak](https://github.com/keycloak/keycloak) PKCE Implementation:
  - [PkceUtils](https://github.com/keycloak/keycloak/blob/26.4.7/services/src/main/java/org/keycloak/protocol/oidc/utils/PkceUtils.java) 
  - [HashUtils](https://github.com/keycloak/keycloak/blob/26.4.7/core/src/main/java/org/keycloak/jose/jws/crypto/HashUtils.java)
  - [Base64Url](https://github.com/keycloak/keycloak/blob/26.4.7/common/src/main/java/org/keycloak/common/util/Base64Url.java)
