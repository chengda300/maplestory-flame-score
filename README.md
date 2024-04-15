# What is this?
Flame Score probability analysis for the game MapleStory.

# What is Flame Score?
(Taken from https://strategywiki.org/wiki/MapleStory/Bonus_Stats and https://whackybeanz.com/guides/flames)
An equipment item in the game can obtain Bonus Stats.

## Possible kinds of stats
There are a total of **19 different kinds** of possible stats, which are **picked without replacement at equal probability**. The kind of stats picked by the system is also known as **"line"**.
Among the possible stats, for most playable characters in the game, there exists:
A. Exactly **1 kind** of stat which adds a **huge amount of matching stat**
B. Exactly **3 kinds** of stats which adds **some amount of matching stat**
C. Exactly **1 kind** of stat which adds **a percentage of all possible stats**
D. Exactly **1 kind** of stat which adds **attack** instead

**Most playable characters use only 1 out of 4 possible types of matching stat**.

For types A and B, the quantity of the matching stat increased is **affected by the equipment item's required level**.
Since most of the contribution comes from types A and B, for types C and D, they are converted to an equivalent value **(the equivalent number of matching stat to match the same damage increase of 1% all stats or 1 attack respectively)**. This is not done by this calculator, so the user would need to input their weights manually.
**All other kinds of stats picked by the system that do not fall within the 4 categories listed above would have 0 contribution to the flame score**.

## Tier/Multiplier of line
When a line is picked, there are **4 different possible tiers to determine the multiplier** applied to it. The multipliers have different probabilities, which are **affected by the type of flame used (only 2 kinds, either "crimson" or "rainbow")**, as well as **whether the equipment item has "flame advantage"**. (Flames are used to reset the bonus stats given by this system in the game, and items with "flame advantage" are pre-defined by the game).
**The tier matches the multiplier**, which means that Tier 7 would multiply the value of the line by 7. While the tier can range from 1 to 7, for a specific scenario, it has a smaller range of possible tiers.

The table below shows the probability when a **"crimson" flame is used on an item with flame advantage**.
| Tier/Multiplier | Probability |
|      :---:      |    :---:    |
|        3        |     20%     |
|        4        |     30%     |
|        5        |     36%     |
|        6        |     14%     |

The table below shows the probability when a **"rainbow" flame is used on an item with flame advantage**.
| Tier/Multiplier | Probability |
|      :---:      |    :---:    |
|        4        |     29%     |
|        5        |     45%     |
|        6        |     25%     |
|        7        |     1%      |

## Conclusion
**The sum of the values of the lines picked by the system (after applying the tier/multiplier) is the flame score for the equipment item**.

# Motivation
Since JavaScript does not have enough computing power to compute so many iterations (the ones found currently all uses Monte-Carlo simulations, which require much lesser iterations), this calculator would calculate every single kind of stats and multipliers applied that is possible to be given, and sum up the probability of a specific flame score as a result of a set of lines picked by the system (different sets of lines can give the same flame score).
With this, we can get the exact probabilities of obtaining a specific flame score or better.

This calculator uses Java, which is a compiled language, so it computes much faster than JavaScript.

# How to Run
1. Ensure that Java 11 or later is installed on the computer.
2. Download the JAR file into **your user folder ("C:/Users/{your user folder}")**.
3. Open Command Prompt.
4. Type "java -jar "flame.score.jar" and press Enter.
