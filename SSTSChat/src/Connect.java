import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Connect {
	
	public static void dbConnect() {
		String route = "https://sstssecurity.com/dbbconnect.php";
		try {
			URL obj = new URL(route);
		    HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
	dbConnect();
	}
}
