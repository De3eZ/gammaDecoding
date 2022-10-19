import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class send extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JButton saveButton;
    private JButton sendButton;
    private JPanel mainPanel;

    public send(String title)
    {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        try (ServerSocket server= new ServerSocket(3345)){
            Socket client = server.accept();

            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            sendButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try {
                        File file1 = new File("C://Users//DeZ//Documents//file1.txt");
                        try
                        {
                            boolean created = file1.createNewFile();
                            if(created)
                                System.out.println("File1 has been created");
                        }
                        catch(IOException ex){

                            System.out.println(ex.getMessage());
                        }

                        File file2 = new File("C://Users//DeZ//Documents//file2.txt");
                        try
                        {
                            boolean created = file2.createNewFile();
                            if(created)
                                System.out.println("File2 has been created");
                        }
                        catch(IOException ex){

                            System.out.println(ex.getMessage());
                        }

                        FileOutputStream output1 = new FileOutputStream(file1);
                        FileOutputStream output2 = new FileOutputStream(file2);

                        output1.write(textField1.getText().getBytes());
                        output2.write(textField2.getText().getBytes());

                    } catch (Exception er) {
                        System.out.println("init error: " + e);
                    }
                }
            });

            in.close();
            out.close();

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
