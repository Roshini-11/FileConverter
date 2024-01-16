package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.SaveXml.CombinedLoanDetails;
import com.brimmatech.FileConverter.SaveXml.CombinedLoanWrapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

@Service
public class TextToXmlConversion implements FileConversion {

    private static final Map<String, String> FIELD_MAPPING;

    static {
        FIELD_MAPPING = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        FIELD_MAPPING.put("Loanid", "setLoanId");
        FIELD_MAPPING.put("Applicant", "setApplicant");
        FIELD_MAPPING.put("EstPropertyValue", "setEstpropertyvalue");
        FIELD_MAPPING.put("LoanType", "setLoantype");
        FIELD_MAPPING.put("Purpose", "setPurpose");
        FIELD_MAPPING.put("Product", "setProduct");
        FIELD_MAPPING.put("LoanTerm", "setLoanterm");
        FIELD_MAPPING.put("RateLock", "setRatelock");
        FIELD_MAPPING.put("Property", "setProperty");
        FIELD_MAPPING.put("ContactNo", "setContactno");
        FIELD_MAPPING.put("Email", "setEmail");
        FIELD_MAPPING.put("Address", "setAddress");
    }


    @Override
    public String conversion(String data) throws ValidationException {
        List<CombinedLoanDetails> loanDetailsList = parseTextLines(data.lines());

        CombinedLoanWrapper combinedLoanWrapper = new CombinedLoanWrapper();
        combinedLoanWrapper.setLoans(loanDetailsList);

        return convertCombinedLoanWrapperToXml(combinedLoanWrapper);
    }

    private List<CombinedLoanDetails> parseTextLines(Stream<String> lines) throws ValidationException {
        List<CombinedLoanDetails> loanDetailsList = lines
                .collect(ArrayList::new, this::accumulateLoanDetails, ArrayList::addAll);

        for (CombinedLoanDetails loanDetails : loanDetailsList) {
            validateEntityElements(loanDetails);
        }

        return loanDetailsList;
    }

    private void validateEntityElements(CombinedLoanDetails loanDetails) throws ValidationException {
        if (isAnyEntityElementMissing(loanDetails)) {
            throw new ValidationException("Missing required fields");
        }
    }

    private boolean isAnyEntityElementMissing(CombinedLoanDetails loanDetails) {
        return Stream.of(
                        loanDetails.getLoanId(), loanDetails.getApplicant(), loanDetails.getEstpropertyvalue(),
                        loanDetails.getLoantype(), loanDetails.getPurpose(), loanDetails.getProduct(),
                        loanDetails.getLoanterm(), loanDetails.getRatelock(), loanDetails.getProperty(),
                        loanDetails.getContactno(), loanDetails.getEmail(), loanDetails.getAddress())
                .anyMatch(value -> value == null || (value instanceof String && ((String) value).isEmpty()));
    }



    private void accumulateLoanDetails(List<CombinedLoanDetails> loanDetailsList, String line) {
        if (line.startsWith("Loanid")) {
            CombinedLoanDetails loanDetails = new CombinedLoanDetails();
            loanDetailsList.add(loanDetails);
        }

        if (!loanDetailsList.isEmpty()) {
            CombinedLoanDetails loanDetails = loanDetailsList.get(loanDetailsList.size() - 1);
            parseLine(loanDetails, line);
        }
    }

    private void parseLine(CombinedLoanDetails loanDetails, String line) {
        String[] keyValue = line.split(" = ");
        if (keyValue.length == 2) {
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            String methodName = FIELD_MAPPING.get(key.toLowerCase());
            if (methodName != null) {
                invokeSetter(loanDetails, methodName, value);
            }
        }
    }

    private void invokeSetter(Object object, String methodName, String value) {
        try {
            Method setter = CombinedLoanDetails.class.getMethod(methodName, String.class);
            setter.invoke(object, value);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Setter method not found: " + methodName, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing setter method: " + methodName, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Error invoking setter method: " + methodName, e.getCause());
        }
    }



    private String convertCombinedLoanWrapperToXml(CombinedLoanWrapper combinedLoanWrapper) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            StringWriter writer = new StringWriter();
            xmlMapper.writeValue(writer, combinedLoanWrapper);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error converting to XML", e);
        }
    }
}
