package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.exception.ConversionException;
import com.brimmatech.FileConverter.exception.InvalidDirectionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.IOException;

import static org.mockito.Mockito.mock;


public class FileConverterServiceTest {

    @InjectMocks
    private FileConverterService fileConverterService;

    @Mock
    private XmlProcessor xmlProcessor;

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
    void testConvertXmlToJson() throws ConversionException, ValidationException, IOException {
        String xmlContent = "<LoanInformation>\n" +
                "<loan>\n" +
                "   <LoanID>1002</LoanID>\n" +
                "   <Applicant>Max Will</Applicant>\n" +
                "   <EstPropertyValue>$33000</EstPropertyValue>\n" +
                "   <LoanType>VA</LoanType>\n" +
                "   <Purpose>Construction</Purpose>\n" +
                "   <Product>11 mo. Interest Only, Fixed Rate</Product>\n" +
                "   <LoanTerm>1 year</LoanTerm>\n" +
                "   <RateLock>No</RateLock>\n" +
                "   <Property>123 EXAMPLE DRSIOUX FALLS, SD 57106</Property>\n" +
                "   <ContactNo>1234567890</ContactNo>\n" +
                "   <Email>paul@gmail.com</Email>\n" +
                "   <Address>64 wake forest, California</Address>\n" +
                "</loan>\n" +
                "</LoanInformation>";
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(xmlContent.getBytes());

        String result = fileConverterService.convert("xml-to-json", file);

        String jsonResult = "{\"LoanInformation\":{\"loan\":{\"LoanID\":\"1002\",\"Applicant\":\"Max Will\",\"EstPropertyValue\":\"$33000\",\"LoanType\":\"VA\",\"Purpose\":\"Construction\",\"Product\":\"11 mo. Interest Only, Fixed Rate\",\"LoanTerm\":\"1 year\",\"RateLock\":\"No\",\"Property\":\"123 EXAMPLE DRSIOUX FALLS, SD 57106\",\"ContactNo\":\"1234567890\",\"Email\":\"paul@gmail.com\",\"Address\":\"64 wake forest, California\"}}}";

        Assertions.assertNotNull(result);
        Assertions.assertEquals(jsonResult, result);
    }

    @Test
    void testConvert_InvalidDirection() throws IOException {
        String xmlContent = "<LoanInformation>\n" +
                "<loan>\n" +
                "   <LoanID>1002</LoanID>\n" +
                "   <Applicant>Max Will</Applicant>\n" +
                "   <EstPropertyValue>$33000</EstPropertyValue>\n" +
                "   <LoanType>VA</LoanType>\n" +
                "   <Purpose>Construction</Purpose>\n" +
                "   <Product>11 mo. Interest Only, Fixed Rate</Product>\n" +
                "   <LoanTerm>1 year</LoanTerm>\n" +
                "   <RateLock>No</RateLock>\n" +
                "   <Property>123 EXAMPLE DRSIOUX FALLS, SD 57106</Property>\n" +
                "   <ContactNo>1234567890</ContactNo>\n" +
                "   <Email>paul@gmail.com</Email>\n" +
                "   <Address>64 wake forest, California</Address>\n" +
                "</loan>\n" +
                "</LoanInformation>";
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(xmlContent.getBytes());

        Assertions.assertThrows(InvalidDirectionException.class, () -> fileConverterService.convert("invalid-direction", file));
    }

    @Test
    void testReadFileContent() throws ConversionException, IOException {
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn("file content".getBytes());

        String result = fileConverterService.readFileContent(file);

        Assertions.assertEquals("file content", result);
    }

    @Test
    void testReadFileContentIOException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenThrow(IOException.class);

