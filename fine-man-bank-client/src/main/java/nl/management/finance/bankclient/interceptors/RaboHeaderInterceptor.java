package nl.management.finance.bankclient.interceptors;

import nl.management.finance.bankclient.BCProperties;
import nl.management.finance.bankclient.IAppInterface;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class RaboHeaderInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RaboHeaderInterceptor.class);
    private final BCProperties properties;
    private final IAppInterface context;

    @Autowired
    public RaboHeaderInterceptor(BCProperties properties, @Lazy @Qualifier("external") IAppInterface context) {
        this.properties = properties;
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (request.url().toString().contains(properties.getRaboApiUrl())) {
            Date date = Calendar.getInstance().getTime();

            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateString = format.format(date);

            String digest = getDigest(request);

            String requestId = UUID.randomUUID().toString();

            Request.Builder builder = request.newBuilder()
                    .addHeader("TPP-Signature-Certificate", properties.getTPPCertificate())
                    .addHeader("X-Request-ID", requestId)
                    .addHeader("Digest", digest)
                    .addHeader("date", dateString)
                    .addHeader("x-ibm-client-id", properties.getRaboClientId())
                    .addHeader("signature", getSignatureHeader(requestId, digest, dateString,
                            request.header("TPP-Redirect-URI")));
            if (!request.url().toString().contains("oauth")) {
                builder.addHeader("Authorization",
                        context.getBankAuth().getTokenType() + " " + context.getBankAuth().getAccessToken());
            }
            request = builder.build();
        }
        return chain.proceed(request);
    }

    private String getDigest(Request request) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digestBytes = md.digest(getRequestBody(request).getBytes());

            return "sha-512=" + Base64.getEncoder().encodeToString(digestBytes);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("No such algorithm for message digest.", e);
        }
        return "";
    }

    private String getSignatureHeader(String requestId, String digest, String dateString, String redirectUri) {
        String signingString = String.format("date: %s\ndigest: %s\nx-request-id: %s%s",
                dateString, digest, requestId, redirectUri != null ? "\ntpp-redirect-uri: " + redirectUri : "");
        PrivateKey privateKey = getPrivateKey();
        String stringSignature = "";
        try {
            Signature signature = Signature.getInstance("SHA512withRSA");
            signature.initSign(privateKey);
            signature.update(signingString.getBytes());
            byte[] signatureBytes = signature.sign();

            stringSignature = Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("signature not found ", e);
        } catch (InvalidKeyException e) {
            LOG.error("initialized sign with invalid key ", e);
        } catch (SignatureException e) {
            LOG.error("signature exception thrown ", e);
        }
        return String.format(Locale.forLanguageTag("nl"),
                "keyId=\"%s\",algorithm=\"rsa-sha512\",headers=\"date digest x-request-id%s\",signature=\"%s\"",
                properties.getRaboKeyId(), redirectUri != null ? " tpp-redirect-uri" : "", stringSignature);
    }

    private PrivateKey getPrivateKey() {
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            byte[] encoded = Base64.getDecoder().decode(properties.getRaboPrivateKey().getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("no key factory for algorithm ", e);
        } catch (InvalidKeySpecException e) {
            LOG.error("invalid key spec", e);
        }
        return null;
    }

    private String getRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            LOG.error("Could not write req body to buffer ", e);
            return "";
        } catch (NullPointerException ignored) {
            return "";
        }
    }
}
