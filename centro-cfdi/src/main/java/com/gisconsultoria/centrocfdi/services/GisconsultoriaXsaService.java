package com.gisconsultoria.centrocfdi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gisconsultoria.centrocfdi.model.Clientes;
import com.gisconsultoria.centrocfdi.service.IClientesService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


@Service
public class GisconsultoriaXsaService {

	private static final Logger LOG =  LoggerFactory.getLogger(GisconsultoriaXsaService.class);

    @Value("${path.archivo.archivosXml}")
    private String pathArchivos;

    @Autowired
    private IClientesService empresaService;

    public void ObtenerArchivos(Long sucursalId, Date fecha) throws IOException{

        Clientes sucursal = empresaService.findById(sucursalId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(fecha);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        if(sucursal.getKeyXsa() != null && sucursal.getServidor() != null) {
        try {
        URL url = new URL("https://".concat(sucursal.getServidor())
                                    .concat("/xsamanager/DownloadExpedidosBloqueServlet?rfcEmisor=")
                                    .concat(sucursal.getRfc().concat("&"))
                                    .concat("key=").concat(sucursal.getKeyXsa()).concat("&")
                                    .concat("fechaInicial=").concat(dateString).concat("&")
                                    .concat("fechaFinal=").concat(dateFormat.format(calendar.getTime()).concat("&"))
                                    .concat("tipo=").concat("XML,PDF"));

        
        InputStream in = new BufferedInputStream(url.openStream(), 1024);//1024
        File zip = File.createTempFile(sucursal.getRfc(), dateString.concat(".zip"));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
        copyInputStream(in, out);
        out.close();

        File path = new File(pathArchivos);
        LOG.info("Obteniendo archivos para la sucursal: " + sucursal.getNombre());
        unpackArchive(zip, path,sucursal.getNombre());

        sucursal.setFechaInicial(calendar.getTime());
        empresaService.updateFechaInicialEmpresaById(calendar.getTime(),sucursal.getId() );
        
        }catch (IOException e) {
			e.printStackTrace();
			LOG.error("Error al momento de transformar la url : "+e);
		}
        }else {
        	LOG.info("No se encuentra un servidor y key asignado al cliente:" +sucursal.getNombre());
        }
    }

    private void unpackArchive(File zip, File path, String nombreSucursal) throws IOException{
    	File pathCliente = new  File(path.toString().concat("\\").concat(nombreSucursal));
    	System.out.println(zip);
        if(!zip.exists()){
            throw new IOException(zip.getAbsolutePath() + " no existe el archivo");
        }
        if(!pathCliente.exists()){
            throw new IOException("No se puede encontrar el directorio: " + path);
        }else {
        	buildDirectory(pathCliente);
        }

        ZipFile zipFile = new ZipFile(zip);
        for(Enumeration entries = zipFile.entries(); entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();

            if(entry.getName().contains(".xml") || entry.getName().contains(".pdf") || 
            		entry.getName().toUpperCase().contains(".xml") || entry.getName().toUpperCase().contains(".pdf")){
                LOG.info("Obteniendo el archivo: " + entry.getName());
                File file = new File(pathCliente, File.separator + entry.getName());
                if(!buildDirectory(file.getParentFile())){
                    throw new IOException("No se puede crear el directorio: " + file.getParentFile());
                }
                if(!entry.isDirectory()){
                    copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
                }else{
                    if(!buildDirectory(file)){
                        throw new IOException("No se puede crear el directorio: " + file);
                    }
                }
            }
        }

        zipFile.close();

    }

    private void copyInputStream(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[2048];

        int len = in.read(buffer);
        while(len >= 0){
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }

        in.close();
        out.close();
    }

    private boolean buildDirectory(File file) {
        return file.exists() || file.mkdir();
    }
}
