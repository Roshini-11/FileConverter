package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.SaveXml.*;
import com.brimmatech.FileConverter.XmlMapping.MappingTableRepository;
import com.brimmatech.FileConverter.exception.DuplicateLoanIdException;
import com.brimmatech.FileConverter.exception.XmlParsingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class XmlProcessor {

    private static LoanInformationRepository loanInformationRepository;
    private static AdditionalLoanInformationRepository additionalLoanInformationRepository;
    private static CombinedLoanDetailsRepository combinedLoanDetailsRepository;

    private static MappingTableRepository mappingTableRepository;

    public XmlProcessor(LoanInformationRepository loanInformationRepository,
                        AdditionalLoanInformationRepository additionalLoanInformationRepository,
                        CombinedLoanDetailsRepository combinedLoanDetailsRepository,
                        MappingTableRepository mappingTableRepository) {
        XmlProcessor.loanInformationRepository = loanInformationRepository;
        XmlProcessor.additionalLoanInformationRepository = additionalLoanInformationRepository;
        XmlProcessor.combinedLoanDetailsRepository = combinedLoanDetailsRepository;
        XmlProcessor.mappingTableRepository = mappingTableRepository;
    }

    public static void parseXml(String xmlContent) throws XmlParsingException {
        try {
            log.info("Parsing XML content...");

            XmlMapper xmlMapper = new XmlMapper();

            LoanInformationListWrapper loanWrapper = xmlMapper.readValue(xmlContent, LoanInformationListWrapper.class);
            saveLoanInformation(loanWrapper.getLoans());

            AdditionalLoanInfoWrapper additionalLoanWrapper = xmlMapper.readValue(xmlContent, AdditionalLoanInfoWrapper.class);
            saveAdditionalLoanInformation(additionalLoanWrapper.getAdditionalLoanInformations());

            CombinedLoanWrapper combinedLoanWrapper = xmlMapper.readValue(xmlContent, CombinedLoanWrapper.class);
            saveLoanDetails(combinedLoanWrapper.getLoans());

            log.info("Parsing XML content...");

            log.info("XML parsing and saving completed successfully.");
        }  catch (DuplicateLoanIdException duplicateLoanIdException) {
            throw duplicateLoanIdException;
        } catch (Exception e) {
            log.error("Error parsing XML content. Check the XML format or content.", e);
            throw new XmlParsingException("Error parsing XML content. Check the XML format or content.", e);
        }
    }

    private static void saveLoanInformation(List<LoanInformation> loanInformations) {
        Set<String> uniqueLoanIds = new HashSet<>();

        for (LoanInformation loanInformation : loanInformations) {
            String loanId = String.valueOf(loanInformation.getLoanId());

            if (!uniqueLoanIds.add(loanId)) {
                log.error("Duplicate LoanID found: {}", loanId);
                throw new DuplicateLoanIdException("Duplicate LoanID found: " + loanId);
            }

            loanInformationRepository.save(loanInformation);
            log.info("LoanInformation saved successfully: {}", loanInformation);
        }
    }

    private static void saveAdditionalLoanInformation(List<AdditionalLoanInformation> additionalLoanInformations) {
        for (AdditionalLoanInformation additionalLoanInfo : additionalLoanInformations) {
            additionalLoanInformationRepository.save(additionalLoanInfo);
            log.info("AdditionalLoanInformation saved successfully: {}", additionalLoanInfo);
        }
    }

    private static void saveLoanDetails(List<CombinedLoanDetails> combinedLoanDetailList) {
        for (CombinedLoanDetails combinedLoanDetails : combinedLoanDetailList) {
            log.info(String.valueOf(combinedLoanDetails));
            combinedLoanDetailsRepository.save(combinedLoanDetails);
        }
    }
}


