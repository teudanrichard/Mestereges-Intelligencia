/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.map;

import ai.event.Controller;
import ai.ui.Main;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ÁdámRichárd
 */
public class ReadSettings {

    private static File last_selected_file;
    private static String karakter_elvalaszto = "";
    private static String karakter_fal = "#";
    private static String karakter_start = "1";
    private static String karakter_cel = "+";
    private static String karakter_ut = " ";
    private static Color szin_fal = Color.GRAY;
    private static Color szin_ut = Color.WHITE;
    private static Color szin_cel = Color.RED;
    private static Color szin_start = Color.BLUE;
    private static boolean racsozott_palya = true;
    private static boolean legrovidebb_ut = true;

    public static File getLast_selected_file() {
        return last_selected_file;
    }

    public static void setLast_selected_file(File last_selected_file) {
        ReadSettings.last_selected_file = last_selected_file;
    }

    public static boolean isLegrovidebb_ut() {
        return legrovidebb_ut;
    }

    public static void setLegrovidebb_ut(boolean legrovidebb_ut) {
        ReadSettings.legrovidebb_ut = legrovidebb_ut;
    }

    public static boolean isRacsozott_palya() {
        return racsozott_palya;
    }

    public static void setRacsozott_palya(boolean racsozott_palya) {
        ReadSettings.racsozott_palya = racsozott_palya;
    }

    public static String getKarakter_elvalaszto() {
        return karakter_elvalaszto;
    }

    public static void setKarakter_elvalaszto(String karakter_elvalaszto) {
        ReadSettings.karakter_elvalaszto = karakter_elvalaszto;
    }

    public static String getKarakter_fal() {
        return karakter_fal;
    }

    public static void setKarakter_fal(String karakter_fal) {
        ReadSettings.karakter_fal = karakter_fal;
    }

    public static String getKarakter_start() {
        return karakter_start;
    }

    public static void setKarakter_start(String karakter_start) {
        ReadSettings.karakter_start = karakter_start;
    }

    public static String getKarakter_cel() {
        return karakter_cel;
    }

    public static void setKarakter_cel(String karakter_cel) {
        ReadSettings.karakter_cel = karakter_cel;
    }

    public static String getKarakter_ut() {
        return karakter_ut;
    }

    public static void setKarakter_ut(String karakter_ut) {
        ReadSettings.karakter_ut = karakter_ut;
    }

    public static Color getSzin_fal() {
        return szin_fal;
    }

    public static void setSzin_fal(Color szin_fal) {
        ReadSettings.szin_fal = szin_fal;
    }

    public static Color getSzin_ut() {
        return szin_ut;
    }

    public static void setSzin_ut(Color szin_ut) {
        ReadSettings.szin_ut = szin_ut;
    }

    public static Color getSzin_cel() {
        return szin_cel;
    }

    public static void setSzin_cel(Color szin_cel) {
        ReadSettings.szin_cel = szin_cel;
    }

    public static Color getSzin_start() {
        return szin_start;
    }

    public static void setSzin_start(Color szin_start) {
        ReadSettings.szin_start = szin_start;
    }

    /**
     * check properties folder then settings.xml if it doesn't exists it will create the folder and the xml file too
     */
    public static void checkXML() {
        File folder = new File("properties");
        if (!folder.isDirectory() && !folder.exists()) {
            folder.mkdir();
            createXML();
        } else {
            if (new File("properties\\settings.xml").exists()) {
                readXML();
            } else {
                createXML();
            }
        }
    }


