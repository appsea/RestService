package com.exuberant.rest.survey.controller;

import com.exuberant.rest.survey.model.tms.Batch;
import com.exuberant.rest.tms.BatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/batch")
public class BatchController {

    public static final Log log = LogFactory.getLog(BatchController.class);

    @Autowired
    private BatchService batchService;

    @RequestMapping(path = "/create", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    public Batch save(@RequestBody Batch batch) throws Exception {
        log.info("Saving New Batch");
        return batchService.saveBatch(batch);
    }
}