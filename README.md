# Description
This application handles loan processing for The Ledger Co, a marketplace for banks to lend money to borrowers and receive payments for the loans. The interest for the loan is calculated by `I = P * N * R` where P is the principal amount, N is the number of years and R is the rate of interest. The total amount to repay will be `A = P + I`. The amount should be paid back monthly in the form of EMIs. The borrowers can also pay a lump sum (that is, an amount more than their monthly EMI). In such a case, the lump sum will be deducted from the total amount (A) which can reduce the number of EMIs. This doesn’t affect the EMI amount unless the remaining total amount is less than the EMI. All transactions happen through The Ledger Co. This application manages the amount a user has paid to the bank and helps to determine how many EMIs are remaining.

## Requirements

The project is built using Java 1.8. It uses Maven as the build system.

1. Java - 1.8.x
2. Maven - 3.x.x

## Building the application using maven

You can build and package the application in the form of a jar file using maven -

```
mvn clean package
```

The above command will produce a standalone jar file named `geektrust.jar` in the `/target` directory.

## Running Tests

The `mvn package` command runs all the unit tests and also packages the application in the form of a jar file. If you just want to run the tests without packaging it, then you can use `mvn test` command.

```
mvn test
```

## Running the application

The application takes input commands from a file. You can pass multiple commands separated by a newline in the file. You can run the jar file created by the `mvn package` command as follows-

```
java -jar target/geektrust.jar <absolute_path_to_input_file>
```

## Supported Commands
There are 3 input commands defined to separate out the actions. The input format will start with either of these commands i.e `LOAN, PAYMENT, BALANCE`

### LOAN

The LOAN command receives a Bank name, Borrower name, Principal Amount, No of Years of Loan period and the Rate of Interest along with it.

Format - `LOAN <BANK_NAME> <BORROWER_NAME> <PRINCIPAL> <NO_OF_YEARS> <RATE_OF_INTEREST>`
Example- LOAN IDIDI Dale 10000 5 4 means a loan amount of 10000 is paid to Dale by IDIDI for a tenure of 5 years at 4% rate of interest.

### PAYMENT

The PAYMENT command receives a Bank name, Borrower name, Lump sum amount and EMI number along with it. The EMI number indicates that the lump sum payment is done after that EMI.

Format - `PAYMENT <BANK_NAME> <BORROWER_NAME> <LUMP_SUM_AMOUNT> <EMI_NO>`
Example - PAYMENT MBI Dale 1000 5 means a lump sum payment of 1000 was done by Dale to MBI after 5 EMI payments.

### BALANCE

The BALANCE command receives a Bank name, Borrower name and a EMI number along with it. It prints the total amount paid by the borrower, including all the Lump Sum amounts paid including that EMI number, and the no of EMIs remaining.

Input format - `BALANCE <BANK_NAME> <BORROWER_NAME> <EMI_NO>`
Example - BALANCE MBI Harry 12 means - print the amount paid including 12th EMI, and EMIs remaining for user Harry against the lender MBI.

Output format - <BANK_NAME> <BORROWER_NAME> <AMOUNT_PAID> <NO_OF_EMIS_LEFT>
Example - MBI Harry 1250 43
