import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class user extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton saveButton;
    private JPanel mainPanel;

    public user(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        try (Socket socket = new Socket("localhost", 3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream());) {

                File file1 = new File("C://Users//DeZ//Documents//file1.txt");

                File file2 = new File("C://Users//DeZ//Documents//file2.txt");

            FileReader reader1 = new FileReader(file1);
            FileReader reader2 = new FileReader(file2);

            String fl1="";

            int c;
            while((c=reader1.read())!=-1){
                System.out.print(c+" ");
                fl1+=(char)c;
            }

            String fl2="";

            int c2;
            while((c2=reader2.read())!=-1){
                fl2+=(char)c2;
            }

            textField1.setText(fl1);
            textField2.setText(fl2);

            /*
            while(!socket.isOutputShutdown()){
                if(br.ready()){
                    String text = br.readLine();
                    String gamma = br.readLine();

                    oos.writeUTF(text);
                    oos.writeUTF(gamma);
                    oos.flush();
                    String result =ois.readUTF();
                    System.out.println(result);
                }
            }
*/

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
