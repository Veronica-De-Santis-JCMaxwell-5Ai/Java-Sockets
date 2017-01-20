import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author veronica.desantis
 */
public class Group {
    private String name;
    private List<Integer> numberOfPort = new ArrayList();
    
    Group(String name, int hashCode){
        this.name = name;
        numberOfPort.add(hashCode);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void deleteClient(int hashCode)
    {
        numberOfPort.remove(hashCode);
    }
    
    public void addClient(int hashCode)
    {
        numberOfPort.add(hashCode);
    }
}
