
import controller.Controller;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.



/**
 *
 * @author admin_master
 */
public class JavaCrush {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        final int SIZE = Integer.decode(args[0]);
        new Controller(SIZE, SIZE);
    }
}
