import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionTools {
	private static final String addr="http://localhost:8080/DealCards/AsyncServer";
	private static HttpURLConnection connection=null;
	public static HttpURLConnection getConnection() throws MalformedURLException, IOException {
		HttpURLConnection c=(HttpURLConnection)new URL(addr).openConnection();
		c.setDoOutput(true);
		c.setDoInput(true);
		c.setRequestMethod("POST");
		c.setUseCaches(false); 
		c.connect();
		connection=c;
		return c;
	}
	public static List<String> doAction(Map<String,String> args) {
		try {
			getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> ret1=new ArrayList<String>();
		String ret=new String();
		for(Map.Entry<String,String> i:args.entrySet()) {
			ret+=i.getKey()+'='+i.getValue();
			ret+='&';
		}
		StringBuilder strBuilder=new StringBuilder(ret);
		if(strBuilder.charAt(strBuilder.length()-1)=='&') {
			strBuilder.deleteCharAt(strBuilder.length()-1);
		}
		try {
			OutputStream pw=connection.getOutputStream();
			pw.write(strBuilder.toString().getBytes());
			BufferedReader is=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String s=new String();
			while((s = is.readLine()) != null) {
				ret1.add(s);
			}
			pw.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		args.clear();
		return ret1;
	}
}
