// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*; 
import java.io.*; 

public class VenusFile {
    public static final String cacheDir = "Cache/";
    private String fileName;
    private String mode;
    private Venus venus; 
    private RandomAccessFile f;

    //Constructor de la clase
    //RECORDATORIO: SE TRABAJA SOBRE LA COPIA LOCAL, SI ESTA NO EXISTE SE DEBE DESCARGAR
    public VenusFile(Venus venus, String fileName, String mode) throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;
        this.fileName = fileName;
        this.mode = mode; 
        try{
            //Se comprueba si el fichero esta en cache
            this.f = new RandomAccessFile(cacheDir + fileName, mode);
        } catch(FileNotFoundException e){
            //Si no esta en cache se descarga
            Vice download_file = (Vice)this.venus.getLookup();
            //Se descarga el fichero
            ViceReader vr = download_file.download(this.fileName,(int)this.venus.getBlockSize());
            byte [] fichero = new byte [vr.getLengthFile()]; 
            //Se descargan todos los bloques del fichero
            for(int i = 0; i<vr.getLengthFile();i = i +venus.getBlockSize()){
                vr.read(venus.getBlockSize(), i);
            }
            //Se escribe el fichero
            f.write(fichero);
            f.setLength(vr.getLengthFile());
            //Se abre el fichero
            this.f = new RandomAccessFile(cacheDir + fileName, mode);
        }
    }

    //Esto no me queda claro si se debe hacer asi o no, pero la verdad es que tiene sentido
    public int read(byte[] b) throws RemoteException, IOException {
        int leidos;
        f.seek(0);
        leidos = f.read(b);
        return leidos;
    } 
        
    public void write(byte[] b) throws RemoteException, IOException {
        return;
    }
    public void seek(long p) throws RemoteException, IOException {
        return;
    }
    public void setLength(long l) throws RemoteException, IOException {
        return;
    }
    public void close() throws RemoteException, IOException {
        return;
    }
}

