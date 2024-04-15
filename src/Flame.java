import static java.lang.Double.parseDouble;
import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

import java.util.Scanner;

public class Flame {
    static long result[];

    // normal items
    static int crimsonProp[] = {0, 20, 30, 36, 14, 0}; // tiers 0 to 5 respectively
    static int rainbowProp[] = {0, 0, 29, 45, 25, 1}; // tiers 0 to 5 respectively

    // items with flame advantage
    static int crimsonPropAdvantage[] = {0, 0, 0, 20, 30, 36, 14, 0}; // tiers 0 to 7 respectively
    static int rainbowPropAdvantage[] = {0, 0, 0, 0, 29, 45, 25, 1}; // tiers 0 to 7 respectively

    static int tierProp[] = crimsonPropAdvantage;
    static int minTier = 1;
    static int maxTier = minTier + 3;
    static int singleStat, doubleStat, maxScore, itemLevel;
    static double percentStat, attack;
    static boolean rainbow = false;
    static boolean advantage = true;
    public static void initialise(int itemLevel, double percentStat, double attack, boolean rainbow, boolean advantage) {
        maxScore = 0;
        Flame.itemLevel = itemLevel;
        Flame.percentStat = percentStat;
        Flame.attack = attack;
        if (itemLevel <= 200) {
            Flame.singleStat = 1 + itemLevel / 20;
            Flame.doubleStat = 1 + itemLevel / 40;
        } else {
            Flame.singleStat = 4 + (itemLevel + 10) / 30;
            Flame.doubleStat = 2 + itemLevel / 50;
        }
        result = new long[(int) (attack + percentStat + singleStat * 2) * 7];
        if (rainbow) {
            tierProp = rainbowPropAdvantage;
            minTier += 1;
            maxTier += 1;
        }
        if (advantage) {
            minTier += 2;
            maxTier += 2;
            calculateFlameScoreAdvantage();
        }
    }
    public static void calculateFlameScoreAdvantage() {
        for (int a = 0; a < 16; a++) {
            for (int b = a + 1; b < 17; b++) {
                for (int c = b + 1; c < 18; c++) {
                    for (int d = c + 1; d < 19; d++) {
                        getScoreFromCombinationAdvantage(a, b, c, d);
                    }
                }
            }
        }
        int starter = 0;
        int average = 0;
        int aboveAverage = 0;
        int minMax = 0;
        int extremeMinMax = 0;
        System.out.println("\n====Detailed Breakdown====");
        for (int a = 0; a <= maxScore; a++) {
            if (a > 0) {
                result[a] += result[a - 1];
            }
            if (a == 0 || result[a] != result[a - 1]) {
                System.out.println("Score " + a + ": " + result[a] / 3876.0 / 1000000.0 + "%");
                if (a > 0 && result[a] <= 80L * 3876 * 1000000) {
                    starter = a;
                }
                if (a > 0 && result[a] <= 95L * 3876 * 1000000) {
                    average = a;
                }
                if (a > 0 && result[a] <= 99L * 3876 * 1000000) {
                    aboveAverage = a;
                }
                if (a > 0 && result[a] <= 99.9 * 3876 * 1000000) {
                    minMax = a;
                }
                if (a > 0 && result[a] <= 99.99 * 3876 * 1000000) {
                    extremeMinMax = a;
                }
            }
        }

        System.out.println("\n====Summarised Breakdowns====\nStarter (80th percentile): " + starter
                + "\nAverage (95th percentile): " + average
                + "\nAbove Average (99th percentile): " + aboveAverage
                + "\nMinmax (99.9th percentile): " + minMax
                + "\nExtreme Minmax (99.99th percentile): " + extremeMinMax
                + "\nMaximum Possible: " + Flame.maxScore);

        System.out.println("\n====Inputs Supplied====\nItem Level: " + Flame.itemLevel
                + "\nWeight of 1% All Stats: " + Flame.percentStat
                + "\nWeight of 1 ATT/Magic ATT: " + Flame.attack
                + "\nFlame Type: " + (rainbow ? "Rainbow" : "Crimson")
                + "\nFlame Advantage Applied: " + (advantage ? "Yes" : "No"));

    }

