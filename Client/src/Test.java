import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class Test {

	public static void main(String[] args) {
		final String addr="http://localhost:8080/DealCards/AsyncServer";
		//while(true) {
		try {
			URL url=new URL(addr);
			HttpURLConnection c=(HttpURLConnection)url.openConnection();
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setRequestMethod("GET");
			c.setUseCaches(false); 
			c.connect();
			OutputStream os=c.getOutputStream();
			os.write("s".getBytes());
			System.out.println(c.getResponseCode());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//}
	}

}
