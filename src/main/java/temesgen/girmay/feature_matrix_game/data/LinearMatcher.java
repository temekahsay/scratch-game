package temesgen.girmay.feature_matrix_game.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LinearMatcher extends CombinationMatcher {

    private final List<CombinationDescriptor> combinations;

    public LinearMatcher(Map<String, WinCombination> winCombinations) {
        this.combinations = filterLinearCombinations(winCombinations);
    }

    @Override
    public List<MatchingResult> match(String[][] board) {
        return combinations.stream()
                .flatMap(cd -> matchCombinations(board, cd).stream())
                .collect(Collectors.toList());
    }

    private List<CombinationDescriptor> filterLinearCombinations(Map<String, WinCombination> winCombinations) {
        return winCombinations.entrySet().stream()
                .filter(e -> e.getValue().when() == CombinationMatch.LINEAR_SYMBOLS)
                .map(e -> new CombinationDescriptor(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private List<MatchingResult> matchCombinations(String[][] board, CombinationDescriptor cd) {
        return cd.winCombination().coveredAreas().stream()
                .filter(coveredArea -> matchCoveredArea(board, coveredArea))
                .map(coveredArea -> createMatchingResult(board, coveredArea, cd))
                .collect(Collectors.toList());
    }

    private boolean matchCoveredArea(String[][] board, List<WinCombination.Coordinate> coveredArea) {
        String symbol = getSymbolFromCoordinate(board, coveredArea.get(0));
        return coveredArea.stream().allMatch(coord -> symbol.equals(getSymbolFromCoordinate(board, coord)));
    }

    private String getSymbolFromCoordinate(String[][] board, WinCombination.Coordinate coord) {
        return board[coord.row()][coord.column()];
    }

    private MatchingResult createMatchingResult(String[][] board, List<WinCombination.Coordinate> coveredArea, CombinationDescriptor cd) {
        String matchedSymbol = getSymbolFromCoordinate(board, coveredArea.get(0));
        return new MatchingResult(
                matchedSymbol,
                cd.name(),
                cd.winCombination().group(),
                cd.winCombination().rewardMultiplier()
        );
    }
}
