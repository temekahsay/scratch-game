package temesgen.girmay.feature_matrix_game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import temesgen.girmay.feature_matrix_game.data.Probabilities;
import temesgen.girmay.feature_matrix_game.data.Symbol;
import temesgen.girmay.feature_matrix_game.data.WinCombination;
import java.util.Map;

public record Config(
        int columns,
        int rows,
        Map<String, Symbol> symbols,
        Probabilities probabilities,
        @JsonProperty("win_combinations")
        Map<String, WinCombination> winCombinations
) {
        public boolean isValid() {
                ConfigValidator validator = new ConfigValidator();
                return validator.validate(this);
        }
}
