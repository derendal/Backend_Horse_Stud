package com.example.myHorseServer.service;

import com.example.myHorseServer.dto.gamer.GamerRegisterDto;
import com.example.myHorseServer.model.Authme;
import com.example.myHorseServer.repository.AuthmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class AuthmeService  {

    @Autowired
    AuthmeRepository authmeRepository;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toLowerCase();
    private static final int SALT_LENGTH = 16;

    public Authme createAuthmeAccount(GamerRegisterDto gamer){

            String ip = null;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
             ip = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        System.out.println("passssssworrddd: " + gamer.getPassword());

        String password = encode(gamer.getPassword());

        System.out.println("passssssworrddd: " + password);

        Authme creator = new Authme();
        creator.setUsername(gamer.getNickname());
        creator.setRealname(gamer.getNickname());
        creator.setPassword(password);
        creator.setIp(ip);
        creator.setLastLogin((int) new Date().getTime());
        creator.setX(0.0);
        creator.setY(0.0);
        creator.setZ(0.0);
        creator.setWorld("world");
        creator.setRegdate((int) new Date().getTime());
        creator.setRegip(ip);
        creator.setYaw(null);
        creator.setPitch(null);
        creator.setEmail(gamer.getEmail());
        creator.setGamerLogged(false);
        creator.setHasSession(false);
        creator.setTotp(null);

        creator = authmeRepository.save(creator);
        return new Authme(
                creator.getId(),
                creator.getUsername(),
                creator.getRealname(),
                creator.getPassword(),
                creator.getIp(),
                null,
                creator.getX(),
                creator.getY(),
                creator.getZ(),
                creator.getWorld(),
                creator.getRegdate(),
                creator.getRegip(),
                creator.getYaw(),
                creator.getPitch(),
                creator.getEmail(),
                false,
                false,
                creator.getTotp()
        );
    }




    public String encode(CharSequence rawPassword) {
        String salt = generateHash();
        try {
            return encode(rawPassword.toString(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] data = encodedPassword.split("\\$");
        try {
            return encode(rawPassword.toString(), data[2]).equals(encodedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String encode(String raw, String salt) throws NoSuchAlgorithmException {
        return "$SHA$" + salt + "$" + hash(hash(raw) + salt);
    }

    private String hash(String toEncrypt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                toEncrypt.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String generateHash() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < SALT_LENGTH; i++) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
