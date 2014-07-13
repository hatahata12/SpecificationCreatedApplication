package itipxnozakki.com.lib.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

abstract public class Reader {

    private String url;
    protected boolean httpsFlag;

    public Reader(String _url, boolean httpsFlag) {
        this.url = _url;
        this.httpsFlag = httpsFlag;
    }

    protected StringBuffer getStreamDataByHttpOrHttps() {
    	if (this.httpsFlag) {
    		return this.getHttpsStreamData();
    	} else {
    		return this.getHttpStreamData();
    	}
    }

    protected StringBuffer getHttpsStreamData() {

        try {

            TrustManager[] tm = { new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }
                    public void checkServerTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }
                }
            };
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, tm, null);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname,
                        SSLSession session) {
                    return true;
                }
            });

            URL connectUrl = new URL(this.url);
            HttpsURLConnection urlconn = (HttpsURLConnection) connectUrl
                    .openConnection();
            urlconn.setDoOutput(true);
            urlconn.setRequestMethod("GET");
            urlconn.setSSLSocketFactory(sslcontext.getSocketFactory());
            urlconn.connect();

            //データの読み取り
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream(), "utf8"));

            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line + '\n');
            }
            reader.close();
            urlconn.disconnect();

            return sb;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return null;

    }

    protected StringBuffer getHttpStreamData() {


		try {

			System.out.println(this.url);
		    URL connectUrl = new URL(this.url);
		    HttpURLConnection urlconn;

			urlconn = (HttpURLConnection) connectUrl
				        .openConnection();
	        urlconn.setDoOutput(true);
	        urlconn.setRequestMethod("GET");
	        urlconn.connect();

	        //データの読み取り
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                urlconn.getInputStream(), "utf8"));

	        String line;
	        StringBuffer sb = new StringBuffer();
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + '\n');
	        }

	        reader.close();
	        urlconn.disconnect();

	        return sb;

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        return null;
    }

}
