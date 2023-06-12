package org.snturk.petition.filegeneration.pdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.FileType;
import org.snturk.petition.filegeneration.FileGenerationService;
import org.snturk.petition.filegeneration.html.HtmlFileGenerationService;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PdfFileGenerationService implements FileGenerationService {

    private final HtmlFileGenerationService htmlFileGenerationService = new HtmlFileGenerationService();

    @Override
    public FileType getFileType() {
        return FileType.PDF;
    }

    @Override
    public void generateFile(PetitionModel model, Path path) {
        // First, we need to generate an html file from the given model
        // Then, we need to convert the html file to pdf
        htmlFileGenerationService.generateFile(model, path);
        OutputStream outputStream = null;
        try {
            File htmlFile = path.toFile();
            Document document = Jsoup.parse(htmlFile, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().prettyPrint(false);

            outputStream = Files.newOutputStream(path.toFile().toPath());
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
