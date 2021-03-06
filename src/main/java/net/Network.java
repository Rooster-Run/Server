package net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import net.packet.CreateGameSession;
import net.packet.JoinGameSession;
import net.packet.Login;
import net.packet.Movement;

public class Network {

	public static final int TCP_PORT = 54555;
	public static final int UDP_PORT = 54556;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(CreateGameSession.class);
		kryo.register(JoinGameSession.class);
		kryo.register(Movement.class);
	}
	
}
