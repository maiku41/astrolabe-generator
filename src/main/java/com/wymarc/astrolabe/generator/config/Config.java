package com.wymarc.astrolabe.generator.config;

import com.wymarc.astrolabe.generator.AstrolabeGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Config {
    /**
     * $Id: AstrolabeGenerator,v 3.0
     * <p/>
     * The Astrolabe Generator is free software; you can redistribute it
     * and/or modify it under the terms of the GNU General Public License
     * as published by the Free Software Foundation; either version 2 of
     * the License, or(at your option) any later version.
     * <p/>
     * The Astrolabe Generator is distributed in the hope that it will be
     * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
     * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     * GNU General Public License for more details.
     * <p/>
     * You should have received a copy of the GNU General Public License
     * along with this program; if not, write to the Free Software
     * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
     * <p/>
     * Copyright (c) 2014, 2015, 2016, 2017 Timothy J. Mitchell
     *
     *
     *  http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
     *  http://www.vogella.com/tutorials/JavaXML/article.html
     */

    private String defaultLocationName = "";
    private String defaultLocation = "";
    private List<ClimateSet> climateSets;
    private List<AstrolabeExample> astrolabeExamples;

    public Config() {
        try {
            InputStream is = AstrolabeGenerator.class.getResourceAsStream("astrolabe_config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

            // get default location
            NodeList defaultSettings = doc.getElementsByTagName("defaultLocation");
            Element defaultSetting = (Element)defaultSettings.item(0);
            defaultLocationName = defaultSetting.getElementsByTagName("name").item(0).getTextContent();
            defaultLocation = defaultSetting.getElementsByTagName("location").item(0).getTextContent();

            // get example astrolabes
            astrolabeExamples = new ArrayList<AstrolabeExample>();
            NodeList astrolabes = doc.getElementsByTagName("astrolabe");
            for (int i = 0; i < astrolabes.getLength(); i++) {
                Node nNode = astrolabes.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    AstrolabeExample tempAstrolabe = new AstrolabeExample();
                    tempAstrolabe.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    tempAstrolabe.setShowThrone(eElement.getElementsByTagName("showThrone").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setShapeOption(Integer.parseInt(eElement.getElementsByTagName("shapeOption").item(0).getTextContent()));
                    tempAstrolabe.setZodiacSymbols(eElement.getElementsByTagName("zodiacSymbols").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setShowRegistration(eElement.getElementsByTagName("showRegistration").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setPrintOption(Integer.parseInt(eElement.getElementsByTagName("printOption").item(0).getTextContent()));
                    tempAstrolabe.setShowHorizonPlate(eElement.getElementsByTagName("showHorizonPlate").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setPrintTimeCorrection(eElement.getElementsByTagName("printTimeCorrection").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setHourMarkings(Integer.parseInt(eElement.getElementsByTagName("hourMarkings").item(0).getTextContent()));
                    tempAstrolabe.setAlmucantarInterval(Integer.parseInt(eElement.getElementsByTagName("almucantarInterval").item(0).getTextContent()));
                    tempAstrolabe.setShowAzimuth(eElement.getElementsByTagName("showAzimuth").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setShowTwilight(eElement.getElementsByTagName("showTwilight").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setDegreeScaleType(Integer.parseInt(eElement.getElementsByTagName("degreeScaleType").item(0).getTextContent()));
                    tempAstrolabe.setShowUnequalHours(eElement.getElementsByTagName("showUnequalHours").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setShowHouses(eElement.getElementsByTagName("showHouses").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setShowMansions(eElement.getElementsByTagName("showMansions").item(0).getTextContent().equals("true"));
                    tempAstrolabe.setBackTopLeft(Integer.parseInt(eElement.getElementsByTagName("backTopLeft").item(0).getTextContent()));
                    tempAstrolabe.setBackTopRight(Integer.parseInt(eElement.getElementsByTagName("backTopRight").item(0).getTextContent()));
                    tempAstrolabe.setBackBottomLeft(Integer.parseInt(eElement.getElementsByTagName("backBottomLeft").item(0).getTextContent()));
                    tempAstrolabe.setBackBottomRight(Integer.parseInt(eElement.getElementsByTagName("backBottomRight").item(0).getTextContent()));
                    tempAstrolabe.setReteType(Integer.parseInt(eElement.getElementsByTagName("reteType").item(0).getTextContent()));
                    tempAstrolabe.setShowCotangent(eElement.getElementsByTagName("showCotangent").item(0).getTextContent().equals("true"));

                    astrolabeExamples.add(tempAstrolabe);
                }
            }

            //get climate sets
            climateSets = new ArrayList<ClimateSet>();
            NodeList climateSetNodes = doc.getElementsByTagName("climateSet");
            for (int i = 0; i < climateSetNodes.getLength(); i++) {
                Node climateSetNode = climateSetNodes.item(i);
                ClimateSet climateSet = new ClimateSet();
                climateSet.setName(climateSetNode.getAttributes().getNamedItem("name").getNodeValue());
                if (climateSetNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element climateSetNodeElement = (Element) climateSetNode;
                    NodeList climateNodes = climateSetNodeElement.getElementsByTagName("climate");
                    for (int j = 0; j < climateNodes.getLength(); j++) {
                        Node climateNode = climateNodes.item(j);
                        ClimatePlate climatePlate = new ClimatePlate();
                        if (climateNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element plateElement = (Element) climateNode;
                            climatePlate.setName(plateElement.getElementsByTagName("name").item(0).getTextContent());
                            climatePlate.setLocation(plateElement.getElementsByTagName("latitude").item(0).getTextContent());
                        }
                        climateSet.addClimate(climatePlate);
                    }
                }
                climateSets.add(climateSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultLocationName() {
        return defaultLocationName;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public List<ClimateSet> getClimateSets(){
        return climateSets;
    }

    public ClimateSet getClimateSet(int index){
        return climateSets.get(index);
    }

    public List<AstrolabeExample> getAstrolabeExamples(){
        return astrolabeExamples;
    }

    public AstrolabeExample getAstrolabeExample(int index){
        return astrolabeExamples.get(index);
    }
}