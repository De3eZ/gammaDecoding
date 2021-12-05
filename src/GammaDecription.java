import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.net.*;


public class GammaDecription extends JFrame{
    private JPanel mainPanel;
    private  JTextField Text;
    private JLabel TextLabel;
    private  JTextField Gamma;
    private JLabel GammaLabel;
    private JButton GetResultButton;
    private JLabel Name;
    private  JLabel Answer;

    public GammaDecription(String title)
    {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        GetResultButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    Socket s = new Socket("127.0.0.1", 3128);
                    DataOutputStream os = new DataOutputStream(s.getOutputStream());
                    DataInputStream is = new DataInputStream(s.getInputStream());

                    os.writeUTF(Text.getText());
                    os.writeUTF(Gamma.getText());
                    Answer.setText(is.readUTF());
                } catch (Exception er) {
                    System.out.println("init error: " + e);
                }
            }
        });
    }
}
