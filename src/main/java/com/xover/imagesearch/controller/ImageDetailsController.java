package com.xover.imagesearch.controller;

import com.xover.imagesearch.document.ImageDetailsDocument;
import com.xover.imagesearch.service.ImageDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ImageDetailsController.ENDPOINT)
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Image Details")
public class ImageDetailsController {

    public static final String ENDPOINT = "/api/v1/images";

    private static final String API_PARAM_DESCRIPTION = "DESCRIPTION";
    private static final String API_PARAM_FILE_TYPE = "FILE TYPE";
    private static final String API_PARAM_FILE_SIZE = "FILE SIZE";

    public static final String REQUEST_PARAM_DESCRIPTION = "description";
    public static final String REQUEST_PARAM_FILE_TYPE = "fileType";
    public static final String REQUEST_PARAM_FILE_SIZE = "fileSize";

    @Autowired
    private ImageDetailsService imageDetailsService;

    @ApiOperation("Get image details")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ImageDetailsDocument>> search(
            @ApiParam(name = API_PARAM_DESCRIPTION, required = false) @RequestParam(value = REQUEST_PARAM_DESCRIPTION) Optional<String> description,
            @ApiParam(name = API_PARAM_FILE_TYPE, required = false) @RequestParam(value = REQUEST_PARAM_FILE_TYPE) Optional<String> fileType,
            @ApiParam(name = API_PARAM_FILE_SIZE, required = false)@RequestParam(value = REQUEST_PARAM_FILE_SIZE) Optional<Long> fileSize,
            @PageableDefault(size = 20)
            Pageable pageable) {
        return new ResponseEntity<List<ImageDetailsDocument>>(imageDetailsService.search(description, fileType, fileSize, pageable), HttpStatus.OK);
    }
}
