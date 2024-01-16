package com.brimmatech.FileConverter.SaveXml;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
    public interface LoanInformationRepository extends JpaRepository<LoanInformation, String> {
    @Query(value = "SELECT * FROM Loan_Information " +
            "WHERE " +
            "(:lt is null OR LOWER(loantype) = LOWER(:lt)) " +
            "AND " +
            "(:applicant is null OR LOWER(applicant) LIKE LOWER(CONCAT('%', :applicant, '%'))) " +
            "AND " +
            "(:estpropertyvalue is null OR LOWER(estpropertyvalue) = LOWER(:estpropertyvalue)) " +
            "AND " +
            "(:purpose is null OR LOWER(purpose) LIKE LOWER(CONCAT('%', :purpose, '%'))) " +
            "AND " +
            "(:product is null OR LOWER(product) LIKE LOWER(CONCAT('%', :product, '%')))", nativeQuery = true)
    List<LoanInformation> filterData(@Param("lt") String loantype,
                                     @Param("applicant") String applicant,
                                     @Param("estpropertyvalue") String estpropertyvalue,
                                     @Param("purpose") String purpose,
                                     @Param("product") String product);

    default List<LoanInformation> filterData(Map<String, String> params) {
        return filterData(
                params.get("loantype"),
                params.get("applicant"),
                params.get("estpropertyvalue"),
                params.get("purpose"),
                params.get("product")
        );
    }

}

