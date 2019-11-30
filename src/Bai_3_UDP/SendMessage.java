package Bai_3_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class SendMessage extends Thread {
    private DatagramSocket ds;
    private InetAddress address;
    private int serverPort;

    public SendMessage(DatagramSocket ds, InetAddress address, int serverPort) {
        this.ds = ds;
        this.address = address;
        this.serverPort = serverPort;
    }

    public void run() {
        // thuc hien dang nhap lan dau
        Scanner sc = new Scanner(System.in);
        String userName;

        System.out.print("Enter your name: ");
        userName = sc.nextLine();
        userName = "new" + userName;
        byte[] data = userName.getBytes();
        Talk_Util.send(this.ds, data, data.length, this.address, this.serverPort);

        String message;

        // nhan danh sach user dang ket noi
        DatagramPacket incoming = Talk_Util.receive(this.ds);
        message = new String(incoming.getData(), 0, incoming.getLength());
        System.out.println(message);

        // nhan tin nhan sau khi dang nhap
        new ReceiveMessage(ds).start();

        // gui tin nhan sau khi dang nhap
        userName = userName.substring(3);
        while(true) {
            System.out.print(userName + ": ");
            message = sc.nextLine();
            message = userName + ": " + message;
            data =  message.getBytes();
            Talk_Util.send(this.ds, data, data.length, this.address, this.serverPort);
        }
    }
}
