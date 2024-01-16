package com.brimmatech.FileConverter.SaveXml;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@Entity
@Table(name = "Loan_Details")
@NoArgsConstructor
@ToString
@XmlRootElement
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CombinedLoanDetails {
    @Id
    @JacksonXmlProperty(localName = "LoanID")
    @JsonProperty("LoanID")
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

    @JacksonXmlProperty(localName = "LoanTerm")
    @NotNull(message = "LoanTerm cannot be null")
    private String loanterm;

    @JacksonXmlProperty(localName = "RateLock")
    @NotNull(message = "RateLock cannot be null")
    private String ratelock;

    @JacksonXmlProperty(localName = "Property")
    @NotNull(message = "Property cannot be null")
    private String property;

    @JacksonXmlProperty(localName = "ContactNo")
    @NotNull(message = "Contact Number cannot be null")
    private String contactno;

    @JacksonXmlProperty(localName = "Email")
    @NotNull(message = "Email cannot be null")
    private String email;

    @JacksonXmlProperty(localName = "Address")
    @NotNull(message = "Address cannot be null")
    private String address;

    @JsonIgnore
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private String createdBy = "system";
}
