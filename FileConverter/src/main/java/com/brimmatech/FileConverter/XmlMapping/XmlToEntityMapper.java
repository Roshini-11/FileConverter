package com.brimmatech.FileConverter.XmlMapping;


import com.brimmatech.FileConverter.SaveXml.CombinedLoanDetails;

import java.util.Date;
import java.util.Map;

public class XmlToEntityMapper {

    public static CombinedLoanDetails mapXmlValuesToEntityAttributes(Map<String, String> xmlValues) {
        CombinedLoanDetails loanDetails = new CombinedLoanDetails();

        loanDetails.setLoanId(xmlValues.get("LoanID"));
        loanDetails.setApplicant(xmlValues.get("Applicant"));
        loanDetails.setEstpropertyvalue(xmlValues.get("EstPropertyValue"));
        loanDetails.setLoantype(xmlValues.get("LoanType"));
        loanDetails.setPurpose(xmlValues.get("Purpose"));
        loanDetails.setProduct(xmlValues.get("Product"));
        loanDetails.setLoanterm(xmlValues.get("LoanTerm"));
        loanDetails.setRatelock(xmlValues.get("RateLock"));
        loanDetails.setProperty(xmlValues.get("Property"));
        loanDetails.setContactno(xmlValues.get("ContactNo"));
        loanDetails.setEmail(xmlValues.get("Email"));
        loanDetails.setAddress(xmlValues.get("Address"));
        loanDetails.setCreatedDate(new Date());

        return loanDetails;
    }
}
