package net;

import java.util.ArrayList;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import net.packet.CreateGameSession;
import net.packet.JoinGameSession;
import net.packet.Login;

public class MPServer {

	public Server server;
	public ArrayList<GameSession> sessions;
	public static boolean online;
		
	public MPServer() {
		server = new Server();
		server.start();
		
		Network.register(server);
		online = true;
		sessions = new ArrayList<>();
				
		try {
			server.bind(Network.TCP_PORT, Network.UDP_PORT);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		server.addListener(new Listener() {
			public void connected(Connection connection) {
				
			}
			
			public void received(Connection connection, Object object) {
				
				if(object instanceof Login) {
					Login packet = (Login) object;
					packet.id = connection.getID();
					server.sendToTCP(connection.getID(), packet);
				}
				
				if(object instanceof CreateGameSession) {
					// make a token and store the game ID to that connection
					// its 4am check this over when im awake
					CreateGameSession packet = new CreateGameSession();
					String token = generateGameToken();
					packet.token = token;
					GameSession session = new GameSession(token);
					session.addPlayer(connection.getID());
					session.setHost(connection.getID());
					sessions.add(session);
					
					server.sendToTCP(connection.getID(), packet);
				}
				
				if(object instanceof JoinGameSession) {
					// get his token and see if exists
					JoinGameSession packet = new JoinGameSession();

					for(GameSession session: sessions) {
						System.out.println("There are currently " + sessions.get(0).getPlayers().size() + " players in the room.");
						System.out.println(session.getToken() + " == " + packet.token);
						if(session.getToken().equals(packet.token)) {
							session.addPlayer(connection.getID());
//							for(Integer x : session.getPlayers()) {
//								server.sendToTCP(x, packet);
//							}
							server.sendToTCP(connection.getID(), packet);
							break;
						}
					}
					System.out.println("There are currently " + sessions.get(0).getPlayers().size() + " players in the room.");

					// if it does put him into the same lobby
					// if not its an invalid token
				}
			}
		});	
	}
	
	public String generateGameToken() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}
	
	public static void main(String[] args) {
//		Log.set(Log.LEVEL_DEBUG);
		new MPServer();
	}
}
