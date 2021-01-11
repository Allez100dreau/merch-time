package unice.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.InstanceMT;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileParser {

    int capacity;
    int nbProducts;
    String fileName;
    List<Integer> weights;
    private static final Logger log = LoggerFactory.getLogger(FileParser.class);

    public void parseFile(String file) {

        try {
            InputStream is = FileParser.class.getClassLoader().getResourceAsStream(file);
            if (is == null) try {
                throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStreamReader isr = new InputStreamReader(is, UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();

            fileName = file;

            capacity = Integer.parseInt(line);
            line = br.readLine();
            nbProducts = Integer.parseInt(line);

            weights = new ArrayList<>();
            String s[] = br.readLine().split(" ");
            for (String str : s)
                weights.add(Integer.parseInt(str));

            br.close();

            log.debug("Parsed file : {}", file);
            log.debug("Capacity : {}, nbProducts : {}, Price : {}", capacity, nbProducts, weights);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InstanceMT parsedFileToInstance(String fileName) {
        parseFile(fileName);
        return new InstanceMT(capacity, nbProducts, weights);
    }

    public static Iterator<FileParser> parseAllFiles() {
        ArrayList<FileParser> parsers = new ArrayList<>();
        InputStream is1 = FileParser.class.getClassLoader().getResourceAsStream("input");
        InputStream is2 = FileParser.class.getClassLoader().getResourceAsStream("codingBattleInput");

        if (is1 == null || is2 == null) try {
            throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isr1 = new InputStreamReader(is1, UTF_8);
        InputStreamReader isr2 = new InputStreamReader(is2, UTF_8);
        BufferedReader br1 = new BufferedReader(isr1);
        BufferedReader br2 = new BufferedReader(isr2);

        ArrayList<String> files = new ArrayList<>();
        try {
            while (br1.ready()) {
                files.add(br1.readLine());
            }
            for (String fileName : files) {
                FileParser parser = new FileParser();
                parser.parseFile("input/" +fileName);
                parsers.add(parser);
            }
            files = new ArrayList<>();
            while (br2.ready()) {
                files.add(br2.readLine());
            }
            for (String fileName : files) {
                FileParser parser = new FileParser();
                parser.parseFile("codingBattleInput/" +fileName);
                parsers.add(parser);

            }
        } catch (IOException e) {
        }
        return parsers.iterator();
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

    public String getFileName() {
        return fileName;
    }

}
