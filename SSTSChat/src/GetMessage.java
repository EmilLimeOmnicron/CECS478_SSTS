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
 
public class GetMessage {
   
    private CloseableHttpClient httpclient;
    private String token;
 
    public GetMessage() {
        httpclient = HttpClients.createDefault();
    }
   
    public GetMessage(String tok) {
        httpclient = HttpClients.createDefault();
        token = tok;
    }
   
    public void Execute() {
        try {
        System.out.println("GET ---");
        HttpPost httpPost = new HttpPost("https://sstssecurity.com/getMessage.php");
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
         httpPost.addHeader("token", token);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);
   
        System.out.println(response2.getStatusLine());
        HttpEntity entity2 = response2.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        //EntityUtils.consume(entity2);
        
        //returns a byte 
        byte[] allMessages = EntityUtils.toByteArray(entity2);
        
        
        //System.out.println(EntityUtils.toString(entity2));
        response2.close();
        } catch(IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}
 