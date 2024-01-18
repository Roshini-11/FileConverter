package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.exception.ConversionException;
import com.brimmatech.FileConverter.exception.XmlParsingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Slf4j
@Service
public class XmlToJsonConversion implements FileConversion {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    private final XmlProcessor xmlProcessor;

    @Autowired
    public XmlToJsonConversion(XmlProcessor xmlProcessor){
        this.xmlProcessor = xmlProcessor;
    }

    @Override
    public String conversion(String data) {
        try {

            xmlProcessor.parseXml(data);

            log.info("xmlProcessor instance: {}", xmlProcessor);


            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            log.info(data);
            Document document = documentBuilder.parse(new InputSource(new StringReader(data)));

            String rootElement = document.getDocumentElement().getNodeName();

            ObjectNode objectNode = objectMapper.createObjectNode();

            JsonNode rootNode = xmlMapper.readTree(data);

            objectNode.set(rootElement, rootNode);

            String jsonResult = objectNode.toString();

            return jsonResult;

        } catch (XmlParsingException e) {
            log.error("Error parsing XML content in conversion method: {}", e.getMessage());
            throw e;
        }catch (SAXParseException e) {
            log.error("Error parsing XML: Line {}, Column {}, Message: {}",
                    e.getLineNumber(), e.getColumnNumber(), e.getMessage());
            throw new ConversionException("Error parsing XML", e);
        }
        catch (javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException | java.io.IOException e) {
            log.error("Error during XML parsing: {}", e.getMessage());
            throw new XmlParsingException("Error during XML parsing", e);
        }
    }
}

