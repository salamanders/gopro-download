package info.benjaminhill.gopro;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Download all files from the hard-coded directory. Got all info from
 * http://www.techanswerguy.com/2013/02/capturing-live-stream-from-gopro-hero-2.html
 *
 * @author benjamin
 */
public class GoProDL {

  public static final String DCIM_URL = "http://10.5.5.9:8080/DCIM/DCIM/100GOPRO/";
  public static final int BUFFER_SIZE = 1_204 * 1_024 * 5;

  /**
   * @param args the command line arguments
   */
  public static void main(final String... args) {
    // Should be ok for >WinXP and OSX
    final String outputFolder = System.getProperty("user.home") + File.separatorChar + "Pictures" + File.separatorChar + "gopro" + File.separatorChar;

    final File outputFolderF = new File(outputFolder);
    if (!outputFolderF.exists()) {
      if (!(outputFolderF.mkdirs())) {
        throw new RuntimeException("Unable to ensure folder exists:" + outputFolder);
      }
      System.out.println("Created download folder:" + outputFolder);
    }

    try {
      final Document doc = Jsoup.connect(DCIM_URL)
              .timeout(15 * 1_000)
              .get();

      for (final Element link : doc.select("a")) {
        final String linkFileName = link.attr("href");
        if (linkFileName.endsWith("JPG") || linkFileName.endsWith("MP4")) {

          final File destFile = new File(outputFolder + linkFileName);
          if (destFile.exists() && destFile.length() > 1_204) {
            System.out.println("Already have\t" + destFile);
          } else {
            System.out.println("Starting download of\t" + destFile);

            Response resultImageResponse = Jsoup.connect(link.absUrl("href")).execute();
            try (final OutputStream out = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE)) {
              out.write(resultImageResponse.bodyAsBytes());
            }
            System.out.println("Downloaded\t" + destFile);
          }
        }
      }
    } catch (final IOException ex) {
      System.err.println("Error\t" + ex.getMessage() + " on " + DCIM_URL);
    }
  }
}
