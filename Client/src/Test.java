import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class Test {
	
	public static void main(String[] args) {
		/*final String addr="http://localhost:8080/DealCards/AsyncServer";
		try {
			URL url=new URL(addr);
			int user_id;
			
			HttpURLConnection c=(HttpURLConnection)url.openConnection();
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setRequestMethod("GET");
			c.setUseCaches(false); 
			c.connect();
			OutputStream os=c.getOutputStream();
			os.write("Action=log_in".getBytes());
			BufferedReader is=new BufferedReader(new InputStreamReader(c.getInputStream()));
			String s=is.readLine();
			if(s.equals("OK")) 
                for (int i=0;(s = is.readLine()) != null;i++) {
                    System.out.println(s);
                    user_id=Integer.parseInt(s);
                }
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
