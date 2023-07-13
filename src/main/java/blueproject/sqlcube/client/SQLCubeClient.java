package blueproject.sqlcube.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLCubeClient implements ClientModInitializer {

    private static final String MOD_ID = "SQLCube";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {
        LOGGER.info("[CLIENT] Initialized!");
    }

}
