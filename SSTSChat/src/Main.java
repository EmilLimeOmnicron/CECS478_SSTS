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
import org.json.JSONObject;
 
public class Main {
 
    public static void main(String[] args) {
       
        String token;
 
        //System.out.println("TEST Register --------------------");
       
    //    Register reg = new Register("Clarissa", "clarissa@yahoo", "password");
      //  reg.Execute();
       
       
        //System.out.println("TEST LOGIN --------------------");
       
        Login log = new Login("Clarissa", "password");
        token = log.Execute();
        System.out.println(token);
        
        SendMessage sm = new SendMessage("daniel","Hi this is a test", token);
        sm.Execute();
       
        //System.out.println("TEST GetMessage ---------------");
       
 
        //System.out.println("TEST SendMessage -------------------");
    }
 
}