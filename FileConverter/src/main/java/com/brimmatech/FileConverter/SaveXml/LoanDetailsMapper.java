package com.brimmatech.FileConverter.SaveXml;

import java.util.ArrayList;
import java.util.List;

public class LoanDetailsMapper {

    public static List<CombinedLoanDetails> mapToLoanDetails(String data) {
        List<CombinedLoanDetails> loanDetailsList = new ArrayList<>();

        // Split the data into individual records (assuming each record is separated by a newline)
        String[] records = data.split("\\n");

        for (String record : records) {
            // Split each record into key-value pairs (assuming each pair is separated by '=')
            String[] keyValuePairs = record.split("=");

            // Create a new CombinedLoanDetails instance and set its properties based on key-value pairs
            CombinedLoanDetails loanDetails = createCombinedLoanDetails(keyValuePairs);

            // Add the created CombinedLoanDetails to the list
            loanDetailsList.add(loanDetails);
        }

        return loanDetailsList;
    }

//    private static CombinedLoanDetails createCombinedLoanDetails(String[] keyValuePairs) {
//        CombinedLoanDetails loanDetails = new CombinedLoanDetails();
//
//        for (String keyValuePair : keyValuePairs) {
//            // Split each key-value pair into key and value
//            String[] parts = keyValuePair.trim().split("\\s*=\\s*");
//
//            if (parts.length == 2) {
//                // Map each property based on the key
//                switch (parts[0].trim()) {
//                    case "Loanid":
//                        loanDetails.setLoanId(parts[1].trim());
//                        break;
//                    case "Applicant":
//                        loanDetails.setApplicant(parts[1].trim());
//                        break;
//                    case "EstPropertyValue":
//                        loanDetails.setEstpropertyvalue(parts[1].trim());
//                        break;
//                    case "LoanType":
//                        loanDetails.setLoantype(parts[1].trim());
//                        break;
//                    case "Purpose":
//                        loanDetails.setPurpose(parts[1].trim());
//                        break;
//                    case "Product":
//                        loanDetails.setProduct(parts[1].trim());
//                        break;
//                    case "LoanTerm":
//                        loanDetails.setLoanterm(parts[1].trim());
//                        break;
//                    case "RateLock":
//                        loanDetails.setRatelock(parts[1].trim());
//                        break;
//                    case "Property":
//                        loanDetails.setProperty(parts[1].trim());
//                        break;
//                    case "ContactNo":
//                        loanDetails.setContactno(parts[1].trim());
//                        break;
//                    case "Email":
//                        loanDetails.setEmail(parts[1].trim());
//                        break;
//                    case "Address":
//                        loanDetails.setAddress(parts[1].trim());
//                        break;
//                    // Add other properties mapping based on the key
//                    // ...
//                }
//            }
//        }
//
//        return loanDetails;
//    }

    private static CombinedLoanDetails createCombinedLoanDetails(String[] keyValuePairs) {
        CombinedLoanDetails loanDetails = new CombinedLoanDetails();

        for (String keyValuePair : keyValuePairs) {
            // Split each key-value pair into key and value
            String[] parts = keyValuePair.trim().split("\\s*=\\s*");

            System.out.println(parts);

            if (parts.length == 2) {
                // Map each property based on the key
                switch (parts[0].trim()) {
                    case "Loanid":
                        loanDetails.setLoanId(parts[1].trim());
                        break;
                    case "Applicant":
                        loanDetails.setApplicant(parts[1].trim());
                        break;
                    // Add other cases for remaining properties...
                    default:
                        // Log unhandled property keys
                        System.out.println("Unhandled property key: " + parts[0].trim());
                }
            } else {
                // Log invalid key-value pairs
                System.out.println("Invalid key-value pair: " + keyValuePair);
            }
        }

        return loanDetails;
    }

}
