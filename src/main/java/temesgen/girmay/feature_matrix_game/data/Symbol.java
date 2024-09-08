package temesgen.girmay.feature_matrix_game.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Symbol(
        @JsonProperty("reward_multiplier")
        BigDecimal rewardMultiplier,
        BigDecimal extra,
        SymbolType type,
        ImpactType impact
) {
}
