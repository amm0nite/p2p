package fr.ambox.p2p.peers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.JsonObject;

import fr.ambox.p2p.connexion.Frame;

public class Friend {
    private PeerId peerId;
    private Socket socket;
    private HostAndPort hostAndPort;
    private ObjectOutputStream oos;

    public Friend(HostAndPort hp, PeerId peerId) {
        this.peerId = peerId;
        this.socket = null;
        this.hostAndPort = hp;
    }

    public void send(Frame fm) throws FriendComException {
        this.send(fm, false);
    }

    synchronized private void send(Frame fm, boolean retry) throws FriendComException {
        try {
            if (this.socket == null) {
                this.socket = new Socket(this.hostAndPort.getHost(), this.hostAndPort.getPort());
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            }

            try {
                this.oos.writeObject(fm);
                this.oos.flush();
            } catch (IOException e) {
                this.socket = null;
                this.oos = null;
                if (!retry) {
                    this.send(fm, true);
                } else {
                    throw new FriendComException("failed to send");
                }
            }
        } catch (UnknownHostException e) {
            throw new FriendComException(this.toString() + " is unknown");
        } catch (IOException e) {
            throw new FriendComException("connection to " + this.toString() + " failed");
        }
    }

    public PeerId getPeerId() {
        return this.peerId;
    }

    public JsonObject toJSON() {
        JsonObject jsonObject = new JsonObject();

        if (this.peerId != null) {
            jsonObject.addProperty("id", this.peerId.getId());
            jsonObject.addProperty("nickname", this.peerId.getNickname());
        } else {
            jsonObject.add("id", null);
            jsonObject.add("nickname", null);
        }

        jsonObject.addProperty("host", this.hostAndPort.getHost());
        jsonObject.addProperty("port", this.hostAndPort.getPort());
        return jsonObject;
    }

    public String toString() {
        return this.hostAndPort.toString();
    }

    public HostAndPort getHostAndPort() {
        return this.hostAndPort;
    }

    public void updateNickname(String nickname) {
        if (nickname != null && !nickname.isEmpty()) {
            this.peerId.setNickname(nickname);
        }
    }
}
