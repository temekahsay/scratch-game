package temesgen.girmay.feature_matrix_game.domain.model;

import temesgen.girmay.feature_matrix_game.data.CellDistribution;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class Distribution {
    private final NavigableMap<Double, String> map = new TreeMap<>();
    private double total = 0;

    private Distribution(Builder builder) {
        builder.cellDistribution.symbols().forEach((symbol, weight) -> add(weight, symbol));
    }

    private void add(double weight, String symbol) {
        if (weight > 0) {
            total += weight;
            map.put(total, symbol);
        }
    }

    public String next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public static class Builder {
        private final CellDistribution cellDistribution;

        public Builder(CellDistribution cellDistribution) {
            this.cellDistribution = cellDistribution;
        }

        public Distribution build() {
            return new Distribution(this);
        }
    }
}
