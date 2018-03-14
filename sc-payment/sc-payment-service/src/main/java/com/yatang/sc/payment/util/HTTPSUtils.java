package com.yatang.sc.payment.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import okhttp3.OkHttpClient;
import org.apache.http.conn.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;

/**
 * Created by yuwei on 2017/7/14.
 */
public class HTTPSUtils {
    private static final Logger logger = LoggerFactory.getLogger(HTTPSUtils.class.getClass());

    private static LoadingCache<String, OkHttpClient> sslClientCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(
                    new CacheLoader<String, OkHttpClient>() {
                        public OkHttpClient load(String key) throws IOException {
                            int indexOf = key.indexOf("->");
                            return createSSLClient(key.substring(0, indexOf), key.substring(indexOf + "->".length()));
                        }
                    });

    public static OkHttpClient getSSLClient(String pCertPath, String pCertPassword) throws ExecutionException {
        return sslClientCache.get(pCertPath + "->" + pCertPassword);
    }

    protected static OkHttpClient createSSLClient(String pCertPath, String pCertPassword) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(getSocketFactory(pCertPath, pCertPassword)).build();
        return client;
    }

    public static SSLSocketFactory getSocketFactory(String pCertPath, String pCertPassword) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(pCertPath)); // 得到证书的输入流
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            keyStore.load(inputStream, pCertPassword.toCharArray());//设置证书密码

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, pCertPassword.toCharArray()).build();
            return sslcontext.getSocketFactory();
        } catch (CertificateException e) {
            logger.error("Occur exception", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Occur exception", e);
        } catch (IOException pE) {
            logger.error("Occur exception", pE);
        } catch (KeyStoreException pE) {
            logger.error("Occur exception", pE);
        } catch (UnrecoverableKeyException pE) {
            logger.error("Occur exception", pE);
        } catch (KeyManagementException pE) {
            logger.error("Occur exception", pE);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException pE) {
                    logger.error("Occur exception", pE);
                }
            }
        }

        return null;
    }
}
