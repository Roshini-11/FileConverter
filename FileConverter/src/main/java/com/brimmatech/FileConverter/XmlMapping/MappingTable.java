package com.brimmatech.FileConverter.XmlMapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "Mapping_Table")
@NoArgsConstructor
@ToString
public class MappingTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Column1;

    private String Column2;
}
