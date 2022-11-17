package com.example.springhexpractice.domain.aggreate.util;

public enum ErrorCode {

    TRAIN_NO_IS_POSITIVE("Min", "車次必須為正整數"),
    TRAIN_STOP_NO("via", "Required String parameter 'via' is not present"),
    Stops("NotEmpty", "停靠站不可為空"),
    TRAIN_STATUS("TrainNotAvailable", "Train is not available"),
    TRAIN_EXIST("TrainNoExists", "Train No is exists"),
    TRAIN_KIND("TrainKindInvalid", "Train Kind is invalid"),
    TRAIN_MULTI_STOP("TrainStopsDuplicate", "Train Stops is duplicate"),
    TRAIN_STOP_SORTED("TrainStopsNotSorted", "Train Stops is not sorted"),
    TRAIN_STOPS_INVALID("TicketStopsInvalid", "Ticket From & To is invalid"),
    TAKE_DATE("Pattern", "日期格式不正確 yyyy-mm-dd"),
    WRONG_TIME_FORMAT("WrongTimeFormat", "時間格式錯誤");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
