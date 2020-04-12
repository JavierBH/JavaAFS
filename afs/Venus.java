// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.rmi.*; 

public class Venus {
    private String host;
    private String port;
    private String block_size;
    public Venus() {
        try {
            // DIreccion de host de la maquina del cliente
            host = System.getenv("REGISTRY_HOST");
            //Puerto de la maquina del cliente
            port = System.getenv("REGISTRY_PORT");
            //Tamaño de los bloques en el envio
            block_size = System.getenv("BLOCKSIZE");
            Naming.lookup("//" + host + ":" + port + "/Banco");
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteBanco:");
            e.printStackTrace();
        }
    }

    public String getHost(){
        return this.host;
    }

    public String getPort(){
        return this.port;
    }

    public String getBlockSize(){
        return this.block_size;
    }
}


