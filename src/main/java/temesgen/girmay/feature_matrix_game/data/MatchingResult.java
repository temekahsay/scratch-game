package temesgen.girmay.feature_matrix_game.data;

import java.math.BigDecimal;

public record MatchingResult(
        String symbol,
        String combination,
        CombinationGroup group,
        BigDecimal rewardMultiplier
) {
}
