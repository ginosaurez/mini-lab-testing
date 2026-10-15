package com.minilab.loan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * STARTER FILE — complete the TODOs below.
 * See README.md for the full task list.
 */
class LoanCalculatorTest {

    private LoanCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new LoanCalculator();
    }

    // ---------------------------------------------------------------
    // Part A — Happy path (example given)
    // ---------------------------------------------------------------

    @Test
    void computeMonthlyPayment_standardLoan_returnsAmortizedPayment() {
        // Arrange
        double principal = 100_000;   // ₱100,000
        double annualRate = 12;       // 12% per year
        int months = 12;

        // Act
        double payment = calculator.computeMonthlyPayment(principal, annualRate, months);

        // Assert
        assertEquals(8_884.88, payment, 0.001);
    }

    // TODO A1: Test a 0% interest loan (₱120,000 over 12 months).

    // TODO A2: Test a one-month loan (the boundary for months).

    // ---------------------------------------------------------------
    // Part B — Exception tests (example given)
    // ---------------------------------------------------------------

    @Test
    void computeMonthlyPayment_zeroPrincipal_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.computeMonthlyPayment(0, 12, 12));

        assertEquals("Principal must be greater than 0", ex.getMessage());
    }

    // TODO B1: Negative principal throws IllegalArgumentException.

    // TODO B2: Negative annual rate throws IllegalArgumentException
    //          and check the message.

    // TODO B3: Annual rate above 100 throws IllegalArgumentException.

    // TODO B4: Zero months throws IllegalArgumentException
    //          and check the message.

    // TODO B5: Double.NaN as principal throws IllegalArgumentException.

    // ---------------------------------------------------------------
    // Part C — Boundary values
    // ---------------------------------------------------------------

    // TODO C1: An annual rate of exactly 100 does NOT throw
    //          (hint: assertDoesNotThrow).

    // TODO C2: An annual rate of exactly 0 does NOT throw.
}
