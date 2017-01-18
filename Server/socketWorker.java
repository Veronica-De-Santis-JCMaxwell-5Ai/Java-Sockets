/*
 * socketWorker.java ha il compito di gestire la connessione al socket da parte di un Client.
 * Elabora il testo ricevuto che in questo caso viene semplicemente mandato indietro con l'aggiunta 
 * di una indicazione che e' il testo che viene dal Server.
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author Prof. Matteo Palitto
 */
class SocketWorker implements Runnable {
  private Socket client;
  // VARIABILE PER CONTROLLARE IL PRIMO STREAM DI DATI
  private boolean isNick = false;
  private boolean isGroup = false;
  private String nick = "";
  private String group = "";

    //Constructor: inizializza le variabili
    SocketWorker(Socket client) {
        this.client = client;
    }

    // Questa e' la funzione che viene lanciata quando il nuovo "Thread" viene generato
    public void run(){
        
        BufferedReader in = null;
        PrintWriter out = null;
        try{
          // connessione con il socket per ricevere (in) e mandare(out) il testo
          in = new BufferedReader(new InputStreamReader(client.getInputStream()));
          out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
          System.out.println("Errore: in|out fallito");
          System.exit(-1);
        }
        String line = "";
        //FINCHE' IL NICKNAME NON SI RIPETE 
        controlNick(line,in,out);
        System.out.println("Connesso con: " + nick);
        while(line != null){
          try{
            line = in.readLine();
            //SCRIVENDO NICKNAME SUL CLIENT, STAMPA TUTTA LA LISTA
            switch(line){
                    case "Nickname":
                    {
                        for(int i = 0; i < ServerTestoMultiThreaded.listaClient.size(); i++)
                        {
                            out.println("Client "+(i+1)+": "+ServerTestoMultiThreaded.listaClient.get(i));
                        }
                    }
                    break;
                    case "New":
                    {
                        out.println("Inserisci il nome del gruppo: ");
                        controlGroup(line,in,out);
                    }
                    break;
                    case "Join":
                    {
                        out.println("Inserisci il nome del gruppo al quale vuoi unirti: ");
                        joinAGroup(line,in,out);
                    }
                    case "Groups":
                    {
                        for(int i = 0; i < ServerTestoMultiThreaded.listaGroup.size(); i++)
                        {
                            out.println("Group "+(i+1)+": "+ServerTestoMultiThreaded.listaGroup.get(i).getName());
                        }
                    }
                    break;
                    case "Exit":
                    {
                        // ELIMINO IL CLIENT DALLA LISTA
                        ServerTestoMultiThreaded.listaClient.remove(nick);
                        try {
                            out.println("Bye.");
                            client.close();
                            System.out.println("connessione con client: " + nick + " terminata!");
                            return;
                        } catch (IOException e) { System.out.println("Errore connessione con client: " + nick); }
                    }
                    break;
                    default:
                    {
                        //scrivi messaggio ricevuto su terminale
                        System.out.println(nick + ">> " + line);
                    }
                    break;
                }
          } catch (IOException e) { System.out.println("lettura da socket fallito");
            System.exit(-1); }
        }
    }
    // METODO CHE RITORNA IL NICKNAME
    public String getNick()
    {
        return nick;
    }
  
  public void controlNick(String line, BufferedReader in, PrintWriter out)
    {
        while(!isNick)
        {
            try{
                // LEGGO IL NICKNAME
                line = in.readLine();
                boolean trov = false;
                int i = 0;
                //FINCHE' NON SCORRO TUTTA LA LISTA O NON TROVO IL NICKNAME
                while(trov==false && i < ServerTestoMultiThreaded.listaClient.size())
                {
                    //CONFRONTA IL NICKNAME INSERITO CON LA POSIZIONE i NELLA LISTA 
                    //SE LO TROVA IMPOSTA LA VARIABILE A TRUE, ALTRIMENTI AUMENTA IL CONTATORE
                    if(ServerTestoMultiThreaded.listaClient.get(i).equals(line))
                    {
                        trov = true;
                    } else i++;
                }
                //SE NON VIENE TROVATO IL NICKNAME 
                if(!trov)
                {
                    //IMPOSTA LE VARIABILI
                    nick = line;
                    isNick = true;
                    ServerTestoMultiThreaded.listaClient.add(nick);
                } else {
                    //ALTRIMENTI STAMPA SUL CLIENT
                    out.println("Nickname gia' esistente, inseriscine un altro");
                }            
            } catch(IOException e) { System.out.println("Lettura da socket fallito");
                                 System.exit(-1); }
        }
    }
    
    public void controlGroup(String line, BufferedReader in, PrintWriter out)
    {
        while(!isGroup)
        {
            try{
                // LEGGO IL NICKNAME
                line = in.readLine();
                boolean trov = false;
                int i = 0;
                //FINCHE' NON SCORRO TUTTA LA LISTA O NON TROVO IL NICKNAME
                while(trov==false && i < ServerTestoMultiThreaded.listaGroup.size())
                {
                    //CONFRONTA IL NICKNAME INSERITO CON LA POSIZIONE i NELLA LISTA 
                    //SE LO TROVA IMPOSTA LA VARIABILE A TRUE, ALTRIMENTI AUMENTA IL CONTATORE
                    if(ServerTestoMultiThreaded.listaGroup.get(i).getName().equals(line))
                    {
                        trov = true;
                    } else i++;
                }
                //SE NON VIENE TROVATO IL NICKNAME 
                if(!trov)
                {
                    //IMPOSTA LE VARIABILI
                    group = line;
                    isGroup = true;
                    ServerTestoMultiThreaded.listaGroup.add(new Group(group,client.hashCode()));
                } else {
                    //ALTRIMENTI STAMPA SUL CLIENT
                    out.println("Gruppo gia' esistente, inseriscine un altro");
                }            
            } catch(IOException e) { System.out.println("Lettura da socket fallito");
                                 System.exit(-1); }
        }
    }
    
    public void joinAGroup(String line, BufferedReader in, PrintWriter out)
    {
        try{
            line = in.readLine();
        } catch(IOException e){ System.out.println("I|O Error");
                                System.exit(-1); }
        boolean trov = false;
        int i = 0;
        int index = 0;
        while(trov==false && i<ServerTestoMultiThreaded.listaGroup.size())
        {
            if(ServerTestoMultiThreaded.listaGroup.get(i).getName().equals(line))
            {
                trov = true;
                index = i;
            } else i++;
        }
        if(trov)
        {
            ServerTestoMultiThreaded.listaGroup.get(index).addClient(client.hashCode());
            out.println("Benvenuto nel gruppo!");
        } else {
            out.println("Il gruppo non esiste, prova a crearlo usando il comando <<New>>");
        }
    }
}
