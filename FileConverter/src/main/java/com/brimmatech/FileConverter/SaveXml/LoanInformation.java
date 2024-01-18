package com.brimmatech.FileConverter.SaveXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Loan_Information")
@NoArgsConstructor
@ToString
@XmlRootElement
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanInformation {

    @Id
    @JacksonXmlProperty(localName = "LoanID")
    private String loanId;

    @JacksonXmlProperty(localName = "Applicant")
    @NotNull(message = "Applicant cannot be null")
    private String applicant;

    @JacksonXmlProperty(localName = "EstPropertyValue")
    @NotNull(message = "Estimation Property Value  cannot be null")
    private String estpropertyvalue;

    @JacksonXmlProperty(localName = "LoanType")
    @NotNull(message = "LoanType cannot be null")
    private String loantype;

    @JacksonXmlProperty(localName = "Purpose")
    @NotNull(message = "Purpose cannot be null")
    private String purpose;

    @JacksonXmlProperty(localName = "Product")
    @NotNull(message = "Product cannot be null")
    private String product;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    public LoanInformation(String loanId) {
        this.loanId = loanId;
    }

}
