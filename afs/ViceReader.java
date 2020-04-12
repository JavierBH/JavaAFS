// Interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.io.IOException;
import java.rmi.*;

public interface ViceReader extends Remote {
    public byte[] read(int tam,int pos) throws RemoteException, IOException;
    public void close() throws RemoteException,IOException;
    public int getLengthFile() throws IOException;
    /* añada los métodos remotos que requiera */
}       

