package com.exuberant.rest.survey.parser;

import java.util.HashMap;
import java.util.Map;

public class AllocateCategoryFromQuestionNumber {

    Map<MinMax, String> map = new HashMap<>();

    public AllocateCategoryFromQuestionNumber() {
        map.put(new MinMax(1, 154), "Operating Systems");
        map.put(new MinMax(155, 282), "Security");
        map.put(new MinMax(283, 333), "Mobile Devices");
        map.put(new MinMax(334, 511), "Troubleshooting");
        map.put(new MinMax(512, 282), "Mix Questions");
    }

    public String findCategoryForQuestion(int questionNumber) {
        String category = null;

        return category;
    }

    private class MinMax {
        int min;
        int max;

        public MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        boolean isBetween(int number) {
            return number >= min && number <= max;
        }
    }
}
