package com.exuberant.rest.survey.local;

import com.exuberant.rest.survey.model.JsonOption;
import com.exuberant.rest.survey.model.JsonQuestion;
import com.exuberant.rest.survey.model.JsonQuestions;
import com.exuberant.rest.survey.model.Prashna;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WordFileWriter {

    public static void main(String[] args) throws IOException, InvalidFormatException, URISyntaxException {
        writeIt(null);
    }

    public void write(JsonQuestions jsonQuestions, Path wordPath) throws Exception {
        XWPFDocument document = new XWPFDocument();
        int number = 0;
        for (JsonQuestion jsonQuestion : jsonQuestions.getQuestions()) {
            this.addText(document, "Question " + ++number);
            this.addText(document, jsonQuestion.getDescription());
            this.addPrashna(document, jsonQuestion.getPrashna());
            List<JsonOption> options = jsonQuestion.getOptions();
            for (JsonOption option : options) {
                this.addOption(document, option);
            }
            this.addText(document, "Explanation: " + jsonQuestion.getExplanation());
            this.addText(document, "------------------------------------------");
        }
        FileOutputStream out = new FileOutputStream(wordPath.toString());
        document.write(out);
        out.close();
        document.close();
    }

    private void addOption(XWPFDocument document, JsonOption option) throws Exception {
        this.addText(document, option.getDescription());
        this.addImage(document, option.getImage());
    }

    private void addPrashna(XWPFDocument document, Prashna prashna) throws Exception {
        if (prashna != null) {
            this.addText(document, prashna.getText());
            this.addImage(document, prashna.getImage());
        }
    }

    public void addText(XWPFDocument document, String text) {
        this.addText(document, text, ParagraphAlignment.LEFT);
    }

    public void addText(XWPFDocument document, String text, ParagraphAlignment paragraphAlignment) {
        if (text != null) {
            XWPFParagraph para3 = document.createParagraph();
            para3.setAlignment(paragraphAlignment);
            XWPFRun para3Run = para3.createRun();
            para3Run.setText(text);
        }
    }

    public void addImage(XWPFDocument document, String imageName) throws Exception {
        if (imageName != null) {
            if(ClassLoader.getSystemResource("images/dvsa/renamed/" + imageName) != null){
                XWPFParagraph image = document.createParagraph();
                image.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun imageRun = image.createRun();
                imageRun.setTextPosition(20);

                Path imagePath = Paths.get(ClassLoader.getSystemResource("images/dvsa/renamed/" + imageName).toURI());
                imageRun.addPicture(Files.newInputStream(imagePath),
                        XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
                        Units.toEMU(300), Units.toEMU(150));
                this.addText(document, imageName, ParagraphAlignment.CENTER);
            }else{
                System.err.println("Couldnt find the image " + imageName);
            }

        }
    }

    public static void writeIt(JsonQuestions jsonQuestions) throws IOException, InvalidFormatException, URISyntaxException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Build Your REST API with Spring");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        Path imagePath = Paths.get(ClassLoader.getSystemResource("images/dvsa/renamed/cttk2000.jpg").toURI());
        imageRun.addPicture(Files.newInputStream(imagePath),
                XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
                Units.toEMU(300), Units.toEMU(150));

        String output = "c:/data/test.docx";
        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
    }
}
