import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws InterruptedException {

        try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {


            while(!socket.isOutputShutdown()){


                if(br.ready()){

                    Thread.sleep(1000);
                    String text = br.readLine();
                    String gamma = br.readLine();

                    oos.writeUTF(text);
                    oos.writeUTF(gamma);
                    oos.flush();
                    Thread.sleep(1000);
                    String result =ois.readUTF();
                    System.out.println(result);
                    Thread.sleep(2000);

                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}