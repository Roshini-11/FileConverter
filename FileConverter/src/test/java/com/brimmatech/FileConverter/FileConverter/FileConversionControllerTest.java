package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.SaveXml.LoanInformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileConversionControllerTest {

    @InjectMocks
    private FileConversionController fileConversionController;

    @Mock
    private FileConverterService fileConverterService;

    private AutoCloseable autoCloseable;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fileConversionController).build();
    }

    @AfterEach
    void cleanUp() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testConvert_statusIsOk() throws Exception {
        String direction = "xml-to-json";

        Mockito.when(fileConverterService.convert(Mockito.anyString(), Mockito.any(MultipartFile.class))).thenReturn("Xml file");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/convert")
                        .file(new MockMultipartFile("file", "filename.xml", "text/xml", "Xml content".getBytes()))
                        .param("direction", direction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testConvert_statusIsNotFound() throws Exception {
        String direction = "xml-to-json";

        Mockito.when(fileConverterService.convert(Mockito.anyString(), Mockito.any(MultipartFile.class))).thenReturn("Xml file");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/apiconvert")
                        .file(new MockMultipartFile("file", "filename.xml", "text/xml", "Xml content".getBytes()))
                        .param("direction", direction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFilterLoanData_() throws Exception {

        LoanInformation loanInformation = new LoanInformation();
        loanInformation.setLoanId("123");
        loanInformation.setLoantype("FHA");
        loanInformation.setPurpose("Construction");

        List<LoanInformation> loanInformationList = new ArrayList<>();
        loanInformationList.add(loanInformation);

        Mockito.when(fileConverterService.filterLoan(Mockito.any())).thenReturn(loanInformationList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/filter/loanInformation")
                        .param("key", "value")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}