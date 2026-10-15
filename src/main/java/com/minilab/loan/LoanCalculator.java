package com.minilab.loan;

public class LoanCalculator {
    public double computeMonthlyPayment(double principal, double annualRatePercent, int months) {
        if (!Double.isFinite(principal) || principal <= 0) {
            throw new IllegalArgumentException("Principal must be greater than 0");
        }
        if (!Double.isFinite(annualRatePercent) || annualRatePercent < 0 || annualRatePercent > 100) {
            throw new IllegalArgumentException("Annual rate must be between 0 and 100");
        }
        if (months <= 0) {
            throw new IllegalArgumentException("Term must be at least 1 month");
        }

        double payment;
        if (annualRatePercent == 0) {
            payment = principal / months;
        } else {
            double monthlyRate = annualRatePercent / 12 / 100;
            payment = principal * monthlyRate / (1 - Math.pow(1 + monthlyRate, -months));
        }
        return Math.round(payment * 100) / 100.0;
    }
}
