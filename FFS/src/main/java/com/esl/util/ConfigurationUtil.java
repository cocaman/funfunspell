package com.esl.util;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import com.esl.dao.PracticeResultDAO;
import com.esl.model.*;

public class ConfigurationUtil {

    private static Document doc = null;

    // Default constructor
    public ConfigurationUtil() {}
    
    public ConfigurationUtil(String fileName) {
//        loadDocument(fileName);
//        initParameters();		// Set all parameters
    }
    
    public static void loadDocument(String fileName) {
    	File file = new File(fileName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValueByTagName(String tagName) {
        return doc.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    private static void initParameters() {        
        //PracticeResultDAO.TOP_RESULT_QUANTITY = Integer.parseInt(getValueByTagName("TOP_RESULT_QUANTITY"));
    //    PracticeResultDAO.MIN_FULL_MARK = Integer.parseInt(getValueByTagName("MIN_FULL_MARK"));
    }
}
