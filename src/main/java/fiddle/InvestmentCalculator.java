package fiddle;

public class InvestmentCalculator {
  public static double calculateTime(
      double currentBalance,
      double finalBalance,
      double monthlyContribution,
      double annualGrowthRate) {
    int n = 12; // Compounding monthly
    double r = annualGrowthRate / 100; // Convert percentage to decimal
    double t = 0; // Time in years

    // Loop until the current balance reaches or exceeds the final balance
    while (currentBalance < finalBalance) {
      // Calculate the interest for this month and update the balance
      currentBalance += currentBalance * (r / n);
      // Add the monthly contribution
      currentBalance += monthlyContribution;
      // Increment time by one month
      t += 1.0 / 12;
    }

    return t; // Return time in years
  }

  public static double calculateFinalBalance(
      double currentBalance,
      double monthlyContribution,
      double annualGrowthRate,
      double timeInYears) {
    int n = 12; // Compounding monthly
    double r = annualGrowthRate / 100; // Convert percentage to decimal

    // Calculate the future value (final balance)
    return currentBalance * Math.pow((1 + r / n), n * timeInYears)
        + (monthlyContribution * (Math.pow((1 + r / n), n * timeInYears) - 1) / (r / n));
  }

  public static void main(String[] args) {
    double time = calculateTime(280, 980, 23, 7);
    System.out.println(time);
    System.out.println(calculateFinalBalance(280, 23, 7, time));
    System.out.println(calculateFinalBalance(280, 50, 7, time));
  }
}
