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
  private String nick = "";

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
        while(!isNick)
        {
            controlNick(in,out);
        }
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
                        // Controllare con una variabile booleana quando viene inserito il nome di un gruppo
                    }
                    break;
                    case "Join":
                    {
                        out.println("Inserisci il nome del gruppo al quale vuoi unirti: ");
                    }
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
  
  public void controlNick(BufferedReader in, PrintWriter out)
    {
        String line = "";
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
    
    public void controlGroup(String group, PrintWriter out)
    {
            boolean trov = false;
            int i = 0;
            //FINCHE' NON SCORRO TUTTA LA LISTA O NON TROVO IL NICKNAME
            while(trov==false && i < ServerTestoMultiThreaded.listaGroups.size())
            {
                //CONFRONTA IL GRUPPO INSERITO CON LA POSIZIONE i NELLA LISTA 
                //SE LO TROVA IMPOSTA LA VARIABILE A TRUE, ALTRIMENTI AUMENTA IL CONTATORE
                if(ServerTestoMultiThreaded.listaGroups.get(i).equals(group))
                {
                    trov = true;
                } else i++;
            }
            //SE NON VIENE TROVATO IL NOME DEL GRUPPO IMPOSTO LE VARIABILI
            if(trov == false)
            {
                //IMPOSTA LE VARIABILI
                groups = group;
                ServerTestoMultiThreaded.listaGroups.add(groups);
            } else {
                //ALTRIMENTI STAMPA SUL CLIENT
                out.println("Gruppo gia' esistente, inseriscine un altro");
            }
    }
}
