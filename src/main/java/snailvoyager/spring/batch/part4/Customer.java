package snailvoyager.spring.batch.part4;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import snailvoyager.spring.batch.part5.Orders;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Level level = Level.NORMAL;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private List<Orders> orders;

    private LocalDate updatedDate;

    @Builder
    private Customer(String username, List<Orders> orders) {
        this.username = username;
        this.orders = orders;
    }

    public boolean availableLevelUp() {
        return Level.availableLevelUp(this.getLevel(), this.getTotalAmount());
    }

    private int getTotalAmount() {
        return this.orders.stream().mapToInt(Orders::getAmount).sum();
    }

    public Level levelUp() {
        Level nextLevel = Level.getNextLevel(this.getTotalAmount());
        this.level = nextLevel;
        this.updatedDate = LocalDate.now();

        return nextLevel;
    }

    public enum Level {
        VIP(500_000, null),
        GOLD(500_000, VIP),
        SILVER(300_000, GOLD),
        NORMAL(200_000, SILVER);

        private final int nextAmount;
        private final Level nextLevel;

        Level(int nextAmount, Level nextLevel) {
            this.nextLevel = nextLevel;
            this.nextAmount = nextAmount;
        }

        private static boolean availableLevelUp(Level level, int totalAmount) {
            if (Objects.isNull(level)) {
                return false;
            }
            if (Objects.isNull(level.nextLevel)) {
                return false;
            }
            return totalAmount >= level.nextAmount;
        }

        private static Level getNextLevel(int totalAmount) {
            if (totalAmount >= VIP.nextAmount) {
                return VIP;
            }
            if (totalAmount >= GOLD.nextAmount) {
                return GOLD.nextLevel;
            }
            if (totalAmount >= SILVER.nextAmount) {
                return SILVER.nextLevel;
            }
            if (totalAmount >= NORMAL.nextAmount) {
                return NORMAL.nextLevel;
            }
            return NORMAL;
        }
    }
}
