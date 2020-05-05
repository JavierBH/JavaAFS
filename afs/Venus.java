// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.rmi.*; 

public class Venus {
    private String host;
    private String port;
    private int block_size;
    private Vice srv;
    public Venus() throws RemoteException {
        this.host = System.getenv("REGISTRY_HOST");
        this.port = System.getenv("REGISTRY_PORT");
        this.block_size = Integer.parseInt(System.getenv("BLOCKSIZE"));
        try {
            srv = (Vice) Naming.lookup("//" + this.host + ":" + this.port + "/AFS");
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        } catch (Exception e) {
            System.err.println("Excepcion en cliente:");
            e.printStackTrace();
        }
    }

    public String getRegistryHost() {
        return this.host;
    }

    public String getRegistryPort() {
        return this.port;
    }

    public int getBlockSize() {
        return this.block_size;
    }

    public Vice getSrv() {
        return this.srv;
    }
}