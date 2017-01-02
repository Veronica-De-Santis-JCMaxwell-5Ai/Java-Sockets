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
            try{
                // LEGGO IL NICKNAME
                line = in.readLine();
                boolean trov = false;
                int i = 0;
                //FINCHE' NON SCORRO TUTTA LA LISTA O NON TROVO IL NICKNAME
                while(trov==false && i < ServerTestoMultiThreaded.listaSocket.size())
                {
                    //CONFRONTA IL NICKNAME INSERITO CON LA POSIZIONE i NELLA LISTA 
                    //SE LO TROVA IMPOSTA LA VARIABILE A TRUE, ALTRIMENTI AUMENTA IL CONTATORE
                    if(ServerTestoMultiThreaded.listaSocket.get(i).getNick().equals(line))
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
                } else {
                    //ALTRIMENTI STAMPA SUL CLIENT
                    out.println("Nickname gia' esistente, inseriscine un altro");
                }
            } catch(IOException e) { System.out.println("Lettura da socket fallito");
                                     System.exit(-1); }
        }
        System.out.println("Connesso con: " + nick);
        while(line != null){
          try{
            line = in.readLine();
            //SCRIVENDO NICKNAME SUL CLIENT, STAMPA TUTTA LA LISTA
            if(line.equals("Nickname")){
              for(int i = 0; i < ServerTestoMultiThreaded.listaSocket.size(); i++)
                {
                    out.println("Client "+(i+1)+": "+ServerTestoMultiThreaded.listaSocket.get(i).getNick());
                }
            } else {
              //Manda lo stesso messaggio appena ricevuto con in aggiunta il "nome" del client
              out.println("Server-->" + nick + ">> " + line);
              //scrivi messaggio ricevuto su terminale
              System.out.println(nick + ">> " + line);
            }
          } catch (IOException e) {
            System.out.println("lettura da socket fallito");
            System.exit(-1);
           }
        }
        try {
            client.close();
            System.out.println("connessione con client: " + nick + " terminata!");
        } catch (IOException e) {
            System.out.println("Errore connessione con client: " + nick);
        }
    }
    // METODO CHE RITORNA IL NICKNAME
    public String getNick()
    {
        return nick;
    }
  
}
