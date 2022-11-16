package com.example.springhexpractice.exception;

public enum ErrorCode {

    TrainNoIsPositive("Min","車次必須為正整數"),
    TrainStopNo("via","Required String parameter 'via' is not present"),
    Stops("NotEmpty","停靠站不可為空"),
    TrainStatus("TrainNotAvailable","Train is not available"),
    TrainExist("TrainNoExists","Train No is exists"),
    TrainKind("TrainKindInvalid","Train Kind is invalid"),
    TrainMultiStop("TrainStopsDuplicate","Train Stops is duplicate"),
    TrainStopSorted("TrainStopsNotSorted","Train Stops is not sorted"),
    TrainStopsInvalid("TicketStopsInvalid","Ticket From & To is invalid"),
    TakeDate("Pattern","日期格式不正確 yyyy-mm-dd"),
    TrainNoIsEmpty("NotNull","車次不可為空"),
    TrainKindIsEmpty("NotEmpty","車種不可為空"),
    TrainStopNameTooLong("NameTooLong","The string length of name must be less than 20"),
    TrainStopTimeIsEmpty("NotEmpty","停靠時間不可為空"),
    TrainFromStopIsEmpty("NotEmpty","上車站不可為空"),
    TrainToStopIsEmpty("NotEmpty","下車站不可為空"),
    WrongTimeFormat("WrongTimeFormat","時間格式錯誤");



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
