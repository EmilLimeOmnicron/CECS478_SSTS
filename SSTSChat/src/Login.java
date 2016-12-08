
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Login {
	
	private CloseableHttpClient httpclient;
	private String username;
	private String password;

	public Login() {
		httpclient = HttpClients.createDefault();
	}
	
	public Login(String name, String pass) {
		httpclient = HttpClients.createDefault();
		username = name;
		password = pass;
	}
	
	public String Execute() {
		String token = "";
		
		try {
		    System.out.println("POST ---");
			HttpPost httpPost = new HttpPost("https://sstssecurity.com/login.php");
			//HttpPost httpPost = new HttpPost("http://httpbin.org/post");
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("password", password));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
		
		    System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    //EntityUtils.consume(entity2);
		    //System.out.println(EntityUtils.toString(entity2));
		    
		    System.out.println("GONNA WRITE TO TOKEN ====================");
		    token = EntityUtils.toString(entity2);
		    System.out.println("TOKEN IS STORED ====================");
		    response2.close();
		} catch(IOException e) {
			System.out.println("IOException");
		}
		
	    return(token);
	}
}
