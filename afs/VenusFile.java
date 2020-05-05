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
    private RandomAccessFile rf;
    private File f;

    //Constructor de la clase
    public VenusFile(Venus venus, String fileName, String mode) throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;
        this.fileName = fileName;
        this.mode = mode; 
        //El fichero se abre en modo r
        if(mode.equals("r")){
        try{
            //Se comprueba si el fichero esta en cache
                this.rf = new RandomAccessFile(cacheDir + fileName, mode);
        } catch(FileNotFoundException e){
            //Si no esta en cache se descarga
            cache_file_r();
        }
        //El fichero se abre en modo rw
    }else{
        try{
            //Se comprueba si el fichero esta en cache
                this.rf = new RandomAccessFile(cacheDir + fileName, mode);
        } catch(FileNotFoundException e){
            //Si no esta en cache se crea
            f = new File(cacheDir + fileName);
        }
        }
    }

    private void cache_file_r() throws IOException {
        ViceReader vr = this.venus.getSrv().download(this.fileName,(int)this.venus.getBlockSize());
        if(vr==null)
            return;
        f = new File(cacheDir + fileName);
        FileOutputStream fos = new FileOutputStream(f);
        //Se descargan todos los bloques del fichero
        byte[] fichero;
        for(int i = 0; i<vr.getLengthFile();i = i +venus.getBlockSize()){
            //Se escribe el fichero
            fichero = vr.read(venus.getBlockSize());
            if(fichero == null)
                break;
            //Se escriben los bytes necesarios en el en el output stream en la posicion indicada
            fos.write(fichero);
        }
        vr.close();
        fos.close();
        //Se abre el fichero
        this.rf = new RandomAccessFile(cacheDir + fileName, mode);
    }

    //Esto no me queda claro si se debe hacer asi o no, pero la verdad es que tiene sentido
    public int read(byte[] b) throws RemoteException, IOException {
        int leidos;
        leidos = rf.read(b);
        return leidos;
    } 
        
    public void write(byte[] b) throws RemoteException, IOException {
        return;
    }
    public void seek(long p) throws RemoteException, IOException {
        rf.seek(p);
        return;
    }
    public void setLength(long l) throws RemoteException, IOException {
        return;
    }
    public void close() throws RemoteException, IOException {
        rf.close();
        return;
    }
}

