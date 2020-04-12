// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.rmi.*; 

public class Venus {
    private String host;
    private String port;
    private String block_size;
    public Venus() {
            // DIreccion de host de la maquina del cliente
            this.host = System.getenv("REGISTRY_HOST");
            //Puerto de la maquina del cliente
            this.port = System.getenv("REGISTRY_PORT");
            //Tamaño de los bloques en el envio
            this.block_size = System.getenv("BLOCKSIZE");
    }

    public Remote getLookup(){
        try{
            Remote lookup = Naming.lookup("//" + host + ":" + port + "/Banco"); 
            return lookup;
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            return null;
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteBanco:");
            e.printStackTrace();
            return null;
        }
    }

    public String getHost(){
        return this.host;
    }

    public String getPort(){
        return this.port;
    }

    public int getBlockSize(){
        return Integer.parseInt(this.block_size);
    }
}


