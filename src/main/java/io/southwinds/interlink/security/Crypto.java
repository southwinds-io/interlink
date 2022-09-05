/*
    Interlink Configuration Management Database
    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

    Contributors to this project, hereby assign copyright in their code to the
    project, to be licensed under the same terms as the rest of the code.
*/
package io.southwinds.interlink.security;

import org.springframework.stereotype.Component;

import java.util.Date;

//
// Interface for symmetric encryption algorithms
//
@Component
public interface Crypto {
    // returns a new secret key
    String newKey();

    // encrypts a plain text
    byte[] encrypt(String plaintext);

    // decrypts a cipher
    byte[] decrypt(byte[] encryptedData, short keyIx);

    // gets the index of the encryption key to use - 0 = no key, 1 or 2 for the two available keys
    short getKeyIx();

    // gets the index of the default key
    short getDefaultKeyIx();

    // gets the date after which the default key is no longer valid
    String getDefaultKeyExpiry();
}
