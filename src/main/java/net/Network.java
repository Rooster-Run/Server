package net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import net.packet.CreateGameSession;
import net.packet.ErrorPacket;
import net.packet.IceEffect;
import net.packet.JoinGameSession;
import net.packet.LeftGameSession;
import net.packet.Login;
import net.packet.Movement;
import net.packet.PlayerInfo;
import net.packet.SessionInfo;
import net.packet.StartGame;
import net.packet.TerminateSession;
import net.packet.Winner;

public class Network {

	public static final int TCP_PORT = 54555;
	public static final int UDP_PORT = 54556;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(CreateGameSession.class);
		kryo.register(JoinGameSession.class);
		kryo.register(Movement.class);
		kryo.register(SessionInfo.class);
		kryo.register(java.util.ArrayList.class);
		kryo.register(StartGame.class);
		kryo.register(PlayerInfo.class);
		kryo.register(ErrorPacket.class);
		kryo.register(TerminateSession.class);
		kryo.register(Winner.class);
		kryo.register(IceEffect.class);
		kryo.register(LeftGameSession.class);
	}
	
}
