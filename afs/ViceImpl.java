// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;
import java.rmi.*;
import java.rmi.server.*;
import java.io.FileNotFoundException;


public class ViceImpl extends UnicastRemoteObject implements Vice {
    public ViceImpl() throws RemoteException {
    }
    /** 
     * Metodo que descarga el fichero indicado del servidor.
     * @param fileName: Nombre del archivo que se quiere descargar
     * @param tam: Tamaño de los bloques de descarga
     * @return Referencia a objecto ViceReader que utilizara el cliente para descargar el fichero
     * @throws RemoteException
     * @throws FileNotFoundException
     */ 
    public ViceReader download(String fileName,int tam) throws RemoteException, FileNotFoundException{
        try{
            ViceReaderImpl vr = new ViceReaderImpl(fileName,tam);
            return vr;
        } catch(FileNotFoundException e){
                  return null;
        }
    }

    public ViceWriter upload(String fileName,int tam) throws RemoteException, FileNotFoundException {
              ViceWriterImpl wr = new ViceWriterImpl(fileName,tam);
        return wr;
    }
}
