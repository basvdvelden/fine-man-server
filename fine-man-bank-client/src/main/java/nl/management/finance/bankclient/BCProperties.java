package nl.management.finance.bankclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
@ConfigurationProperties(prefix = "bc")
public class BCProperties {
    private String serverSslKeyStore;
    private String serverSslTrustStore;
    private String serverSslKeyStorePassword;
    private String serverSslTrustStorePassword;
    private String raboApiUrl;
    private String raboConsentUrl;
    private String tppCertificate;
    private String raboClientId;
    private String raboClientSecret;
    private String raboKeyId;
    private String raboPrivateKey;

    public String getServerSSLKeyStore() {
        return serverSslKeyStore;
    }

    public String getServerSSLTrustStore() {
        return serverSslTrustStore;
    }

    public String getServerSslKeyStorePassword() {
        return serverSslKeyStorePassword;
    }

    public String getServerSslTrustStorePassword() {
        return serverSslTrustStorePassword;
    }

    public String getRaboApiUrl() {
        return raboApiUrl;
    }

    public String getTPPCertificate() {
        return tppCertificate;
    }

    public String getRaboClientId() {
        return raboClientId;
    }

    public String getRaboKeyId() {
        return raboKeyId;
    }

    public String getRaboPrivateKey() {
        return raboPrivateKey;
    }

    public void setServerSSLKeyStore(String serverSSLKeyStore) {
        this.serverSslKeyStore = serverSSLKeyStore;
    }

    public void setServerSSLTrustStore(String serverSSLTrustStore) {
        this.serverSslTrustStore = serverSSLTrustStore;
    }

    public void setServerSslKeyStorePassword(String serverSSLKeyStorePwd) {
        this.serverSslKeyStorePassword = serverSSLKeyStorePwd;
    }

    public void setServerSslTrustStorePassword(String serverSSLTrustStorePwd) {
        this.serverSslTrustStorePassword = serverSSLTrustStorePwd;
    }

    public void setRaboApiUrl(String raboApiUrl) {
        this.raboApiUrl = raboApiUrl;
    }

    public void setTPPCertificate(String TPPCertificate) {
        this.tppCertificate = TPPCertificate;
    }

    public void setRaboClientId(String raboClientId) {
        this.raboClientId = raboClientId;
    }

    public void setRaboKeyId(String raboKeyId) {
        this.raboKeyId = raboKeyId;
    }

    public void setRaboPrivateKey(String raboPrivateKey) {
        this.raboPrivateKey = raboPrivateKey;
    }

    public String getRaboConsentUrl() {
        return raboConsentUrl;
    }

    public void setRaboConsentUrl(String raboConsentUrl) {
        this.raboConsentUrl = raboConsentUrl;
    }

    public String getRaboClientSecret() {
        return raboClientSecret;
    }

    public void setRaboClientSecret(String raboClientSecret) {
        this.raboClientSecret = raboClientSecret;
    }
}
