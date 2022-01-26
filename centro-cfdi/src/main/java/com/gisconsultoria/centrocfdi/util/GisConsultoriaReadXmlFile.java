package com.gisconsultoria.centrocfdi.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gisconsultoria.centrocfdi.model.dao.ComplementoDao;
import com.gisconsultoria.centrocfdi.model.dao.ImpuestoDao;
import com.gisconsultoria.centrocfdi.model.dto.ComprobanteXmlDto;
import com.gisconsultoria.centrocfdi.model.dto.TimbreFiscalDto;
import com.gisconsultoria.centrocfdi.sheduled.XsaConnector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GisConsultoriaReadXmlFile implements IReadXmlFile{

	protected final Logger LOG = Logger.getLogger(GisConsultoriaReadXmlFile.class);

    @Autowired
    private ILogicaFacade logicaFacade;

    @Override
    public void readXmlFile(File dir) throws IOException {

        LOG.info("LEECTURA DEL ARCHIVO DENTRO DE LA CARPETA: ".concat(dir.getName()));
        String fileExtencion=null;
        try(Stream<Path> stream = Files.walk(Paths.get(dir.getAbsolutePath()))){
            Set<String> archivosXml = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());

            for(String xml : archivosXml){
            	fileExtencion = FilenameUtils.getExtension(xml);
            	if(fileExtencion.equals("xml")) {
            		decodeXmlFile(dir, xml);
            	}
            }

        }catch (IOException ex){
            LOG.error("Ocurrió un error al momento de extraer los archivos: ", ex);
            throw new IOException("Ocurrió un error al momento de extraer los archivos: " +
                    ex.getMessage());
        } catch (ParserConfigurationException e) {
            LOG.error("OCURRIÓ UN ERROR DE EJECUCIÓN: ", e);
            e.printStackTrace();
        } catch (SAXException e) {
            LOG.error("OCURRIÓ UN ERROR DE EJECUCIÓN: ", e);
            e.printStackTrace();
        } catch (Exception e) {
            LOG.error("OCURRIÓ UN ERROR DE EJECUCIÓN: ", e);
            e.printStackTrace();
        }
    }

    @Override
    public void decodeXmlFile(File file, String xml) throws Exception {

        LOG.info("DECODIFICACIÓN DEL ARCHIVO: ".concat(xml));

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(
                new File(file.getAbsolutePath() + "/" + xml));

        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("cfdi:Comprobante");

        Double version = 0.0;

        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element)node;
                version = Double.parseDouble(element.getAttribute("Version"));
            }
        }

        if(version == 3.2){
            throw new IOException("Versión incorrecta del comprobante");
        }else if(version == 3.3){
            try {
                unmarshallXmlToComprobanteXml(file, xml);
            }catch(JAXBException jaxbException){
                LOG.error("Error al momento de deserializar el xml", jaxbException);
                jaxbException.printStackTrace();
            }
       }

    }

    @Override
    public void unmarshallXmlToComprobanteXml(File file, String xml) throws Exception {

        LOG.info("DESERIALIZACIÓN DEL OBJETO");

        JAXBContext jaxbContext = JAXBContext.newInstance(ComprobanteXmlDto.class,
                ComplementoDao.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ComprobanteXmlDto comprobante = (ComprobanteXmlDto)unmarshaller.unmarshal(
                new File(file.getAbsolutePath() + "/" + xml));

        LOG.info("XML DESEREALIZADO");
        ZoneId zoneId = ZoneId.of("America/Mexico_City");
        Date fechaActual = Date.from(ZonedDateTime.now(zoneId).toInstant());
 
        DOMResult res = new DOMResult();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(comprobante, res);

        Document doc = (Document)res.getNode();
        NodeList nodeList = doc.getElementsByTagName("tfd:TimbreFiscalDigital");
       
        Date fechaDoc;
        SimpleDateFormat parser = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");
        SimpleDateFormat parseFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        TimbreFiscalDto timbreFiscal = new TimbreFiscalDto();
        LOG.info("LECTURA DEL NODO PARA BUSCAR LA FECHA Y UUID");
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element)node;
                fechaDoc = parseFecha.parse(element.getAttribute("FechaTimbrado"));
                timbreFiscal.setFechaTimbrado(parseFecha.format(fechaDoc));
                fechaDoc=parser.parse(element.getAttribute("FechaTimbrado"));
                timbreFiscal.setTimeTimbre(formatter.format(fechaDoc));
                timbreFiscal.setUuid(element.getAttribute("UUID"));
                timbreFiscal.setCertificadoSat(element.getAttribute("NoCertificadoSAT"));
                timbreFiscal.setVersion(element.getAttribute("Version"));
                timbreFiscal.setSelloCfd(element.getAttribute("SelloCFD"));
                timbreFiscal.setSelloSat(element.getAttribute("SelloSAT"));
            }
        }
        if(timbreFiscal.getUuid().isEmpty()){
            LOG.error("Documento sin timbre fiscal");
            throw new Exception("Documento sin timbre fiscal");
        }

       if(logicaFacade.checarUuidRepetidoBD(timbreFiscal, file, xml)){
            if(logicaFacade.checarRfcReceptor(comprobante)){
                LOG.info("GUARDANDO ARCHIVO: ".concat(xml).concat( "EN LA BD"));
                logicaFacade.guardarComprobanteBd(comprobante,file, xml,timbreFiscal);
            }
        }

    }
}
