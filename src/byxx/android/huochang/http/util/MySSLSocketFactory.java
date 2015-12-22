/**
 * 
 */
package byxx.android.huochang.http.util;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author WGary Τ����
 * 2015-6-6
 */
public class MySSLSocketFactory extends SSLSocketFactory{

	static{
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			mySSLSocketFactory = new MySSLSocketFactory( trustStore,createSContext());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static MySSLSocketFactory mySSLSocketFactory = null;



	private static SSLContext createSContext(){
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return null;
		}
		return sslcontext;
	}

	@SuppressWarnings("deprecation")
	private MySSLSocketFactory(KeyStore truststore, SSLContext sslContext)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(truststore);
		SSLContext mSSLContext = sslContext;
		this.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	public static MySSLSocketFactory getInstance(){
		try{
			if(mySSLSocketFactory != null){
				return mySSLSocketFactory;
			}else{
				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);
				return mySSLSocketFactory = new MySSLSocketFactory( trustStore,createSContext());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}