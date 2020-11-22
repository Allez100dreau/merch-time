package unice.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.instance.InstanceMT;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;


public class FileParser {

   int capacity;
   int nbProducts;
   List<Integer> weights;
   private static final Logger log = LoggerFactory.getLogger(FileParser.class);

    public void parseFile(String file) {
        BufferedReader br;
        try {

            br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("input/"+file), "UTF-8"));

            String line = br.readLine();
            capacity = Integer.parseInt(line);
            log.info("Capacity : {}" ,capacity);
            line = br.readLine();
            nbProducts = Integer.parseInt(line);
            log.info("nbProducts : {}" ,nbProducts);

            weights = new ArrayList<>();
            String s[]= br.readLine().split(" ");
            for(String str : s)
                weights.add(Integer.parseInt(str));

            log.info("poids  : {}",weights);
            br.close();

            log.info("Parsed file : {}", file);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public InstanceMT parsedFileToInstance(String fileName)
    {
        parseFile(fileName);
        return new InstanceMT(capacity,nbProducts,weights);



    }
    public void parseAllFiles() {

        String projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString();
        Path resourcesPath = Paths.get(projectDirAbsolutePath, "/src/main/resources");
        getClass().getClassLoader().getResourceAsStream(resourcesPath.toString());


        InputStream is = FileParser.class.getClassLoader().getResourceAsStream("input");
        if (is == null) try {
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(is, UTF_8);
        BufferedReader br = new BufferedReader(isr);

        ArrayList<String> files = new ArrayList<>();
        try {
            while (br.ready()) {
                files.add(br.readLine());
            }
            for (String fileName : files) {
                parseFile(fileName);
            }
        } catch (IOException e) {

        }


    }
    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfProducts() {
        return nbProducts;
    }

    public List<Integer> getWeights() {
        return new ArrayList<>(weights);
    }
}
