package temesgen.girmay;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import temesgen.girmay.feature_matrix_game.configuration.Config;
import temesgen.girmay.feature_matrix_game.domain.model.GameBoard;
import temesgen.girmay.feature_matrix_game.domain.use_case.ScratchGame;
import temesgen.girmay.feature_matrix_game.util.ConfigParameters;

import java.io.File;
import java.io.IOException;

public class ScratchGameApp {

    public static void main(String[] args)  throws IOException {
        ConfigParameters configParameters = new ConfigParameters();
        if (configParameters.init(args)) {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                    .build();
            Config config = objectMapper.readValue(new File(configParameters.getConfigPath()), Config.class);

            GameBoard output = new ScratchGame(config).scratch(configParameters.getBettingAmount());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, output);
        }
    }
}
