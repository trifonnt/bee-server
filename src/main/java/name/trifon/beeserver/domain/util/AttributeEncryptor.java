package name.trifon.beeserver.domain.util;

import java.security.Key;
import java.security.InvalidKeyException;

import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

//@Trifon
/*
 *   DATABASE COLUMN-LEVEL ENCRYPTION WITH SPRING DATA JPA
 * - https://sultanov.dev/blog/database-column-level-encryption-with-spring-data-jpa/
 * 
 * Usage: 
 * 
 * @Convert(converter = AttributeEncryptor.class)
 * private String name;
 * 
 */
@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

	private static final String AES = "AES";
	private static final String SECRET = "4<$+Tb[,]{ZJ1JlMn5<ALLpLY$0SFg5s"; //MUST be 32 characters long!!!

	private final Key key;
	private final Cipher cipher;

	public AttributeEncryptor() throws Exception {
		key = new SecretKeySpec(SECRET.getBytes(), AES);
		cipher = Cipher.getInstance(AES);
	}

	@Override
	public String convertToDatabaseColumn(String attribute) throws IllegalStateException {
		try {
			if (attribute != null) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
				return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
			} else {
				return null;
			}
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) throws IllegalStateException {
		try {
			if (dbData != null) {
				cipher.init(Cipher.DECRYPT_MODE, key);
				return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
			} else {
				return null;
			}
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			throw new IllegalStateException(e);
		}
	}
}