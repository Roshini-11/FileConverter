package com.brimmatech.FileConverter.SaveXml;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdditionalLoanInformationRepository extends JpaRepository<AdditionalLoanInformation, String> {
    @Query(value = "SELECT * FROM Additional_Loan_Information\n" +
            "WHERE\n" +
            "    (:loanterm is null OR LOWER(loanterm) = LOWER(:loanterm) OR LOWER(loanterm) LIKE LOWER(CONCAT('%', :loanterm, '%')))\n" +
            "    AND\n" +
            "    (:property is null OR LOWER(property) = LOWER(:property) OR LOWER(property) LIKE LOWER(CONCAT('%', :property, '%')))\n" +
            "    AND\n" +
            "    (:ratelock is null OR LOWER(ratelock) = LOWER(:ratelock) OR LOWER(ratelock) LIKE LOWER(CONCAT('%', :ratelock, '%')))\n" +
            "    AND\n" +
            "    (:contactno is null OR LOWER(contactno) = LOWER(:contactno) OR LOWER(contactno) LIKE LOWER(CONCAT('%', :contactno, '%')))\n" +
            "    AND\n" +
            "    (:email is null OR LOWER(email) = LOWER(:email) OR LOWER(email) LIKE LOWER(CONCAT('%', :email, '%')))\n" +
            "    AND\n" +
            "    (:address is null OR LOWER(address) = LOWER(:address) OR LOWER(address) LIKE LOWER(CONCAT('%', :address, '%')))", nativeQuery = true)
    List<AdditionalLoanInformation> filterData(@Param("loanterm") String loanterm,
                                               @Param("property") String property,
                                               @Param("ratelock") String ratelock,
                                               @Param("contactno") String contactno,
                                               @Param("email") String email,
                                               @Param("address") String address);

    default List<AdditionalLoanInformation> filterData(Map<String, String> params) {
        return filterData(
                params.get("loanterm"),
                params.get("property"),
                params.get("ratelock"),
                params.get("contactno"),
                params.get("email"),
                params.get("address")
        );
    }



}
