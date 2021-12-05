import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
class Server extends Thread
{
    public String decryption(String text,String gamma)
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
            String gamma=is.readUTF();

            os.writeUTF(decryption(text,gamma));

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

