package fact.it.preferenceservice.decoder;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JwtDecoder {

    public static String decodeJwtAndGetSub(String jwtToken) {
        try {
            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);

            // Verify the signature (optional, but recommended)
            // Note: You may need to obtain the public key used to sign the JWT
            // and create a JWSVerifier to verify the signature.

            // Extract JWT claims
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            // Retrieve the 'sub' claim (subject)
            String sub = claimsSet.getSubject();

            return sub;
        } catch (Exception e) {
            // Handle exceptions (e.g., parsing or verification errors)
            System.out.println(e);
            return null;
        }
    }

    public static String getUserId(String jwtToken) {
        // Example usage
        String sub = decodeJwtAndGetSub(jwtToken);

        if (sub != null) {
            System.out.println("Subject (sub) from JWT: " + sub);
        } else {
            System.out.println("Failed to decode JWT or retrieve subject");
        }
        return sub;
    }

}