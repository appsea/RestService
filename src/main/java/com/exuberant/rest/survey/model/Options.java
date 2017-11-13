package com.exuberant.rest.survey.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rakesh on 22-Sep-2017.
 */
public class Options {
    public static final int OPTION_COUNT = 4;
    private List<Option> options = new ArrayList<>();

    public void add(Option option) {
        options.add(option);
    }

    public boolean showRadioButton() {
        return options.stream().filter(option -> option.isCorrect()).count() == 1;
    }

    public boolean areValid() {
        return options.size() == OPTION_COUNT && options.stream().filter(option -> option.isCorrect()).count() > 0;
    }

    public List<Option> giveCorrectAnswers() {
        return options.stream().filter(option -> option.isCorrect()).collect(Collectors.toList());
    }

    public int size() {
        return options.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Option option : options) {
            stringBuilder.append(option).append("\n");
        }
        return stringBuilder.toString();
    }

    public void addAnswer(List<String> tags) {
        Collections.sort(options, Comparator.comparing(Option::getTag));
        for (Option option : new HashSet<>(options)) {
            if (tags.contains(option.getTag())) {
                option.setCorrect(true);
            }
        }
    }

    public boolean isCorrect(String[] answers) {
        return this.giveCorrectAnswers().stream().map(answer -> answer.getTag()).collect(Collectors.toList()).containsAll(Arrays.asList(answers));
    }

    public List<Option> getOptions() {
        return options;
    }
}
