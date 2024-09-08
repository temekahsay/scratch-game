package temesgen.girmay.feature_matrix_game.data;

import java.util.Map;

public record CellDistribution(
        int column,
        int row,
        Map<String, Integer> symbols
) {
}
