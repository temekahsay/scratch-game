package temesgen.girmay.feature_matrix_game.configuration;

import temesgen.girmay.feature_matrix_game.data.Probabilities;
import temesgen.girmay.feature_matrix_game.data.Symbol;
import temesgen.girmay.feature_matrix_game.data.WinCombination;

import java.util.Map;

public class ConfigValidator {
    public boolean validate(Config config) {
        return validateDimensions(config.columns(), config.rows())
                && validateSymbols(config.symbols())
                && validateProbabilities(config.probabilities(), config.columns(), config.rows())
                && validateWinCombinations(config.winCombinations());
    }

    private boolean validateDimensions(int columns, int rows) {
        return columns > 0 && rows > 0;
    }

    private boolean validateSymbols(Map<String, Symbol> symbols) {
        return symbols != null && !symbols.isEmpty();
    }

    private boolean validateProbabilities(Probabilities probabilities, int columns, int rows) {
        return probabilities != null
                && probabilities.standardSymbols() != null
                && !probabilities.standardSymbols().isEmpty()
                && probabilities.standardSymbols().size() == columns * rows;
    }

    private boolean validateWinCombinations(Map<String, WinCombination> winCombinations) {
        return winCombinations != null && !winCombinations.isEmpty();
    }
}
