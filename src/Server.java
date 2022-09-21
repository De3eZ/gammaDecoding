import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    public static String decryption(String text, String gamma)
    {
        char[] array={'А','Б','В','Г','Д','Е','Ё','Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ы','Ь','Э','Ю','Я',' ','0','1','2','3','4','5','6','7','8','9',};
        int size=44;
        String result="";

        char[] words = text.toCharArray();
        char[] words2=gamma.toCharArray();

        int[] C=new int[text.length()];
        int[] G=new int[text.length()];


        for(int i=0;i<text.length();i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (words[i] == array[j])
                {
                    C[i]=j+1;
                    break;
                }
            }
        }

        for(int i=0,k=0;i<text.length();i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (words2[k] == array[j])
                {
                    G[i]=j+1;
                    break;
                }
            }
            k++;

            if(k==gamma.length())
            {
                k=0;
            }
        }

        int[] res=new int[text.length()];

        for(int i=0;i<text.length();i++)
        {
            res[i]=C[i]-G[i]+size;
        }
        for(int i=0;i<text.length();i++)
        {
            if(res[i]>size)
            {
                res[i]%=size;
            }
        }
        for(int i=0;i<text.length();i++)
        {
            result+=array[res[i]-1];
        }

        return result;
    }
    public static void main(String[] args) throws InterruptedException {

        try (ServerSocket server= new ServerSocket(3345)){
            Socket client = server.accept();

            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            DataInputStream in = new DataInputStream(client.getInputStream());

            String text=in.readUTF();
            System.out.println("Исходный текст: "+text);
            String gamma=in.readUTF();
            System.out.println("Исходный ключ: "+gamma);

            String result=decryption(text,gamma);

            System.out.println("Результат: "+result);

            out.writeUTF(result);

            in.close();
            out.close();

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}