package blueproject.sqlcube;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLCube implements ModInitializer {
    private static final String MOD_ID = "SQLCube";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("[GLOBAL] Initialized!");
    }
}
