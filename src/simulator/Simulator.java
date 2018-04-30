package simulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.json.*;

public class Simulator {

    private final String config;
    private final int seed;
    private final int load;

    public Simulator(String config, int seed, int load) {
        this.config = config;
        this.seed = seed;
        this.load = load;
    }

    public void Exec() throws Exception {
        try {
            String configuration = new String(Files.readAllBytes(Paths.get(this.config)));
            JSONObject configurationObject = new JSONObject(configuration);

            try {
                boolean error = configurationObject.getBoolean("error");

                if (error) {
                    throw new Exception("You told me to do so...");
                }
            } catch (JSONException e) {
                // Ignore exception
            }

            // If timeout is not set, chooses it randomly (in seconds)
            long duration = ThreadLocalRandom.current().nextInt(1, 10);

            try {
                duration = configurationObject.getInt("duration");
            } catch (JSONException e) {
                // Ignore exception
            }

            boolean stress = false;
            double cpuLoad = 0.0;
            double memoryLoad = 0.0;

            try {
                stress = configurationObject.getBoolean("stress");
                cpuLoad = configurationObject.getDouble("cpuLoad");
                memoryLoad = configurationObject.getDouble("memoryLoad");
            } catch (JSONException e) {
                // Ignore exception
            }

            if (stress) {

                if ((cpuLoad > 0.0 && cpuLoad <= 1.0) && (memoryLoad > 0.0 && memoryLoad <= 1.0)) {
                    // Memory stress 
                    // It depends on JVM memory allocation. On tests I could
                    // only allocate 127MB of memory
                    long allocationMemory = (long) (MemoryUtils.getMaxMemory() * memoryLoad);

                    if (allocationMemory > MemoryUtils.getFreeMemory()) {
                        allocationMemory = MemoryUtils.getFreeMemory();
                    }

                    byte[] stressBuffer = new byte[(int) allocationMemory];
                    Arrays.fill(stressBuffer, (byte) 127);

                    // CPU stress
                    long startTime = System.currentTimeMillis();

                    // Loop for the given duration
                    while (System.currentTimeMillis() - startTime < (duration * 1000)) {
                        // Every 100ms, sleep for the percentage of unladen time
                        if (System.currentTimeMillis() % 100 == 0) {
                            Thread.sleep((long) Math.floor((1 - cpuLoad) * 100));
                        }
                    }
                } else {
                    Thread.sleep((long) (duration * 1000)); // Convert to milliseconds
                }
            }

            JSONObject outputObject = new JSONObject();
            outputObject.put("Load", load);
            outputObject.put("Seed", seed);
            outputObject.put("BBR", ThreadLocalRandom.current().nextDouble(load, load + 100.0));
            outputObject.put("Called Blocked by Cos", ThreadLocalRandom.current().nextDouble(load, load + 100.0));
            outputObject.put("BP-0", ThreadLocalRandom.current().nextDouble(load, load + 100.0));
            outputObject.put("BP-1", ThreadLocalRandom.current().nextDouble(load, load + 100.0));
            outputObject.put("BP-2", ThreadLocalRandom.current().nextDouble(load, load + 100.0));
            outputObject.put("LPS", ThreadLocalRandom.current().nextInt(load * 100, load * 200));

            System.out.println(outputObject.toString());

        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
