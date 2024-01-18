package com.brimmatech.FileConverter.XmlMapping;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Mapping_Table")
@ToString
public class MappingTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Column1;

    private String Column2;

    public MappingTable(String Column1, String Column2) {
        this.Column1 = Column1;
        this.Column2 = Column2;
    }
}
