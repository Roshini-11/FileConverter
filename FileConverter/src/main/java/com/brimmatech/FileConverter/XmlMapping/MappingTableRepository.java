package com.brimmatech.FileConverter.XmlMapping;

import com.brimmatech.FileConverter.XmlMapping.MappingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MappingTableRepository extends JpaRepository<MappingTable, String> {

    @Query(value = "SELECT * FROM Mapping_Table", nativeQuery = true)
    List<MappingTable> findAll();
//
//    @Query(value = "SELECT column1 FROM Mapping_Table", nativeQuery = true)
//    List<String> getAllColumn1Values();
//
//    @Query(value = "SELECT column2 FROM Mapping_Table", nativeQuery = true)
//    List<String> getAllColumn2Values();

}
