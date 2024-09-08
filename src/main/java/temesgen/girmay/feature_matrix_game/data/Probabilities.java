package temesgen.girmay.feature_matrix_game.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Probabilities(
        @JsonProperty("standard_symbols")
        List<CellDistribution> standardSymbols,
        @JsonProperty("bonus_symbols")
        CellDistribution bonusSymbols
) {
}
