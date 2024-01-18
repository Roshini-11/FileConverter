package com.brimmatech.FileConverter.XmlMapping;

import com.brimmatech.FileConverter.FileConverter.FileConverterService;
import com.brimmatech.FileConverter.SaveXml.CombinedLoanDetails;
import com.brimmatech.FileConverter.SaveXml.CombinedLoanDetailsRepository;
import com.brimmatech.FileConverter.exception.DuplicateLoanIdException;
import com.brimmatech.FileConverter.exception.XmlParsingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XmlMappingServiceTest {

    @InjectMocks
    private XmlMappingService xmlMappingService;
    @Mock
    private CombinedLoanDetailsRepository combinedLoanDetailsRepository;
    @Mock
    private MappingTableRepository mappingTableRepository;
    @Mock
    private FileConverterService fileConverterService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testParseXmlWithInvalidXmlFormat() {
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(fileConverterService.readFileContent(file)).thenReturn("invalid-xml-format");

        Assertions.assertThrows(XmlParsingException.class, () -> xmlMappingService.parseXml(file));
    }

}
