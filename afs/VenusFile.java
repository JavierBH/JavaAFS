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
    private long seek;
   // private int modified; //Indica si el fichero se ha modificado

    //Constructor de la clase
    public VenusFile(Venus venus, String fileName, String mode) throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;
        this.fileName = fileName;
        this.mode = mode; 
        this.seek = 0;
      //  this.modified = 0;
        try{
            //Se comprueba si el fichero esta en cache
            this.rf = new RandomAccessFile(cacheDir + fileName, mode);
        } catch(FileNotFoundException e){
            //Si no esta en cache se descarga
            cache_file_r();   
            }
            this.rf = new RandomAccessFile(cacheDir + fileName, mode);
        }

//Metodo que descarga el fichero del servidor, si el fichero no existe en servidor devuelve false
    private boolean cache_file_r() throws IOException {
        boolean res = true;
        ViceReader vr = this.venus.getSrv().download(this.fileName,(int)this.venus.getBlockSize());
        if(vr==null)
            return true;
        f = new File(cacheDir + fileName);
        FileOutputStream fos = new FileOutputStream(f);
        //Se descargan todos los bloques del fichero
        byte[] fichero;
        for(int i = 0; i<vr.getLengthFile();i = i +venus.getBlockSize()){
            //Se escribe el fichero
            fichero = vr.read(venus.getBlockSize());
            if(fichero==null){
                res = false;
                break;
            }
            //Se escriben los bytes necesarios en el en el output stream en la posicion indicada
            fos.write(fichero);
        }
        vr.close();
        fos.close();
        //Se abre el fichero
        return res;
    }

    public int read(byte[] b) throws RemoteException, IOException {
        int leidos;
        leidos = rf.read(b);
        return leidos;
    } 
        
    public void write(byte[] b) throws RemoteException, IOException {
       // modified  = 1;
       // rf.write(b);
        return;
    }
    public void seek(long p) throws RemoteException, IOException {
        seek = p;
        rf.seek(p);
        return;
    }
    public void setLength(long l) throws RemoteException, IOException {
        return;
    }

    public void close() throws RemoteException, IOException {
        //Si el fichero se ha modificado se sube al servidor
       /* if(modified==1)
            this.venus.getSrv().upload(fileName);
        modified = 0;*/
        rf.close();
        return;
    }
}