    public static void readXML() {
        //beolvassuk a properties/settings.xml fájlt a letárolt változók miatt
        try {
            //fájl
            File f = new File("properties\\settings.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            //megkeressük a settings taget
            NodeList list = doc.getElementsByTagName("Settings");
            //itemeket listázunk
            Node node = list.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                //kiszedjük belőle a Parameters taget majd megint listázunk
                NodeList list2 = element.getElementsByTagName("Parameters");

                Node node2 = list2.item(0);

                if (node2.getNodeType() == Node.ELEMENT_NODE) {

                    Element element2 = (Element) node2;

                    //változók beolvasása sima stringként
                    ReadSettings.setKarakter_cel(element2.getElementsByTagName("karakter_cel").item(0).getTextContent());
                    ReadSettings.setKarakter_fal(element2.getElementsByTagName("karakter_fal").item(0).getTextContent());
                    ReadSettings.setKarakter_start(element2.getElementsByTagName("karakter_start").item(0).getTextContent());
                    ReadSettings.setKarakter_ut(element2.getElementsByTagName("karakter_ut").item(0).getTextContent());
                    //lecsekkolju,hogy a string true-e a boolean-t beállítjuk
                    if (element2.getElementsByTagName("racsozott_palya").item(0).getTextContent().equals("true")) {
                        ReadSettings.setRacsozott_palya(true);
                    } else {
                        ReadSettings.setRacsozott_palya(false);
                    }
                    //lecsekkolju,hogy a string true-e a boolean-t beállítjuk
                    if (element2.getElementsByTagName("legrovidebb_ut").item(0).getTextContent().equals("true")) {
                        ReadSettings.setLegrovidebb_ut(true);
                    } else {
                        ReadSettings.setLegrovidebb_ut(false);
                    }
                    //falszín tag mekeresése majd az összes értéket (R,G,B) kiolvassuk
                    NodeList list3 = element.getElementsByTagName("szin_fal");

                    Node node3 = list3.item(0);
                    //az értékeket rögtön karakterből intre alakítjuk
                    if (node3.getNodeType() == Node.ELEMENT_NODE) {
                        Element element3 = (Element) node3;
                        ReadSettings.setSzin_fal(new Color(Integer.parseInt(element3.getElementsByTagName("szin_fal_r").item(0).getTextContent()), Integer.parseInt(element3.getElementsByTagName("szin_fal_g").item(0).getTextContent()), Integer.parseInt(element3.getElementsByTagName("szin_fal_b").item(0).getTextContent())));
                    }

                    NodeList list4 = element.getElementsByTagName("szin_ut");

                    Node node4 = list4.item(0);

                    if (node4.getNodeType() == Node.ELEMENT_NODE) {
                        Element element4 = (Element) node4;
                        ReadSettings.setSzin_ut(new Color(Integer.parseInt(element4.getElementsByTagName("szin_ut_r").item(0).getTextContent()), Integer.parseInt(element4.getElementsByTagName("szin_ut_g").item(0).getTextContent()), Integer.parseInt(element4.getElementsByTagName("szin_ut_b").item(0).getTextContent())));
                    }
                    NodeList list5 = element.getElementsByTagName("szin_cel");

                    Node node5 = list5.item(0);

                    if (node5.getNodeType() == Node.ELEMENT_NODE) {
                        Element element5 = (Element) node5;
                        ReadSettings.setSzin_cel(new Color(Integer.parseInt(element5.getElementsByTagName("szin_cel_r").item(0).getTextContent()), Integer.parseInt(element5.getElementsByTagName("szin_cel_g").item(0).getTextContent()), Integer.parseInt(element5.getElementsByTagName("szin_cel_b").item(0).getTextContent())));
                    }

                    NodeList list6 = element.getElementsByTagName("szin_start");

                    Node node6 = list6.item(0);

                    if (node6.getNodeType() == Node.ELEMENT_NODE) {
                        Element element6 = (Element) node6;
                        ReadSettings.setSzin_start(new Color(Integer.parseInt(element6.getElementsByTagName("szin_start_r").item(0).getTextContent()), Integer.parseInt(element6.getElementsByTagName("szin_start_g").item(0).getTextContent()), Integer.parseInt(element6.getElementsByTagName("szin_start_b").item(0).getTextContent())));
                    }
                }
            }
            //beállítjuk a beállítások menü alatt a mezőket az újonnal feltöltött értékek alapján
            Controller.setAllTextField();
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            JOptionPane.showMessageDialog(null, "Hiba az xml fájl olvasása közben\nMessage:" + ex.getMessage(), "Hiba történt", JOptionPane.WARNING_MESSAGE);
        }
    }
    public static void createXML() {
        //létrehozzuk az xml fájlt olyan felépítéssel,hogy a beolvasás tökéletesen működhessen
        try {
            File f = new File("properties\\settings.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();

            Element rootElement = document.createElement("Settings");
            document.appendChild(rootElement);

            Element parameters = document.createElement("Parameters");

            Element ut_element = document.createElement("karakter_ut");
            ut_element.appendChild(document.createTextNode(karakter_ut));
            parameters.appendChild(ut_element);

            Element fal_element = document.createElement("karakter_fal");
            fal_element.appendChild(document.createTextNode(karakter_fal));
            parameters.appendChild(fal_element);

            Element start_element = document.createElement("karakter_start");
            start_element.appendChild(document.createTextNode(karakter_start));
            parameters.appendChild(start_element);

            Element cel_element = document.createElement("karakter_cel");
            cel_element.appendChild(document.createTextNode(karakter_cel));
            parameters.appendChild(cel_element);

            Element racsozott_palya_element = document.createElement("racsozott_palya");
            racsozott_palya_element.appendChild(document.createTextNode("" + racsozott_palya));
            parameters.appendChild(racsozott_palya_element);

            Element legrovidebb_ut_element = document.createElement("legrovidebb_ut");
            legrovidebb_ut_element.appendChild(document.createTextNode("" + legrovidebb_ut));
            parameters.appendChild(legrovidebb_ut_element);
            //Fal színének beállítása
            Element fal_color_node = document.createElement("szin_fal");
            Element fal_color_element_r = document.createElement("szin_fal_r");
            fal_color_element_r.appendChild(document.createTextNode("" + szin_fal.getRed()));

            Element fal_color_element_g = document.createElement("szin_fal_g");
            fal_color_element_g.appendChild(document.createTextNode("" + szin_fal.getGreen()));

            Element fal_color_element_b = document.createElement("szin_fal_b");
            fal_color_element_b.appendChild(document.createTextNode("" + szin_fal.getBlue()));
            fal_color_node.appendChild(fal_color_element_r);
            fal_color_node.appendChild(fal_color_element_g);
            fal_color_node.appendChild(fal_color_element_b);
            parameters.appendChild(fal_color_node);

            //az út színének beállítása
            Element ut_color_node = document.createElement("szin_ut");
            Element ut_color_element_r = document.createElement("szin_ut_r");
            ut_color_element_r.appendChild(document.createTextNode("" + szin_ut.getRed()));

            Element ut_color_element_g = document.createElement("szin_ut_g");
            ut_color_element_g.appendChild(document.createTextNode("" + szin_ut.getGreen()));

            Element ut_color_element_b = document.createElement("szin_ut_b");
            ut_color_element_b.appendChild(document.createTextNode("" + szin_ut.getBlue()));
            ut_color_node.appendChild(ut_color_element_r);
            ut_color_node.appendChild(ut_color_element_g);
            ut_color_node.appendChild(ut_color_element_b);
            parameters.appendChild(ut_color_node);

            //cél szíénének beálítása
            Element cel_color_node = document.createElement("szin_cel");
            Element cel_color_element_r = document.createElement("szin_cel_r");
            cel_color_element_r.appendChild(document.createTextNode("" + szin_cel.getRed()));

            Element cel_color_element_g = document.createElement("szin_cel_g");
            cel_color_element_g.appendChild(document.createTextNode("" + szin_cel.getGreen()));

            Element cel_color_element_b = document.createElement("szin_cel_b");
            cel_color_element_b.appendChild(document.createTextNode("" + szin_cel.getBlue()));
            cel_color_node.appendChild(cel_color_element_r);
            cel_color_node.appendChild(cel_color_element_g);
            cel_color_node.appendChild(cel_color_element_b);
            parameters.appendChild(cel_color_node);

            Element start_color_node = document.createElement("szin_start");
            Element start_color_element_r = document.createElement("szin_start_r");
            start_color_element_r.appendChild(document.createTextNode("" + szin_start.getRed()));

            Element start_color_element_g = document.createElement("szin_start_g");
            start_color_element_g.appendChild(document.createTextNode("" + szin_start.getGreen()));

            Element start_color_element_b = document.createElement("szin_start_b");
            start_color_element_b.appendChild(document.createTextNode("" + szin_start.getBlue()));
            start_color_node.appendChild(start_color_element_r);
            start_color_node.appendChild(start_color_element_g);
            start_color_node.appendChild(start_color_element_b);
            parameters.appendChild(start_color_node);

            rootElement.appendChild(parameters);

            TransformerFactory transfact = TransformerFactory.newInstance();
            Transformer trasform = transfact.newTransformer();
            trasform.setOutputProperty(OutputKeys.INDENT, "yes");
            trasform.setOutputProperty(OutputKeys.METHOD, "xml");
            trasform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult stream = new StreamResult(f);

            trasform.transform(source, stream);

        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            JOptionPane.showMessageDialog(null, "Hiba az xml fájl írása közben\nMessage:" + ex.getMessage(), "Hiba történt", JOptionPane.WARNING_MESSAGE);
        } catch (TransformerException ex) {
            JOptionPane.showMessageDialog(null, "Hiba az xml fájl írása közben\nMessage:" + ex.getMessage(), "Hiba történt", JOptionPane.WARNING_MESSAGE);
        }
    }

}
