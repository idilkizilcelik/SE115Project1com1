import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    public static int[][][] profits;

    public static void loadData() {
        profits = new int[MONTHS][DAYS][COMMS];

        for (int i = 0; i < 12; i++) {
            Scanner reader = null;
            try {
                reader = new Scanner(Paths.get("Data_Files/" + months[i] + ".txt"));
                while (reader.hasNextLine()) {
                    String line = reader.nextLine().trim();
                    String[] parts = line.split(",");
                    String dayStr = parts[0].trim();
                    String commStr = parts[1].trim();
                    String profitStr = parts[2].trim();
                    int day;
                    int profitValue;
                    try {
                        day = Integer.parseInt(dayStr);
                        profitValue = Integer.parseInt(profitStr);
                    } catch (NumberFormatException ex) {
                        continue;
                    }

                    int cIndex = commodityIndex(commStr);
                    profits[i][day - 1][cIndex] = profitValue;

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (reader != null) reader.close();
            }


        }

    }

    public static int commodityIndex(String commodity) {
        if (commodity == null) {
            return -1;
        }
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(commodity)) {
                return i;
            }
        }
        return -1;
    }

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11 || profits == null) {
            return "INVALID_MONTH";
        }
        int bestSum = -99999;
        int bestIndex = 0;
        for (int i = 0; i < commodities.length; i++) {
            int sum = 0;
            for (int j = 0; j < 28; j++) {
                sum += profits[month][j][i];
            }
            if (sum > bestSum) {
                bestSum = sum;
                bestIndex = i;
            }

        }
        return commodities[bestIndex] + bestSum;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month > 11 || day < 1 || day > 28)
            return -99999;
        int totalprofit = 0;
        for (int i = 0; i < commodities.length; i++) {
            totalprofit += profits[month][day - 1][i];
        }
        return totalprofit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int cIndex = commodityIndex(commodity);
        if (commodity == null || from < 1 || from > 28 || to < from)
            return -99999;
        int profitInRange = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = from - 1; j < to - 1; j++) {
                profitInRange = profits[i][j][cIndex];
            }
        }
        return profitInRange;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month > 11) {
            return -1;
        }
        int bestday = 1;
        int bestsum = Integer.MIN_VALUE;
        for (int i = 0; i <= 28; i++) {
            int total = totalProfitOnDay(month, i);
            if (total > bestsum) {
                total = bestsum;
                bestday = i;
            }
        }
        return bestday;

    }

    public static String bestMonthForCommodity(String comm) {
        int cIndex = commodityIndex(comm);
        if (cIndex == -1) {
            return "INVALID_COMMODITY";
        }
        int bestsum = Integer.MIN_VALUE;
        int bestmonth = 0;
        for (int i = 0; i < 12; i++) {
            int sum = 0;
            for (int j = 0; j < 28; j++) {
                sum += profits[i][j][cIndex];
            }
            if (sum > bestsum) {
                bestsum = sum;
                bestmonth = i;
            }
        }
        return months[bestmonth];

    }

    public static int consecutiveLossDays(String comm) {
        int cIndex = commodityIndex(comm);
        if (cIndex < 0) {
            return -1;
        }
        int streak = 0;
        int current = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 28; j++) {
                if (profits[i][j][cIndex] < 0) {
                    current++;
                }
                if (current > streak) {
                    current = streak;
                } else {
                    current = 0;
                }
            }
        }
        return streak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int cIndex = commodityIndex(comm);
        if (cIndex == -1) {
            return -1;
        }

        int count = 0;
        for (int m = 0; m < 12; m++) {
            for (int d = 0; d < 28; d++) {
                if (profits[m][d][cIndex] > threshold)
                    count++;
            }
        }
        return count;
    }
    public static int biggestDailySwing(int month){
        if (month < 0 || month > 11 )
            return -99999;

        int Swing = 0;
        int a = totalProfitOnDay(month, 1);

        for (int day = 2; day <= 28; day++) {
            int curr = totalProfitOnDay(month, day);
            int diff = curr - a;
            if (diff < 0) {
                diff = -diff;
            }
            if (diff > Swing) {
                Swing = diff;
            }
            a = curr;
        }
        return Swing;
    }
    public static String compareTwoCommodities(String c1, String c2){
        int a = commodityIndex(c1);
        int b = commodityIndex(c2);
        if (a == -1 || b == -1 ){
            return "INVALID_COMMODITY";}

        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < 12; i++) {
            for (int d = 0; d < 28; d++) {
                sum1 += profits[i][d][a];
                sum2 += profits[i][d][b];
            }
        }

        if (sum1 == sum2){ return "Equal";}
        if (sum1 > sum2) {return c1 + " is better by " + (sum1 - sum2);}
        return c2 + " is better by " + (sum2 - sum1);
    }
    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month > 11)
        {return "INVALID_MONTH";}

        int bestWeek = 1;
        int bestSum = Integer.MIN_VALUE;

        for (int i = 1; i <= 4; i++) {
            int startDay = (i - 1) * 7 + 1;
            int endDay = i * 7;

            int sum = 0;
            for (int d = startDay; d <= endDay; d++) {
                sum += totalProfitOnDay(month, d);
            }

            if (sum > bestSum) {
                bestSum = sum;
                bestWeek = i;
            }
        }
        return "Week " + bestWeek;
    }
    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}