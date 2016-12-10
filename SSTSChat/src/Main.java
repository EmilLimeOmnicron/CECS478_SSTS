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
        int jwtStart = 8;
        //System.out.println("TEST Register --------------------");
       
     Register reg = new Register("emildavid", "emildavid@yahoo.com", "password123");
       reg.Execute();
       
       
        //System.out.println("TEST LOGIN --------------------");
       
        Login log = new Login("emildavid", "password123");
        token = log.Execute();
        
        String modifiedToken = token.substring(8, token.length()-2);
        System.out.println(modifiedToken);

        SendMessage sm = new SendMessage("daniel","im a trumpW sub", modifiedToken);
        sm.Execute();
       
        //System.out.println("TEST GetMessage ---------------");
       GetMessage gm = new GetMessage(modifiedToken);
       gm.Execute();
       
 
        //System.out.println("TEST SendMessage -------------------");
    }
 
}