package com.example.javafx;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class EguraldiaController {

    public void xmlDescargatuetaEditatu() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, NoSuchAlgorithmException, KeyManagementException, TransformerException {
        // Crear el DocumentBuilder
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // Obtener el XML desde la URL o desde un archivo
        String url = "https://www.aemet.es/xml/municipios/localidad_20076.xml";
        ArrayList<Object> a = getFiles(true, url);
        InputStream iXmlFile = (InputStream) a.get(0);
        File fXmlFile = (File) a.get(1);

        Document document;
        if (iXmlFile != null) {
            document = dBuilder.parse(iXmlFile);
        } else if (fXmlFile != null) {
            document = dBuilder.parse(fXmlFile);
        } else {
            throw new IOException("No se pudo obtener el archivo XML.");
        }

        // Evaluar la expresión XPath para obtener los próximos 7 días
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "//prediccion/dia"; // Selecciona todos los días
        NodeList nodeList = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);

        // Crear un nuevo documento XML para la información filtrada
        Document newDoc = dBuilder.newDocument();
        Element root = newDoc.createElement("informazioFiltratua");
        newDoc.appendChild(root);

        // Procesar todos los días
        for (int i = 0; i < Math.min(7, nodeList.getLength()); i++) {
            Node diaNode = nodeList.item(i);
            Element diaElement = (Element) diaNode;

            // Crear un nuevo elemento <dia> y copiar el atributo "fecha"
            Element newDia = newDoc.createElement("dia");
            newDia.setAttribute("fecha", diaElement.getAttribute("fecha"));

            if (i == 0) {
                // 1. Temperatura máxima y mínima (solo para el primer día)
                NodeList tempMaxNodes = (NodeList) xpath.evaluate("temperatura/maxima", diaNode, XPathConstants.NODESET);
                NodeList tempMinNodes = (NodeList) xpath.evaluate("temperatura/minima", diaNode, XPathConstants.NODESET);

                if (tempMaxNodes.getLength() > 0) {
                    Element maxTemp = newDoc.createElement("temperatura_maximoa");
                    maxTemp.setTextContent(tempMaxNodes.item(0).getTextContent().trim());
                    newDia.appendChild(maxTemp);
                }

                if (tempMinNodes.getLength() > 0) {
                    Element minTemp = newDoc.createElement("temperatura_minimoa");
                    minTemp.setTextContent(tempMinNodes.item(0).getTextContent().trim());
                    newDia.appendChild(minTemp);
                }

                // 2. Estado del cielo (solo para el primer día, periodo "12-24")
                NodeList cieloNodes = (NodeList) xpath.evaluate("estado_cielo[@periodo='12-24']", diaNode, XPathConstants.NODESET);
                if (cieloNodes.getLength() > 0) {
                    String descripcionCielo = cieloNodes.item(0).getAttributes().getNamedItem("descripcion") != null ?
                            cieloNodes.item(0).getAttributes().getNamedItem("descripcion").getTextContent().trim() :
                            "";

                    if (!descripcionCielo.isEmpty()) {
                        Element cielo = newDoc.createElement("zeruaren_egoera");
                        cielo.setTextContent(descripcionCielo);
                        newDia.appendChild(cielo);
                    }
                }

                // 3. Probabilidad de precipitación (solo para el primer día, periodo "12-24")
                NodeList precipNodes = (NodeList) xpath.evaluate("prob_precipitacion[@periodo='12-24']", diaNode, XPathConstants.NODESET);
                if (precipNodes.getLength() > 0) {
                    Element precipitacion = newDoc.createElement("euri_probabilitatea");
                    precipitacion.setTextContent(precipNodes.item(0).getTextContent().trim());
                    newDia.appendChild(precipitacion);
                }

                // 4. Velocidad del viento (solo para el primer día, periodo "12-24")
                NodeList vientoNodes = (NodeList) xpath.evaluate("viento[@periodo='12-24']", diaNode, XPathConstants.NODESET);
                if (vientoNodes.getLength() > 0) {
                    Node velocidadNode = (Node) xpath.evaluate("velocidad", vientoNodes.item(0), XPathConstants.NODE);
                    if (velocidadNode != null) {
                        Element viento = newDoc.createElement("haize_abiadura");
                        viento.setTextContent(velocidadNode.getTextContent().trim());
                        newDia.appendChild(viento);
                    }
                }
            } else {
                // Procesar todos los días como antes (sin filtro específico para "12-24")
                // 1. Temperatura máxima y mínima
                NodeList tempMaxNodes = (NodeList) xpath.evaluate("temperatura/maxima", diaNode, XPathConstants.NODESET);
                NodeList tempMinNodes = (NodeList) xpath.evaluate("temperatura/minima", diaNode, XPathConstants.NODESET);

                if (tempMaxNodes.getLength() > 0) {
                    Element maxTemp = newDoc.createElement("temperatura_maximoa");
                    maxTemp.setTextContent(tempMaxNodes.item(0).getTextContent().trim());
                    newDia.appendChild(maxTemp);
                }

                if (tempMinNodes.getLength() > 0) {
                    Element minTemp = newDoc.createElement("temperatura_minimoa");
                    minTemp.setTextContent(tempMinNodes.item(0).getTextContent().trim());
                    newDia.appendChild(minTemp);
                }

                // 2. Estado del cielo (para todos los periodos)
                NodeList cieloNodes = (NodeList) xpath.evaluate("estado_cielo", diaNode, XPathConstants.NODESET);
                if (cieloNodes.getLength() > 0) {
                    String descripcionCielo = cieloNodes.item(0).getAttributes().getNamedItem("descripcion") != null ?
                            cieloNodes.item(0).getAttributes().getNamedItem("descripcion").getTextContent().trim() :
                            "";

                    if (!descripcionCielo.isEmpty()) {
                        Element cielo = newDoc.createElement("zeruaren_egoera");
                        cielo.setTextContent(descripcionCielo);
                        newDia.appendChild(cielo);
                    }
                }

                // 3. Probabilidad de precipitación (para todos los periodos)
                NodeList precipNodes = (NodeList) xpath.evaluate("prob_precipitacion", diaNode, XPathConstants.NODESET);
                if (precipNodes.getLength() > 0) {
                    Element precipitacion = newDoc.createElement("euri_probabilitatea");
                    precipitacion.setTextContent(precipNodes.item(0).getTextContent().trim());
                    newDia.appendChild(precipitacion);
                }

                // 4. Velocidad del viento (para todos los periodos)
                NodeList vientoNodes = (NodeList) xpath.evaluate("viento", diaNode, XPathConstants.NODESET);
                if (vientoNodes.getLength() > 0) {
                    Node velocidadNode = (Node) xpath.evaluate("velocidad", vientoNodes.item(0), XPathConstants.NODE);
                    if (velocidadNode != null) {
                        Element viento = newDoc.createElement("haize_abiadura");
                        viento.setTextContent(velocidadNode.getTextContent().trim());
                        newDia.appendChild(viento);
                    }
                }
            }

            // Añadir el elemento <dia> filtrado o completo al documento final
            root.appendChild(newDia);
        }

        // Guardar el nuevo documento XML filtrado con formato legible
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(newDoc);
        File outputFile = new File("../2mg3_2Erronka_4Taldea_eguraldia/eguraldia.xml");
        StreamResult result = new StreamResult(outputFile);
        transformer.transform(source, result);

        // Ejecutar el archivo BAT de forma asíncrona (fire and forget)
        try {
            ejecutarBat("eguraldiancommit.bat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Object> getFiles(boolean aukeratu, String url)
            throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {
        File fXmlFile = null;
        InputStream iXmlFile = null;

        if (url != null && !url.isEmpty()) {
            // Si se proporciona una URL, se deshabilita la validación SSL y se descarga el archivo
            aukeratu = false;
            MyUrlConnection.disableSSLCertificateValidation();
            iXmlFile = MyUrlConnection.getFileFromURL(url);
        }

        if (aukeratu) {
            // Si se indica que se debe elegir el archivo, se abre una ventana de selección
            fXmlFile = FileChoser.chooseWindow();
        } else if (fXmlFile == null) {
            // Si no se seleccionó un archivo, se intenta obtenerlo por una ruta predefinida
            fXmlFile = FileChoser.getFileFromRoute();
        }

        ArrayList<Object> a = new ArrayList<>();
        a.add(iXmlFile);
        a.add(fXmlFile);

        return a;
    }

    /**
     * Ejecuta el archivo BAT de forma asíncrona.
     * No se espera a que termine el proceso; se lanza y se lee la salida en un hilo aparte.
     */
    public static void ejecutarBat(String rutaBat) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", rutaBat);
        pb.redirectErrorStream(true);
        pb.directory(new File("2mg3_2Erronka_4Taldea_GerenteAplikazioa"));

        Process proceso = pb.start();

        // Iniciamos un hilo para leer la salida del proceso sin bloquear
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
