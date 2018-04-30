package simulator;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

    public static void main(String[] args) throws Exception {
        ArgumentParser parser = ArgumentParsers.newFor("Simulator").build()
                .defaultHelp(true)
                .description("Simulator of ONS simulator.");

        parser.addArgument("-c", "--config")
                .required(true)
                .help("Specify configuration file");
        parser.addArgument("-s", "--seed")
                .required(true)
                .type(Integer.class)
                .help("Specify seed");
        parser.addArgument("-l", "--load")
                .required(true)
                .type(Integer.class)
                .help("Specify load");

        Namespace ns = null;
        
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        String config = ns.get("config");
        int seed = ns.get("seed");
        int load = ns.get("load");
        
        Simulator simulator = new Simulator(config, seed, load);
        simulator.Exec();
    }

}
