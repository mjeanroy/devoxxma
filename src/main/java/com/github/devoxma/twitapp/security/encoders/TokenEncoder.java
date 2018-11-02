package com.github.devoxma.twitapp.security.encoders;

public interface TokenEncoder {

	String encode(String decodedValue);

	String decode(String encodedValue);

}
