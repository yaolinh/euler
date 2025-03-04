package nampnguyen.app.practices.projecteuler.controller.problem21;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.common.integration.web.response.ErrorCode;
import nampnguyen.app.practices.projecteuler.common.integration.web.response.HttpResponse;
import nampnguyen.app.practices.projecteuler.common.integration.web.response.problem21.Problem21Response;
import nampnguyen.app.practices.projecteuler.model.Result;
import nampnguyen.app.practices.projecteuler.model.problem21.Problem21Result;
import nampnguyen.app.practices.projecteuler.programlogic.problem21.Problem21;

@RestController
@RequestMapping("/problem21")
@Slf4j
public class Problem21Controller {

    @Autowired
    private Problem21<Problem21Result> problem21;
    @Autowired
    @Qualifier("GeneralObjectMapper")
    private ObjectMapper objectMapper;

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam(name = "imgId") String imgId) {
        // Load image from resources folder (or use a dynamic path)
        byte[] imageBytes = problem21.retrieveImage(imgId);

        // Set response headers for JPEG
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/jpeg");

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/calculate")
    public ResponseEntity<Problem21Response> calculate(@RequestParam(name = "upperBound") BigInteger upperBound) {
        Result<Problem21Result> r = (Result<Problem21Result>)problem21.solve(upperBound);
        Problem21Response response = new Problem21Response(HttpResponse.builder().source("Problem21").errorCode(ErrorCode.SUCCESS).status(HttpStatus.OK).timestamp(LocalDateTime.now()).build(), r, problem21.retrieveImage(r.getResult().getImgId()));

        try {
            log.debug("Response: {}", objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            log.debug("Error while debug at controller, /calculate: {}", ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.ok(response);
    }
}
