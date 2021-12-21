import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Array;

class Server extends Thread
{
    public String caesar(String text,int key)
    {
        String result="";
        char[] array={'А','Б','В','Г','Д','Е','Ё','Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ы','Ь','Э','Ю','Я'};
        char[] words = text.toCharArray();
        char[] words2=new char[text.length()+1];

        for(int i=0;i<text.length();i++)
        {
            int k=1;
            for(int j=0;j<33;j++)
            {
                if(words[i]==array[j])
                {
                    k=j+key;
                    break;
                }
            }

            if(k>=33)
            {
                k-=33;
            }

            words2[i]=array[k];
        }
        for(int i=0;i<text.length();i++)
        {
           result+=words2[i];
        }

        return result;
    }

    Socket s;
    int num;
    public static void main(String args[])
    {
        try
        {
            int i = 0;
            ServerSocket server = new ServerSocket(3128, 0,
                    InetAddress.getByName("localhost"));
            System.out.println("Сервер запущен");
            while(true)
            {
                new Server(i, server.accept());
                i++;
                System.out.println("На сервер поступил запрос №: "+i);
            }
        }
        catch(Exception e)
        {
            System.out.println("init error: "+e);
        }
    }
    public Server(int num, Socket s)
    {
        this.num = num;
        this.s = s;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }
    public void run()
    {
        try
        {
            DataOutputStream os = new DataOutputStream(s.getOutputStream());
            DataInputStream is = new DataInputStream(s.getInputStream());

            String text=is.readUTF();
            int key=is.readInt();

            os.writeUTF(caesar(text,key));

            is.close();
            os.close();
            s.close();
        }
        catch(Exception e)
        {
            System.out.println("init error: "+e);
        }
    }
}

