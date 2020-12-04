package unice.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.solver.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigParser {

    private static final Logger log = LoggerFactory.getLogger(ConfigParser.class);

    public ISolver getSolver() throws Exception {
        String propertyFile = "config.properties";
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);

        prop.load(inputStream);

        if (inputStream == null) {
            log.error("Property file '{}' not found in the classpath",propertyFile);
            throw new FileNotFoundException("Property file '" + propertyFile + "' not found in the classpath");
        }

        switch (prop.getProperty("algo.choisi")) {
            case "D":
                return new SolverD();

            case "E":
                return new SolverE();

            case "H":
                return new SolverH();

            case "Z":
                return new SolverZ();

            default:
                log.error("Algo {} choisi n'éxiste pas", prop.getProperty("algo.choisi"));
                throw new Exception("Algo choisi n'éxiste pas");
        }
    }
}
