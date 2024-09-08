package temesgen.girmay.feature_matrix_game.domain.use_case;

import temesgen.girmay.feature_matrix_game.configuration.Config;
import temesgen.girmay.feature_matrix_game.data.CombinationMatcher;
import temesgen.girmay.feature_matrix_game.data.MatchingResult;
import temesgen.girmay.feature_matrix_game.data.Symbol;
import temesgen.girmay.feature_matrix_game.domain.model.GameBoard;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ZERO;

public class GameBoardCalculator {
    private final Config config;
    private final RewardCalculationStrategy rewardCalculationStrategy;

    Map<String, List<String>> winningCombinations;
    Map<String, BigDecimal> rewardMultipliers;

    public GameBoardCalculator(Config config) {
        this.config = config;
        this.rewardCalculationStrategy = new BasicRewardCalculationStrategy(config);
    }

    public GameBoard calculate(GameBoard gameBoard, BigDecimal bet) {
        List<MatchingResult> matched = findMatchingCombinations(gameBoard);
        BigDecimal reward = ZERO;

        if (!matched.isEmpty()) {
            filterMultipliers(matched);
            reward = calculateReward(bet);
            reward = applyBonusSymbol(gameBoard.bonusSymbol(), reward);
        }

        return buildGameBoard(gameBoard, reward);
    }

    private List<MatchingResult> findMatchingCombinations(GameBoard gameBoard) {
        return new CombinationMatcher(config.winCombinations()).match(gameBoard.matrix());
    }

    private void filterMultipliers(List<MatchingResult> matched) {
        winningCombinations = new HashMap<>();
        rewardMultipliers = new HashMap<>();
        Set<String> covered = new HashSet<>();
        for (MatchingResult matchingResult : matched) {
            //only one combination per group per symbol
            if (covered.add(matchingResult.symbol() + ":" + matchingResult.group())) {
                winningCombinations.computeIfAbsent(matchingResult.symbol(), k -> new ArrayList<>())
                        .add(matchingResult.combination());

                rewardMultipliers.compute(matchingResult.symbol(),
                        (k, v) -> v == null ? matchingResult.rewardMultiplier() : v.multiply(matchingResult.rewardMultiplier()));
            }
        }
    }

    private BigDecimal calculateReward(BigDecimal bet) {
        return rewardCalculationStrategy.calculateReward(rewardMultipliers, bet);
    }

    private BigDecimal applyBonusSymbol(String bonusSymbol, BigDecimal reward) {
        if (bonusSymbol != null) {
            Symbol bonus = config.symbols().get(bonusSymbol);
            switch (bonus.impact()) {
                case MULTIPLY_REWARD:
                    return reward.multiply(bonus.rewardMultiplier());
                case EXTRA_BONUS:
                    return reward.add(bonus.extra());
                case MISS:
                    // Nothing happens here
            }
        }
        return reward;
    }

    private GameBoard buildGameBoard(GameBoard gameBoard, BigDecimal reward) {
        return new GameBoard(
                gameBoard.matrix(),
                reward,
                winningCombinations,
                reward.equals(ZERO) ? null : gameBoard.bonusSymbol()
        );
    }
}
