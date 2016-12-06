

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class Register {

    private CloseableHttpClient httpclient;
 
    public void register(String uname, String email, String pass) {
      // httpclient = HttpClientBuilder.create().build();
        final String url = "https://www.sstssecurity.com/register.php";
        JSONObject json = new JSONObject();

        try {
        	
			json.put("username", uname);  
			json.put("email", email);
	        json.put("password", pass);
	        // Create the POST object and add the parameters
	        HttpPost httpPost = new HttpPost(url);
	        StringEntity entity = new StringEntity(json.toString(), HTTP.UTF_8);
	        entity.setContentType("application/json");
	        httpPost.setEntity(entity);
	        CloseableHttpClient httpclient= HttpClientBuilder.create().build();
	        HttpResponse response = httpclient.execute(httpPost);
	        System.out.println(response);
		} catch (JSONException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      
    }

}
