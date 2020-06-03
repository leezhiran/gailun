import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import Services.Deck;
public class Test {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int i = scanner.nextInt();
		if (i == 0) {
			Map<String, String> netArgs = new HashMap<String, String>();
			List<String> netRet = null;
			int user_id = -1;
			int match_id = -1;
			Deck[] hand;
			netArgs.put("Action", "log_in");
			netRet = ConnectionTools.doAction(netArgs);
			if (netRet.get(0).equals("OK")) {
				user_id = Integer.parseInt(netRet.get(1));
			}
			netRet.clear();
			netArgs.put("Action", "create_match");
			netArgs.put("user_id", String.valueOf(user_id));
			netRet = ConnectionTools.doAction(netArgs);
			if (netRet.get(0).equals("OK")) {
				match_id = Integer.parseInt(netRet.get(1));
			}
			netRet.clear();
			while (true) {
				netArgs.put("Action", "Spooling");
				netArgs.put("user_id", String.valueOf(user_id));
				netArgs.put("match_id", String.valueOf(match_id));
				netRet = ConnectionTools.doAction(netArgs);
				if (netRet.get(0).equals("Spooling")) {
					System.out.println(netRet.get(1));
				} else {
					byte[] s = Base64.getDecoder().decode(netRet.get(1).replace("\r\n", ""));
					ByteArrayInputStream bis = new ByteArrayInputStream(s);
					try {
						ObjectInputStream ois = new ObjectInputStream(bis);
						hand = (Deck[]) ois.readObject();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					netRet.clear();
					break;
				}
				netRet.clear();
			}
		} else {
			Map<String, String> netArgs = new HashMap<String, String>();
			List<String> netRet = null;
			int user_id = -1;
			int match_id = -1;
			Deck[] hand;
			netArgs.put("Action", "log_in");
			netRet = ConnectionTools.doAction(netArgs);
			if (netRet.get(0).equals("OK")) {
				user_id = Integer.parseInt(netRet.get(1));
			}
			netRet.clear();
			netArgs.put("Action", "list_match");
			netRet = ConnectionTools.doAction(netArgs);
			if (netRet.get(0).equals("OK")) {
				byte[] s = Base64.getDecoder().decode(netRet.get(1).replace("\r\n", ""));
				ByteArrayInputStream bis = new ByteArrayInputStream(s);
				try {
					ObjectInputStream ois = new ObjectInputStream(bis);
					List<String> l = (List<String>) ois.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				netRet.clear();
			}
			netRet.clear();
			netArgs.put("Action", "join_match");
			netArgs.put("user_id", String.valueOf(user_id));
			match_id=0;
			netArgs.put("match_id", "0");
			netRet = ConnectionTools.doAction(netArgs);
			
			if (netRet.get(0).equals("OK")) {
				System.out.println(netRet.get(1));
			}
			netRet.clear();
			while (true) {
				netArgs.put("Action", "Spooling");
				netArgs.put("user_id", String.valueOf(user_id));
				netArgs.put("match_id", String.valueOf(match_id));
				netRet = ConnectionTools.doAction(netArgs);
				if (netRet.get(0).equals("Spooling")) {
					System.out.println(netRet.get(1));
				} else {
					byte[] s = Base64.getDecoder().decode(netRet.get(1).getBytes(StandardCharsets.UTF_8));
					ByteArrayInputStream bis = new ByteArrayInputStream(s);
					try {
						ObjectInputStream ois = new ObjectInputStream(bis);
						hand = (Deck[]) ois.readObject();
						System.out.println(hand[0].name());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					netRet.clear();
					break;
				}
				netRet.clear();
			}
		}

	}
}
