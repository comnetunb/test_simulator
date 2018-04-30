package simulator;

public class MemoryUtils {

    //! \return free JVM memory in bytes
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    //! \return total JVM memory in bytes
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }
    
    //! \return max JVM memory in bytes
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }
}
