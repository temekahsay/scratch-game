package temesgen.girmay.feature_matrix_game.data;

import java.util.*;
import java.util.stream.Collectors;

public class SameSymbolMatcher extends CombinationMatcher {

    private final Map<Integer, CombinationDescriptor> combinations = new HashMap<>();

    public SameSymbolMatcher(Map<String, WinCombination> winCombinations) {
        winCombinations.entrySet().stream()
                .filter(e -> e.getValue().when() == CombinationMatch.SAME_SYMBOLS)
                .forEach(e -> combinations.put(e.getValue().count(), new CombinationDescriptor(e.getKey(), e.getValue())));
    }

    public List<MatchingResult> match(String[][] board) {
        Map<String, Long> symbolCounts = Arrays.stream(board)
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(symbol -> symbol, Collectors.counting()));

        return symbolCounts.entrySet().stream()
                .map(e -> {
                    CombinationDescriptor descriptor = combinations.get(e.getValue().intValue());
                    if (descriptor != null) {
                        return new MatchingResult(
                                e.getKey(),
                                descriptor.name(),
                                descriptor.winCombination().group(),
                                descriptor.winCombination().rewardMultiplier()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)  // Remove null values
                .collect(Collectors.toList());
    }
}
