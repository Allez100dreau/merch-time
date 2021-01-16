package unice.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.solver.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigParser {

    private static final Logger log = LoggerFactory.getLogger(ConfigParser.class);
    private static String propertyFile = "application.properties";
    private static Properties prop = new Properties();
    private static InputStream inputStream = ConfigParser.class.getClassLoader().getResourceAsStream(propertyFile);


    /**
     * loads the application.properties file and reads the solver, then return the ISolver accordingly
     * @return ISolver
     */
    public static ISolver[] getSolver() throws Exception {
        ISolver[] solvers = new ISolver[2];
        prop.load(inputStream);
        if (inputStream == null) {
            log.error("Property file '{}' not found in the classpath",propertyFile);
            throw new FileNotFoundException("Property file '" + propertyFile + "' not found in the classpath");
        }

        switch (prop.getProperty("Solveur1")) {
            case "D":
                solvers[0] = new SolverD();
                break;

            case "E":
                solvers[0] = new SolverE();
                break;

            case "H":
                solvers[0] = new SolverH();
                break;
            default:
                log.error("Le solveur {} n'existe pas", prop.getProperty("solveur"));
                throw new Exception("Le solveur choisit n'existe pas");
        }

        switch (prop.getProperty("Solveur2")) {
            case "D":
                solvers[1] = new SolverD();
                break;
            case "E":
                solvers[1] = new SolverE();
                break;
            case "H":
                solvers[1] = new SolverH();
                break;
            default:
                log.error("Le solveur {} n'existe pas", prop.getProperty("solveur"));
                throw new Exception("Le solveur choisit n'existe pas");
        }
        return solvers;
    }

    public static String getInputFile()  {

        try {
            if (inputStream == null) {
                log.error("Property file '{}' not found in the classpath",propertyFile);
                return null;
            }
            else
                prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "input/input"+prop.getProperty("Instance")+".txt";
    }

    }