        Assertions.assertThrows(ConversionException.class, () -> fileConverterService.readFileContent(file));
    }

    @Test
    void testConvertXmlToJson_xmlParsingException() throws ConversionException, IOException {
        String xmlContent = "<loan>\n" +
                "   <LoanID>1002</LoanID>\n" +
                "   <Applicant>Max Will</Applicant>\n" +
                "   <EstPropertyValue>$33000</EstPropertyValue>\n" +
                "   <LoanType>VA</LoanType>\n" +
                "   <Purpose>Construction</Purpose>\n" +
                "   <Product>11 mo. Interest Only, Fixed Rate</Product>\n" +
                "   <LoanTerm>1 year</LoanTerm>\n" +
                "   <RateLock>No</RateLock>\n" +
                "   <Property>123 EXAMPLE DRSIOUX FALLS, SD 57106</Property>\n" +
                "   <ContactNo>1234567890</ContactNo>\n" +
                "   <Email>paul@gmail.com</Email>\n" +
                "   <Address>64 wake forest, California</Address>\n" +
                "</loan>\n" +
                "</LoanInformation>";
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(xmlContent.getBytes());

        Assertions.assertThrows(ConversionException.class, () -> fileConverterService.convert("xml-to-json", file));

    }

    @Test
    void testConvertJsonToXml_InavlidJson_throwsException() throws ConversionException, IOException {
        String xmlContent = "{\n" +
                "  \"LoanInformation\": {\n" +
                "    \"loan\": {\n" +
                "      \"LoanID\": 1005,\n" +
                "      \"Applicant\": \"Renera\",\n" +
                "      \"EstPropertyValue\": \"$33000\",\n" +
                "      \"LoanType\": \"FHA\",\n" +
                "      \"Purpose\": \"Construction\",\n" +
                "      \"Product\": \"11 mo. Interest Only, Fixed Rate\",\n" +
                "      \"LoanTerm\": \"1 year\"\n" +
                "      \"RateLock\": \"No\",\n" +
                "      \"Property\": \"123 EXAMPLE DRSIOUX FALLS, SD 57106\",\n" +
                "      \"ContactNo\": 1234567890,\n" +
                "      \"Email\": \"paul@gmail.com\",\n" +
                "      \"Address\": \"64 wake forest, California\",\n" +
                "      \"Gender\": \"MAle\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(xmlContent.getBytes());

        Assertions.assertThrows(ConversionException.class, () -> fileConverterService.convert("json-to-xml", file));

    }

    @Test
    void testConvertJsonToXml_validJson() throws ConversionException, ValidationException, IOException {
        String xmlContent = "{\n" +
                "  \"LoanInformation\": {\n" +
                "    \"loan\": {\n" +
                "      \"LoanID\": 1005,\n" +
                "      \"Applicant\": \"Renera\",\n" +
                "      \"EstPropertyValue\": \"$33000\",\n" +
                "      \"LoanType\": \"FHA\",\n" +
                "      \"Purpose\": \"Construction\",\n" +
                "      \"Product\": \"11 mo. Interest Only, Fixed Rate\",\n" +
                "      \"LoanTerm\": \"1 year\",\n" +
                "      \"RateLock\": \"No\",\n" +
                "      \"Property\": \"123 EXAMPLE DRSIOUX FALLS, SD 57106\",\n" +
                "      \"ContactNo\": 1234567890,\n" +
                "      \"Email\": \"paul@gmail.com\",\n" +
                "      \"Address\": \"64 wake forest, California\",\n" +
                "      \"Gender\": \"MAle\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(xmlContent.getBytes());

        String result = fileConverterService.convert("json-to-xml", file);

        String jsonResult = "<LoanInformation><loan><LoanID>1005</LoanID><Applicant>Renera</Applicant><EstPropertyValue>$33000</EstPropertyValue><LoanType>FHA</LoanType><Purpose>Construction</Purpose><Product>11 mo. Interest Only, Fixed Rate</Product><LoanTerm>1 year</LoanTerm><RateLock>No</RateLock><Property>123 EXAMPLE DRSIOUX FALLS, SD 57106</Property><ContactNo>1234567890</ContactNo><Email>paul@gmail.com</Email><Address>64 wake forest, California</Address><Gender>MAle</Gender></loan></LoanInformation>";

        Assertions.assertNotNull(result);
        Assertions.assertEquals(jsonResult, result);
    }

    @Test
    void testConvertTextToXml() throws ConversionException, ValidationException, IOException {
        String xmlContent = "Loanid = 1005\n" +
                "applicant = Max Will\n" +
                "EstPropertyValue = $33000\n" +
                "LoanType = VA\n" +
                "Purpose = Construction\n" +
                "Product = 11 mo. Interest Only, Fixed Rate\n" +
                "LoanTerm = 1 year\n" +
                "RateLock = No\n" +
                "Property = 123 EXAMPLE DRSIOUX FALLS, SD 57106\n" +
                "contactNo = 1234567890\n" +
                "Email = paul@gmail.com\n" +
                "Address = 64 wake forest, California";
        MultipartFile file = mock(MultipartFile.class);
        Mockito.when(file.getBytes()).thenReturn(xmlContent.getBytes());

        String result = fileConverterService.convert("text-to-xml", file);

        String jsonResult = "<LoanInformation><loan><LoanID>1005</LoanID><Applicant>Max Will</Applicant><EstPropertyValue>$33000</EstPropertyValue><LoanType>VA</LoanType><Purpose>Construction</Purpose><Product>11 mo. Interest Only, Fixed Rate</Product><LoanTerm>1 year</LoanTerm><RateLock>No</RateLock><Property>123 EXAMPLE DRSIOUX FALLS, SD 57106</Property><ContactNo>1234567890</ContactNo><Email>paul@gmail.com</Email><Address>64 wake forest, California</Address></loan></LoanInformation>";

        Assertions.assertNotNull(result);
        Assertions.assertEquals(jsonResult, result);
    }
}
