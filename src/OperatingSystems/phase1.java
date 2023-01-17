package OperatingSystems;

import java.io.*;

public class phase1
{
    public static void main(String[] args) throws IOException
    {
        start s=new start();
        s.LOAD();
    }
}

class start
{
    char M[][]=new char[100][4];
    char IR[]=new char[4];
    char R[]=new char[4];
    static int IC=0;
    int SI;
    boolean C;


    public void INIT()
    {
        for(int i=0;i<100;i++)
        {
            for(int j=0;j<4;j++)
            {
                M[i][j]=' ';
            }
        }
        for(int j=0;j<4;j++)
        {
            IR[j] = ' ';
        }
    }


    public void LOAD() throws FileNotFoundException, IOException
    {
        FileReader fr=new FileReader("D:\\Amey Java\\Practice_java\\src\\OperatingSystems\\Input_Phase1.txt");
        BufferedReader br=new BufferedReader(fr);
        String ch;
        for(int i=0;i<100;i++)
        {
            for(int j=0;j<4;j++)
            {
                M[i][j] = ' ';
            }
        }
        while((ch=br.readLine())!=null)
        {
            String l1;
            l1=ch.substring(0,4);
            if(l1.equals("$AMJ"))
            {

                String l2;
                int length,i,j,linecount;
                char c='A';
                linecount=0;
                while(true&&c!='H')
                {
                    l2=br.readLine();
                    length=l2.length();
                    for(i=0;i<length;i++)
                    {
                        for(j=0;j<4;j++)
                        {
                            c=l2.charAt(i);
                            M[linecount][j]=c;
                            i++;
                            if(c=='H')
                            {
                                break;
                            }
                        }
                        if(c=='H')
                        {
                            break;
                        }
                        i--;
                        linecount++;
                    }
                }


            }
            else if(l1.equals("$DTA"))
            {
                STARTEXECUTION(br);
            }
            else if (l1.equals("$END"))
            {
                INIT();
            }
        }
    }


    public void MOS(BufferedReader br,int operand) throws IOException
    {
        switch(SI)
        {
            case 1:
                READ(br,operand);
                break;
            case 2:
                WRITE(operand);
                break;
            case 3:
                TERMINATE();
        }
    }


    public void STARTEXECUTION(BufferedReader br) throws IOException
    {
        IC=0;
        EXECUTEUSERPROGRAM(br);
    }

    public void EXECUTEUSERPROGRAM(BufferedReader br) throws IOException
    {
        int i,operand,temp=0;
        for(i=0;i<4;i++)
        {
            IR[i]=M[IC][i];
        }
        IC++;
        operand=Character.getNumericValue(IR[2])*10+Character.getNumericValue(IR[3]);
       // System.out.println(IR[0]); --> To print IR Content.
        switch(IR[0])
        {
            case 'L':
                for(i=0;i<4;i++)
                {
                    R[i]=M[operand][i];
                }

                for(i=0;i<4;i++)
                {
                    System.out.println(R[i]);
                }
                EXECUTEUSERPROGRAM(br);
                break;
            case 'S':
                for(i=0;i<4;i++)
                {
                    M[operand][i]=R[i];
                }
                EXECUTEUSERPROGRAM(br);
                break;
            case 'C':
                for(i=0;i<4;i++)
                {
                    if(R[i]==M[operand][i])
                    {
                        temp++;
                    }
                    else
                    {
                        continue;
                    }
                }
                if(temp==4)
                {
                    C=true;
                }
                else
                {
                    C=false;
                }
                EXECUTEUSERPROGRAM(br);
                break;
            case 'B':
                if(C)
                {
                    IC=operand;
                }
                EXECUTEUSERPROGRAM(br);
                break;
            case 'G':
                SI=1;
                MOS(br,operand);
                EXECUTEUSERPROGRAM(br);
                break;
            case 'P':
                SI=2;
                MOS(br,operand);
                EXECUTEUSERPROGRAM(br);
                break;
            case 'H':
                SI=3;
                MOS(br,operand);
                break;
        }
    }

    public void READ(BufferedReader br, int operand) throws IOException
    {
        String l1;
        int i,j=0;
        l1=br.readLine();
        int length=l1.length();
        try
        {
            for(i=0;i<length;i++)
            {
                M[operand][j]=l1.charAt(i);
                j++;
                if(j%4==0)
                {
                    operand++;
                    j=0;
                }
            }
            M[operand][i]='\0';
            if(length%4!=0)
            {
                operand++;
            }
        }
        catch(Exception e)
        {
            System.out.println("");
        }
    }

    public void WRITE(int operand) throws IOException
    {
        System.out.println("Writing to File");
        File file=new File("D:\\Amey Java\\Practice_java\\src\\OperatingSystems\\output.txt");
        FileWriter fr=new FileWriter(file, true);
        BufferedWriter br1=new BufferedWriter(fr);
        String temp;
        temp=Character.toString(M[operand][0]);
        int j;
        for(int i=operand;i<operand+9;i++)
        {
            for(j=0;j<4;j++)
            {
                if(i==operand&&j==0)
                {
                    continue;
                }
                System.out.println("I : " + i + " j: " + j + " M[i][j] : " + M[i][j] + " temp : " + temp);
                temp=temp+Character.toString(M[i][j]);
                if(M[i][j]=='\0')
                {
                    break;
                }
            }
            if(j<4&&M[i][j]=='\0')
            {
                break;
            }
        }
        System.out.println(temp);
        fr.append(temp);
        br1.newLine();
        br1.flush();
        br1.close();
    }


    public void TERMINATE() throws IOException
    {
        File file=new File("D:\\Amey Java\\Practice_java\\src\\OperatingSystems\\output.txt");
        FileWriter fr=new FileWriter(file, true);
        BufferedWriter br1=new BufferedWriter(fr);
        br1.newLine();
        br1.newLine();
        br1.newLine();

        br1.flush();
        br1.close();
    }
}


