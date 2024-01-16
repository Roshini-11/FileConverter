package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.SaveXml.*;
import com.brimmatech.FileConverter.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileConverterService {

    private Map<String, FileConversion> conversion;

    @Autowired
    private CombinedLoanDetailsRepository combinedLoanDetailsRepository;

    @Autowired
    private LoanInformationRepository loanInformationRepository;

    @Autowired
    private AdditionalLoanInformationRepository additionalLoanInformationRepository;

    public FileConverterService(){
        conversion = new HashMap<>();
        conversion.put("xml-to-json", new XmlToJsonConversion());
        conversion.put("json-to-xml", new JsonToXmlConversion());
        conversion.put("text-to-xml", new TextToXmlConversion());
    }

    public String convert(String direction, MultipartFile file) throws ConversionException, ValidationException {
        String data = readFileContent(file);
        FileConversion fileConversion = Optional.ofNullable(conversion.get(direction.toLowerCase()))
                .orElseThrow(InvalidDirectionException::new);

        return fileConversion.conversion(data);
    }

    public String readFileContent(MultipartFile file) throws ConversionException {
        try {
            return new String(file.getBytes());
        } catch (IOException e) {
            throw new ConversionException("Failed to read file content", e);
        }
    }

    public List<CombinedLoanDetails> getAllLoans() {
        return combinedLoanDetailsRepository.findAll();
    }

    public List<LoanInformation> filterLoan(Map<String, String> filterBy) {
        return loanInformationRepository.filterData(filterBy);
    }

    public List<AdditionalLoanInformation> filterAdditionalInfo(Map<String, String> filterBy) {
        return additionalLoanInformationRepository.filterData(filterBy);
    }

    public void validateFilterKeys(Map<String, String> filterBy) {
        validateFilterKeysInternal(filterBy);
    }

    private static void validateFilterKeysInternal(Map<String, String> filterBy) {
        Set<String> validKeys = Arrays.stream(LoanInformation.class.getDeclaredFields())
                .map(java.lang.reflect.Field::getName)
                .collect(Collectors.toSet());

        Set<String> invalidKeys = filterBy.keySet().stream()
                .filter(key -> !validKeys.contains(key))
                .collect(Collectors.toSet());

        if (!invalidKeys.isEmpty()) {
            throw new NoDataException("No data found" , new RuntimeException("Invalid keys found"));
        }
    }
}

