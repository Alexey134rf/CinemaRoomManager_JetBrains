package com.CinemaRoomManager;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class CinemaRoomManager {
    public static Scanner sc = new Scanner(System.in);
    private static final char CHAR_SEAT = 'S';
    private static final char CHAR_BUY_SEAT = 'B';

    public static void main(String[] args) {
        startProgram();
    }

    public static void startProgram(){
        int numberOfRows = requestNumberOfRows();
        int numberOfSeats = requestNumberOfSeats();
        char[][] cinemaArray = createCinema(numberOfRows, numberOfSeats);
        menuProgram(cinemaArray, numberOfRows, numberOfSeats);
    }

    private static int requestNumberOfRows() {
        System.out.println("Enter the number of rows:");
        int numberOfRows = sc.nextInt();
        return numberOfRows;
    }

    private static int requestNumberOfSeats() {
        System.out.println("Enter the number of seats in each row:");
        int numberOfSeats = sc.nextInt();
        return numberOfSeats;
    }

    private static void menuProgram(char[][] cinemaArray, int numberOfRows, int numberOfSeats) {
        while (true) {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int number;
            while (true) {
                number = sc.nextInt();
                if (number >= 0 && number <= 3) {
                    break;
                }
                System.out.println("Invalid number entered!");
            }
            if (number == 0) {
                break;
            }

            switch (number) {
                case 0:
                    break;
                case 1:
                    printCinema(cinemaArray);
                    break;
                case 2:
                    buyTicket(cinemaArray, numberOfRows, numberOfSeats);
                    break;
                case 3:
                    calculatingStatistics(cinemaArray, numberOfRows, numberOfSeats);
                    break;
            }
        }
    }

    private static char[][] createCinema(int numberOfRows, int numberOfSeats ) {
        char[][] cinemaArray = new char[numberOfRows][numberOfSeats];

        for (int i = 0; i < cinemaArray.length; i++) {
            for (int j = 0; j <cinemaArray[i].length; j++) {
                cinemaArray[i][j] = CHAR_SEAT;
            }
        }
        return cinemaArray;
    }

    private static void printCinema(char[][] arrayCinema) {
        for (int i = 0; i <= arrayCinema[0].length; i++) {
            if (i == 0) {
                System.out.print("\nCinema:\n  ");
            } else  {
                System.out.print(i + " ");
            }
        }

        for (int i = 0; i < arrayCinema.length; i++) {
            System.out.println("");
            for (int j = 0; j < arrayCinema[i].length; j++) {
                if (j != 0) {
                    System.out.print(arrayCinema[i][j] + " ");
                } else {
                    System.out.print((i + 1) + " " + arrayCinema[i][j] + " ");
                }
            }
        }
        System.out.println("");
    }

    private static void buyTicket(char[][] cinemaArray, int numberOfRowsStart, int numberOfSeatsStart) {
        int totalIncome;
        int numberOfRows;
        int numberOfSeats;
        while (true) {
            numberOfRows = requestNumberOfRows();
            numberOfSeats = requestNumberOfSeats();
            if (!(chekIndexRange(cinemaArray, numberOfRows, numberOfSeats))) {
                System.out.println("Wrong input!\n");
            } else {
                if (checkModArray(cinemaArray, numberOfRows, numberOfSeats)) {
                    System.out.println("\nThat ticket has already been purchased!\n");
                    while (true) {
                        numberOfRows = requestNumberOfRows();
                        numberOfSeats = requestNumberOfSeats();
                        if (!(chekIndexRange(cinemaArray, numberOfRows, numberOfSeats))) {
                            System.out.println("Wrong input!\n");
                        } else {
                            if (checkModArray(cinemaArray, numberOfRows, numberOfSeats) == false) {
                                break;
                            } else {
                                System.out.println("\nThat ticket has already been purchased!\n");
                            }
                        }
                    }
                }
                break;
            }
        }

        int totalSeats = numberOfRowsStart * numberOfSeatsStart;
        if (totalSeats <= 60) {
            totalIncome = 10;
        } else {
            if (numberOfRows <= numberOfRowsStart / 2) {
                totalIncome = 10;
            } else {
                totalIncome = 8;
            }
        }
        modArray(cinemaArray, numberOfRows, numberOfSeats);
        System.out.printf("Ticket price: $%s%n", totalIncome);
    }

    private static boolean chekIndexRange(char[][] array, int numberOfRows, int numberOfSeats) {
        if (numberOfRows < 0 || numberOfRows > array.length || numberOfSeats < 0 || numberOfSeats > array[0].length) {
            return false;
        }
        else return true;
    }

    private static char[][] modArray(char[][] cinemaArray, int numberOfRow, int numberOfSeat) {
        cinemaArray[numberOfRow - 1][numberOfSeat - 1] = CHAR_BUY_SEAT;
        return cinemaArray;
    }

    private static boolean checkModArray(char[][] cinemaArray, int numberOfRow, int numberOfSeat) {
        if (cinemaArray[numberOfRow - 1][numberOfSeat - 1] == CHAR_BUY_SEAT) {
            return true;
        } else  return false;
    }

    private static void calculatingStatistics(char[][] arrayCinema, int numberOfRowsStart, int numberOfSeatsStart) {
        System.out.printf("%nNumber of purchased tickets: %d%n", buyTicketCounter(arrayCinema));
        System.out.printf("Percentage: %.2f%%%n", buyTicketPercentage(arrayCinema,numberOfRowsStart, numberOfSeatsStart));
        System.out.printf("Current income: $%d%n", calculationСurrentIncome(arrayCinema, numberOfRowsStart, numberOfSeatsStart));
        System.out.printf("Total income: $%d%n", calculationСurrentIncome(numberOfRowsStart, numberOfSeatsStart));
    }

    private static int buyTicketCounter(char[][] arrayCinema) {
        int counter = 0;
        for (int i = 0; i < arrayCinema.length; i++) {
            for (int j = 0; j < arrayCinema[i].length; j++) {
                if (arrayCinema[i][j] == CHAR_BUY_SEAT) {
                    counter++;
                }
            }
        }  return counter;
    }

    private static double buyTicketPercentage(char[][] arrayCinema, int numberOfRowsStart, int numberOfSeatsStart) {
        return  100 * buyTicketCounter(arrayCinema) /  (double) (numberOfRowsStart * numberOfSeatsStart);
    }

    private static int calculationСurrentIncome(char[][] cinemaArray, int numberOfRowsStart, int numberOfSeatsStart) {
        int currentIncome = 0;
        int totalSeats = numberOfRowsStart * numberOfSeatsStart;
        for (int i = 0; i < cinemaArray.length; i++) {
            for (int j = 0; j < cinemaArray[i].length; j++) {
                if (cinemaArray[i][j] == 'B') {
                    if (totalSeats <= 60) {
                        currentIncome += 10;
                    } else {
                        if (i <= numberOfRowsStart / 2) {
                            currentIncome += 10;
                        } else {
                            currentIncome += 8;
                        }

                    }
                }
            }
        } return currentIncome;
    }

    private static int calculationСurrentIncome(int numberOfRowsStart, int numberOfSeatsStart) {
        int totalIncome;
        int totalSeats = numberOfRowsStart * numberOfSeatsStart;
        if (totalSeats <= 60) {
            totalIncome = 10 * totalSeats;
        } else {
            if (numberOfRowsStart % 2 == 0) {
                totalIncome = numberOfRowsStart / 2 * numberOfSeatsStart * 10 + numberOfRowsStart / 2 * numberOfSeatsStart * 8;
            } else {
                totalIncome = numberOfRowsStart / 2 * numberOfSeatsStart * 10 + (numberOfRowsStart / 2 + 1) * numberOfSeatsStart * 8;
            }
        }
        return  totalIncome;
    }
}



