# Mini Lab: Unit Testing Exceptions with JUnit 5

**Topic:** Unit testing · Runtime exceptions · Boundary values
**Estimated time:** 45–60 minutes
**Tools:** Java 17+, Maven, JUnit 5 (any IDE: IntelliJ IDEA, VS Code, Eclipse)

---

## Learning objectives

By the end of this lab you should be able to:

1. Write unit tests using the **Arrange–Act–Assert** pattern.
2. Verify that a method throws the correct **runtime exception** using `assertThrows`.
3. Check the **exception message**, not just the exception type.
4. Identify and test **boundary values** (the edges of valid input).
5. *(Stretch)* Reduce repetition with **parameterized tests**.

---

## Background

A **runtime exception** (a subclass of `RuntimeException`) is an *unchecked* exception: the compiler does not force callers to catch it. Methods commonly throw `IllegalArgumentException` to reject bad input. Because nothing forces callers to handle it, **unit tests are how we prove the method rejects bad input correctly.**

### The class under test

`src/main/java/com/minilab/loan/LoanCalculator.java`

```java
public double computeMonthlyPayment(double principal, double annualRatePercent, int months)
```

It returns the fixed monthly payment for a loan (in ₱), rounded to 2 decimal places:

```
payment = P × r / (1 − (1 + r)^(−n))      where r = annualRatePercent / 12 / 100
payment = P / n                            when annualRatePercent is 0
```

| Parameter           | Valid range                 | If invalid, throws                                                   |
|---------------------|-----------------------------|----------------------------------------------------------------------|
| `principal`         | `> 0`, finite               | `IllegalArgumentException("Principal must be greater than 0")`       |
| `annualRatePercent` | `0` to `100` (inclusive), finite | `IllegalArgumentException("Annual rate must be between 0 and 100")` |
| `months`            | `≥ 1`                       | `IllegalArgumentException("Term must be at least 1 month")`          |

> **Do not modify `LoanCalculator.java`.** Your job is to test it, not change it.

### Reference values

| Principal  | Annual rate | Months | Monthly payment |
|-----------:|------------:|-------:|----------------:|
| ₱100,000   | 12%         | 12     | ₱8,884.88       |
| ₱120,000   | 0%          | 12     | ₱10,000.00      |
| ₱50,000    | 12%         | 1      | ₱50,500.00      |
| ₱500,000   | 6%          | 60     | ₱9,666.40       |

---

## Quick JUnit 5 reference

```java
// Expect a value (use a delta when comparing doubles)
assertEquals(expected, actual, 0.001);

// Expect an exception, and capture it to inspect the message
IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> calculator.computeMonthlyPayment(0, 12, 12));
assertEquals("Principal must be greater than 0", ex.getMessage());

// Expect NO exception
assertDoesNotThrow(() -> calculator.computeMonthlyPayment(100_000, 100, 12));
```

---

## Tasks

Open `src/test/java/com/minilab/loan/LoanCalculatorTest.java`. One example is already done in Part A and Part B. Replace each `TODO` with a `@Test` method.

Name tests as `methodName_condition_expectedResult`, e.g. `computeMonthlyPayment_zeroMonths_throwsIllegalArgumentException`.

### Part A: Happy path
- **A1.** 0% interest: ₱120,000 over 12 months → ₱10,000.00
- **A2.** One-month loan: ₱50,000 at 12% for 1 month → ₱50,500.00

### Part B: Exception tests
- **B1.** Negative principal throws `IllegalArgumentException`.
- **B2.** Negative annual rate throws and has the message `"Annual rate must be between 0 and 100"`.
- **B3.** Annual rate above 100 (e.g. `100.01`) throws.
- **B4.** `months = 0` throws and has the message `"Term must be at least 1 month"`.
- **B5.** `Double.NaN` as principal throws.

### Part C: Boundary values
- **C1.** An annual rate of exactly `100` does **not** throw.
- **C2.** An annual rate of exactly `0` does **not** throw.

### Part D: Stretch (optional)
- **D1.** Rewrite Part A as one `@ParameterizedTest` using `@CsvSource`.
- **D2.** Write one `@ParameterizedTest` with `@ValueSource(doubles = {...})` covering every invalid principal you can think of (hint: there are more than 3).

---

## Running the tests

From the project root:

```bash
mvn test
```

Or right-click `LoanCalculatorTest` in your IDE and choose **Run**. All tests should be green.

**Sanity check:** temporarily change `principal <= 0` to `principal < 0` in `LoanCalculator.java`. At least one of your tests should go **red**. If none fail, your tests are not checking enough. Undo the change afterwards.

---

## Reflection questions

Answer briefly in a comment at the bottom of your test file:

1. Why is it better to check the exception **message** in addition to the exception **type**?
2. Why do we pass a **lambda** (`() -> ...`) to `assertThrows` instead of calling the method directly?
3. Why do we use a **delta** (`0.001`) in `assertEquals` for `double` values?
4. Name one input that a beginner would likely forget to test. Why is it risky?


