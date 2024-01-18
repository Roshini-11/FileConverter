package com.brimmatech.FileConverter.SaveXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Additional_Loan_Information")
@NoArgsConstructor
@ToString
@XmlRootElement
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalLoanInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "additional_loan_info_id")
    private UUID additionalLoanInfoId;

    @ManyToOne
    @JoinColumn(name = "LoanID")
    @JacksonXmlProperty(localName = "LoanID")
    private LoanInformation loanInformation;

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

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private String createdBy = "system";
}