    public static void getScoreFromCombinationAdvantage(int a, int b, int c, int d) {
        for (int e = minTier; e <= maxTier; e++) {
            for (int f = minTier; f <= maxTier; f++) {
                for (int g = minTier; g <= maxTier; g++) {
                    for (int h = minTier; h <= maxTier; h++) {
                        getScoreFromTierAdvantage(a, e, b, f, c, g, d, h);
                    }
                }
            }
        }
    }

    public static void getScoreFromTierAdvantage(int a, int ta, int b, int tb, int c, int tc, int d, int td) {
        int currScore = 0;
        if (a == 0) {
            currScore += singleStat * ta;
        } else if (a == 4 || a == 5 || a == 6) {
            currScore += doubleStat * ta;
        } else if (a == 10) {
            currScore += attack * ta;
        } else if (a == 17) {
            currScore += percentStat * ta;
        }
        if (b == 0) {
            currScore += singleStat * tb;
        } else if (b == 4 || b == 5 || b == 6) {
            currScore += doubleStat * tb;
        } else if (b == 10) {
            currScore += attack * tb;
        } else if (b == 17) {
            currScore += percentStat * tb;
        }
        if (c == 0) {
            currScore += singleStat * tc;
        } else if (c == 4 || c == 5 || c == 6) {
            currScore += doubleStat * tc;
        } else if (c == 10) {
            currScore += attack * tc;
        } else if (c == 17) {
            currScore += percentStat * tc;
        }
        if (d == 0) {
            currScore += singleStat * td;
        } else if (d == 4 || d == 5 || d == 6) {
            currScore += doubleStat * td;
        } else if (d == 10) {
            currScore += attack * td;
        } else if (d == 17) {
            currScore += percentStat * td;
        }

        if (currScore > maxScore) {
            maxScore = currScore;
        }
        long probability = (long) tierProp[ta] * tierProp[tb] * tierProp[tc] * tierProp[td];
        result[currScore] += probability;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int itemLevel;
        while (true) {
            try {
                while (true) {
                    System.out.print("Item level: ");
                    itemLevel = parseInt(sc.nextLine());
                    if (itemLevel >= 0 && itemLevel <= 250) break;
                    System.out.println("Item level must be between 0 and 250.");
                }
                break;
            } catch (Exception e) {
                System.out.println("The input item level is invalid, please input whole numbers only.");
            }
        }
        double percentStat;
        while (true) {
            try {
                while (true) {
                    System.out.print("Weight of 1% All Stat: ");
                    percentStat = parseDouble(sc.nextLine());
                    if (percentStat >= 0 && percentStat <= 100) break;
                    System.out.println("The weight must be between 0 and 100.");
                }
                break;
            } catch (Exception e) {
                System.out.println("The ratio is invalid, please input numbers only.");
            }
        }
        double attack;
        while (true) {
            try {
                System.out.print("Weight of 1 ATT/Magic ATT: ");
                attack = parseDouble(sc.nextLine());
                if (attack >= 0 && attack <= 100) break;
                System.out.println("The weight must be between 0 and 100.");
            } catch (Exception e) {
                System.out.println("The ratio is invalid, please input numbers only.");
            }
        }
        while (true) {
            System.out.print("Crimson or Rainbow Flame: (crimson/rainbow) ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("crimson") || input.equalsIgnoreCase("rainbow")) {
                if (input.equalsIgnoreCase("rainbow")) {
                    rainbow = true;
                } else {
                    rainbow = false;
                }
                break;
            }
            System.out.println("The input given is invalid. Please enter 'crimson' or 'rainbow' only.");
        }
        while (true) {
            System.out.print("Does the item have flame advantage: (yes/no) ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")) {
                if (input.equalsIgnoreCase("no")) {
                    advantage = false;
                } else {
                    advantage = true;
                }
                break;
            }
            System.out.println("The input given is invalid. Please enter 'yes' or 'no' only.");
        }
        initialise(itemLevel, percentStat, attack, rainbow, advantage);
    }
}
