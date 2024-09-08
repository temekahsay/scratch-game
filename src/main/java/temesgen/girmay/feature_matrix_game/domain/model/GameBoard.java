package temesgen.girmay.feature_matrix_game.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record GameBoard(
        String[][] matrix,
        BigDecimal reward,
        @JsonProperty("win_combinations")
        Map<String, List<String>> winningCombinations,
        @JsonProperty("bonus_symbols")
        String bonusSymbol
) {
}
