package temesgen.girmay.feature_matrix_game.domain.use_case;

import temesgen.girmay.feature_matrix_game.configuration.Config;
import temesgen.girmay.feature_matrix_game.data.CellDistribution;
import temesgen.girmay.feature_matrix_game.domain.model.Distribution;
import temesgen.girmay.feature_matrix_game.domain.model.GameBoard;

import java.math.BigDecimal;
import java.util.Random;

import static java.math.BigDecimal.ZERO;

public class ScratchGame {
    private final Config config;

    public ScratchGame(Config config) {
        this.config = config;
    }

    public GameBoard scratch(BigDecimal bet) {
        return new GameBoardCalculator(config).calculate(distribute(), bet);
    }

    private GameBoard distribute() {
        boolean isBonus = config.probabilities().bonusSymbols() != null;
        String[][] board = new String[config.rows()][config.columns()];
        String bonusSymbol = null;
        for (CellDistribution cellDistribution : config.probabilities().standardSymbols()) {
            if (isBonus && new Random().nextBoolean()) {
                bonusSymbol = new Distribution.Builder(config.probabilities().bonusSymbols()).build().next();
                board[cellDistribution.row()][cellDistribution.column()] = bonusSymbol;
                isBonus = false;
            } else {
                board[cellDistribution.row()][cellDistribution.column()] = new Distribution.Builder(cellDistribution).build().next();
            }
        }
        return new GameBoard(board, ZERO, null, bonusSymbol);
    }
}
