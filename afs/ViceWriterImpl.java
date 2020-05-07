// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.server.*;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {
    private static final String AFSDir = "AFSDir/";
    private String fileName;
    private int tam;
    private RandomAccessFile f;
    public ViceWriterImpl(String fileName,int tam) throws RemoteException, FileNotFoundException {
        this.tam=tam;
        this.fileName = fileName;
        this.f = new RandomAccessFile(AFSDir + fileName, "rw");
    }
    public void write(byte [] b) throws RemoteException,IOException {
        f.write(b);
        return;
    }
    public void close() throws IOException, RemoteException {
        f.close();
        return;
    }
}       

