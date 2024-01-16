package com.brimmatech.FileConverter.FileConverter;

import com.brimmatech.FileConverter.SaveXml.*;
import com.brimmatech.FileConverter.responseHandling.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FileConversionController {

    private FileConverterService fileConverterService;

    @PostMapping("/convert")
    public ResponseEntity<ResponseMessage<String>> convert(@RequestParam String direction,
                                                           @RequestPart MultipartFile file) {
        try {
            String response = fileConverterService.convert(direction, file);
            return ResponseEntity.ok(new ResponseMessage<>(HttpStatus.OK.value(), "Success", response));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/filter/loanInformation")
    public ResponseEntity<ResponseMessage<List<LoanInformation>>> filterLoanData(@RequestParam Map<String, String> filterBy) {
        try {
            fileConverterService.validateFilterKeys(filterBy);
            List<LoanInformation> filteredData = fileConverterService.filterLoan(filterBy);

            return ResponseEntity.ok(new ResponseMessage<>(HttpStatus.OK.value(), "Success", filteredData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), "Error in fetching details", null));
        }
    }

    @GetMapping("/filter/additionalLoanInformation")
    public ResponseEntity<ResponseMessage<List<AdditionalLoanInformation>>> filterAdditionalInfoData(@RequestParam Map<String, String> filterby) {
        try {
            List<AdditionalLoanInformation> filteredData = fileConverterService.filterAdditionalInfo(filterby);

            return ResponseEntity.ok(new ResponseMessage<>(HttpStatus.OK.value(), "Success", filteredData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), "Error in fetching details", null));        }
    }


    @GetMapping("/all-loans")
    public List<CombinedLoanDetails> getAllLoans() {
        return fileConverterService.getAllLoans();
    }

}
