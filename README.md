
# Java-Sockets
Concetti e realizzazione di una semplice connessione Client/Server usando Sockets in Java
```
Obitettivo del progetto: inviare linee di testo da un programma (Client)... -->
                         --> ad un altro (Server) anche eventualmente remoto.
```
**Nota:** Visiona le [Slides della lezione](https://docs.google.com/presentation/d/19vhath-GbtUO7ofpukJE4Opi16aTfrnurJ2lUizB0iE/edit?usp=sharing)
## Istruzioni
Per replicare il progetto sul vostro computer personale. Potrete apportare modifiche ed eventualmente fonderle con il progetto principale, o in alcuni casi creare un progetto proprio.

### Prerequisiti
Java SDK (Software Development Kit) - programma per la compilazione (javac) da codice in Java a ByteCode. L'installazione comprende anche Java JRE (Java Runtime Envirorment) che fornisce la Virtual Machine (VM) su cui far eseguire il ByteCode.
```
E' suggerito anche l'utilizzo di netBeans IDE (Integrated Development Envirorment)
```

### Installazione
Scarica i files del progetto cliccando "Clone/Download"
* in NetBeans crea due nuovi progetti "Java-Sockets-Client" e "Java-Sockets-Server"

* estrai i files e spostali nella sub-directory "src" dei rispettivi progetti (es. Documents\NetBeansProjects\Java-Sockets-Server\src)

## Uso
Dal terminale del Server:
```
java ServerTestoMultiThreaded <server port>
```
Da un terminale Client:
```
java clientTesto <host> <server port>
dove:
* host puo' essere espresso sia in forma numerica (es. 127.0.0.1) che in forma alfanumerica (es. www.nomeSito.com)
```
**Nota**: posso collegarmi al server con quanti Clients desidero, sia su stesso computer che da terminali su computer diversi, purchè abbiano diversi nickname

Visualizzare elenco utenti connessi da Client:
```
<Nickname>
```

### Esempio usando stesso computer sia per eseguire Server che multipli Clients
Per prima cosa compiliamo tutte le classi del progetto, sulla finestra del Server scriviamo i comandi:
```
cd Documents\NetBeansProjects\Java-Socket-Server\src>javac SocketWorker.java
cd Documents\NetBeansProjects\Java-Socket-Server\src>javac ServerTestMultiThreaded.java
```
Sulla finestra del Client scriviamo invece:
```
cd Documents\NetBeansProjects\Java-Socket-Client\src>javac listener.java
cd Documents\NetBeansProjects\Java-Socket-Client\src>javac ClientTesto.java
```
Da finestra di comando Server esguire il Server:
```
cd Documents\NetBeansProjects\Java-Sockets-Server\src
java ServerTestoMultiThreaded 1234
```
Da finestra di comando Client eseguire il ```primo``` Client
```
cd Documents\NetBeansProjects\Java-Sockets-Client\src
java client-Testo localhost 1234
```
Da nuova finestra di comando Client esguire il ```secondo``` Client
```
cd Documents\NetBeansProjects\Java-Sockets-Client\src
java client-Testo localhost 1234
```
## Diventa un collaboratore
Questa e' la lista delle funzionalità del progetto:
```
Nuovi aggiornamenti a breve
```
La prossima modifica consiste nell'inserimento di una Group chat, trovate il nuovo progetto sulla pagina Misael-Micciche-JCMaxwell-5Ai.
 
## Licenza
OpenSource

## Ringraziamenti
Al professor Matteo Palitto
