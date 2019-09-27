package com.exuberant.rest.tms;

import com.exuberant.rest.survey.model.tms.Batch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {

    List<Batch> batches = new ArrayList<>();

    public Batch saveBatch(Batch batch) {
        batches.add(batch);
        System.err.println("batches: " + batches);
        return batch;
    }
}
