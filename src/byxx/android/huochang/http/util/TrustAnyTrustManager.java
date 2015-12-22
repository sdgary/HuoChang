/**
 * 
 */
package byxx.android.huochang.http.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author WGary 韦永城
 * 2015-6-6
 */
public class TrustAnyTrustManager implements X509TrustManager{

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
