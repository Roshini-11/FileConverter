package com.brimmatech.FileConverter.SaveXml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "LoanInformation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CombinedLoanWrapper {

    @JacksonXmlProperty(localName = "loan")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CombinedLoanDetails> loans;
}
