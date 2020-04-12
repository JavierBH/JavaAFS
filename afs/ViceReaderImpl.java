// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "AFSDir/"; // Directorio en el que se encuentran los ficheros del server
    private RandomAccessFile f;

    /**
     * Constructor de la clase
     * 
     * @param fileName: Nombre del archivo que se quiere descargar
     * @param mode:     Modo de apertura del fichero (J:Aunque creo que esto
     *                  sobraria)
     * @param tam:      Tamaño de los bloques de descarga
     * @throws RemoteException
     * @throws FileNotFoundException
     */
    public ViceReaderImpl(String fileName, String mode, int tam /* añada los parámetros que requiera */)
            throws RemoteException, FileNotFoundException {
        this.f = new RandomAccessFile(AFSDir + fileName, mode);
    }
    /**
     * Metodo que lee un bloque del fichero solicitado 
     * @param tam: Tamaño de bytes que se quieren leer (Tamaño el bloque)
     * @param pos: Posicion desde la que se quiere leer 
     * @return array de bytes correspondiente al tamaño del bloque indicado
     */
    public byte[] read(int tam,int pos) throws RemoteException, IOException {
        byte[] file = new byte[tam];
        f.seek(0);
        f.read(file);
        return file;
    }

    public void close() throws RemoteException, IOException {
        f.close(); //Creo que solo es esto IDK
        return;
    }

    public int getLengthFile() throws IOException{
        return (int)f.length();
    }
}       

