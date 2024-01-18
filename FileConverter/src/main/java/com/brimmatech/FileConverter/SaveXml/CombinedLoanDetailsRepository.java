package com.brimmatech.FileConverter.SaveXml;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombinedLoanDetailsRepository extends JpaRepository<CombinedLoanDetails, String> {

    boolean existsByLoanId(String loanId);


}
