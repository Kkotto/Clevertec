package com.kkotto.Clevertec.service.util.consts;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final double VAT_VALUE = 0.17;
    public static final int PRODUCT_AMOUNT_FOR_DISCOUNT = 5;
    public static final double FULL_PRICE_VALUE = 1;
    public static final double DISCOUNT_VALUE_FOR_PRODUCT_AMOUNT = 0.1;
    public static final double NO_DISCOUNT_VALUE = 0;
    public static final int DISCOUNT_PERCENT_COEFFICIENT = 100;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String PRODUCT_LIST_FORMAT_FILENAME = "Products\\Products-%s.csv";
    public static final String PRODUCT_LIST_FORMAT_HEADER = "id;name;price;createAt\n";
    public static final String PRODUCT_LIST_FORMAT_TEMPLATE = "%d;%s;%.2f;%s\n";
    public static final String PRODUCT_LIST_REGEX_TEMPLATE = "^\\d+;.*?;\\d+,\\d{2};\\d{2}-\\d{2}-\\d{4} \\d{2}-\\d{2}-\\d{2}";
    public static final String PRODUCT_PARAMS_SEPARATOR_REGEX = ";";
    public static final String DIRECTORY_REGEX = "\\";
    public static final String EMPTY_LINE = "\n";
    public static final String CSV_FILENAME_REGEX = "^.*\\.csv$";
    public static final int PARAM_NUMBER_PRODUCT_NAME_FOR_READING_FILE = 1;
    public static final int PARAM_NUMBER_PRODUCT_PRICE_FOR_READING_FILE = 2;
    public static final int PARAM_NUMBER_PRODUCT_CREATE_AT_FOR_READING_FILE = 3;
    public static final String BIG_DECIMAL_INCORRECT_SEPARATOR = ",";
    public static final String BIG_DECIMAL_CORRECT_SEPARATOR = ".";
}
