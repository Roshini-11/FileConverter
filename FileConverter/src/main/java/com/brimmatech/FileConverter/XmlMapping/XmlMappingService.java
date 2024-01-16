package com.brimmatech.FileConverter.XmlMapping;

import com.brimmatech.FileConverter.FileConverter.FileConverterService;
import com.brimmatech.FileConverter.SaveXml.CombinedLoanDetails;
import com.brimmatech.FileConverter.SaveXml.CombinedLoanDetailsRepository;
import com.brimmatech.FileConverter.exception.DuplicateLoanIdException;
import com.brimmatech.FileConverter.exception.XmlParsingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.brimmatech.FileConverter.XmlMapping.XmlToEntityMapper.mapXmlValuesToEntityAttributes;

@Service
@Slf4j
public class XmlMappingService {

    private  CombinedLoanDetailsRepository combinedLoanDetailsRepository;

    private  MappingTableRepository mappingTableRepository;

    private FileConverterService fileConverterService;

    public XmlMappingService(CombinedLoanDetailsRepository combinedLoanDetailsRepository,
                             MappingTableRepository mappingTableRepository,
                             FileConverterService fileConverterService){
        this.combinedLoanDetailsRepository = combinedLoanDetailsRepository;
        this.mappingTableRepository = mappingTableRepository;
        this.fileConverterService = fileConverterService;
    }


    public String parseXml(MultipartFile file) {
        String xmlString = fileConverterService.readFileContent(file);
        try {
            System.out.println("XML Content: " + xmlString);
            List<Map<String, String>> loanList = extractLoanData(xmlString);

            List<MappingTable> mappingList = mappingTableRepository.findAll();
            Map<String, String> columnMapping = buildColumnMapping(mappingList);

            processLoanData(loanList, columnMapping);

            return "XML parsing and processing successful";
        } catch (DuplicateLoanIdException duplicateLoanIdException) {
            throw duplicateLoanIdException;
        } catch (Exception e) {
            throw new XmlParsingException("Error parsing XML content. Check the XML format or content.", e);
        }
    }

    private  List<Map<String, String>> extractLoanData(String xmlString) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlString);

            List<Map<String, String>> loanList = new ArrayList<>();

            JsonNode loanNode = rootNode.findValue("Loan");
            if (loanNode.isArray()) {
                for (JsonNode individualLoan : loanNode) {
                    Map<String, String> loanValues = xmlMapper.convertValue(individualLoan, new TypeReference<Map<String, String>>() {});
                    loanList.add(loanValues);
                }
            }else {
                Map<String, String> loanValues = xmlMapper.convertValue(loanNode, new TypeReference<Map<String, String>>() {});
                loanList.add(loanValues);
            }
            return loanList;
        } catch (Exception e) {
            throw new XmlParsingException("Error parsing XML content. Check the XML format or content.", e);
        }
    }

    private  Map<String, String> buildColumnMapping(List<MappingTable> mappingList) {
        Map<String, String> columnMapping = new HashMap<>();
        for (MappingTable mapping : mappingList) {
            String sourceColumn = mapping.getColumn2().toLowerCase();
            String destinationColumn = mapping.getColumn1();

            columnMapping.put(sourceColumn, destinationColumn);
        }
        return columnMapping;
    }

    private  void processLoanData(List<Map<String, String>> loanList, Map<String, String> columnMapping) {
        for (Map<String, String> xmlValues : loanList) {
            Map<String, String> mappedXmlValues = mapXmlToEntity(xmlValues, columnMapping);

            String loanId = mappedXmlValues.get("LoanID");
            checkForDuplicateLoanId(loanId);

            CombinedLoanDetails loanDetails = mapXmlValuesToEntityAttributes(mappedXmlValues);
            combinedLoanDetailsRepository.save(loanDetails);
        }
    }

    private  void checkForDuplicateLoanId(String loanId) {
        if (loanId != null && combinedLoanDetailsRepository.existsByLoanId(loanId)) {
            throw new DuplicateLoanIdException("LoanId already exists: " + loanId);
        }
    }

    private  Map<String, String> mapXmlToEntity(Map<String, String> xmlValues, Map<String, String> columnMapping) {
        Map<String, String> mappedXmlValues = new HashMap<>();
        for (Map.Entry<String, String> entry : xmlValues.entrySet()) {
            String xmlElement = entry.getKey().toLowerCase();
            System.out.println("Lowercase XML Element: " + xmlElement);

            String entityColumn = columnMapping.get(xmlElement);
            if (entityColumn != null) {
                mappedXmlValues.put(entityColumn, entry.getValue());
            } else {
                System.out.println("No mapping found for XML Element: " + entry.getKey());
                System.out.println("Available keys in columnMapping: " + columnMapping.keySet());
            }
        }
        return mappedXmlValues;
    }

}
