package unice.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.solver.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigParser {

    private static final Logger log = LoggerFactory.getLogger(ConfigParser.class);

    /**
     * loads the application.properties file and reads the solver, then return the ISolver accordingly
     * @return ISolver
     */
    public ISolver getSolver() throws Exception {
        String propertyFile = "application.properties";
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);

        prop.load(inputStream);

        if (inputStream == null) {
            log.error("Property file '{}' not found in the classpath",propertyFile);
            throw new FileNotFoundException("Property file '" + propertyFile + "' not found in the classpath");
        }

        switch (prop.getProperty("solveur")) {
            case "D":
                return new SolverD();

            case "E":
                return new SolverE();

            case "H":
                return new SolverH();

            default:
                log.error("Le solveur {} n'existe pas", prop.getProperty("solveur"));
                throw new Exception("Le solveur choisit n'existe pas");
        }
    }
}
