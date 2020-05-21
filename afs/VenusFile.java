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
    private boolean modified; //Indica si el fichero se ha modificado

    //Constructor de la clase
    public VenusFile(Venus venus, String fileName, String mode) throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;
        this.fileName = fileName;
        this.mode = mode; 
        this.modified = false;
        File f = new File(cacheDir + fileName);
        if(!f.exists()){
            cache_file_r(f);
        }
        this.rf = new RandomAccessFile(cacheDir + fileName, this.mode);
    }

//Metodo que descarga el fichero del servidor, si el fichero no existe en servidor devuelve false
    private void cache_file_r(File f) throws IOException {
        ViceReader vr = this.venus.getSrv().download(this.fileName,(int)this.venus.getBlockSize());
        if(vr==null){return;}
        FileOutputStream fos = new FileOutputStream(f);
        //Se descargan todos los bloques del fichero
        byte[] fichero;
        for(int i = 0; i<vr.getLengthFile();i = i +venus.getBlockSize()){
            //Se escribe el fichero
            fichero = vr.read(venus.getBlockSize());
            if(fichero == null){
                vr.close();
                fos.close();
                return;
            }
            //Se escriben los bytes necesarios en el en el output stream en la posicion indicada
            fos.write(fichero);
        }
        vr.close();
        fos.close();
        //Se abre el fichero
        return;
    }

    public int read(byte[] b) throws RemoteException, IOException {
        int leidos;
        leidos = rf.read(b);
        return leidos;
    } 
        
    public void write(byte[] b) throws RemoteException, IOException {
        modified  = true;
        rf.write(b);
        return;
    }
    public void seek(long p) throws RemoteException, IOException {
        rf.seek(p);
        return;
    }
    public void setLength(long l) throws RemoteException, IOException {
        modified=true;
        rf.setLength(l);
        return;
    }

    public void close() throws RemoteException, IOException {
        //Si el fichero se ha modificado se sube al servidor
        if(this.mode.equals("rw") && modified){
            modified = false;
            ViceWriter wr = this.venus.getSrv().upload(fileName,rf.length());
            //Se descargan todos los bloques del fichero
            long tam_fich = rf.length();
            rf.seek(0);
            //Se vacia el fichero del servidor
            wr.removeContent();
            //Se lee el fichero por bloques
            for(int i = 0; i<tam_fich;i = i +venus.getBlockSize()){
                byte[] b;
                if(venus.getBlockSize()>(int)tam_fich){
                    b = new byte[(int)tam_fich];
                    //Si el tamaño de bloque es mayor que el numero de bytes leidos
                }else if((i+venus.getBlockSize())>(int)tam_fich){
                    b = new byte[(int)tam_fich-i];
                }
                else{
                    b = new byte[venus.getBlockSize()];
                }
                //Se lee la info del fichero y se escribe en el
                read(b);
                wr.write(b);
            }
            wr.close();
        }
        rf.close();
        return;
    }
}

