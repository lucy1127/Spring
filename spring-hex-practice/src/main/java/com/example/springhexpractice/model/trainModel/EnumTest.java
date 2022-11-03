package com.example.springhexpractice.model.trainModel;

public class EnumTest {
    TrainKind trainKind;

    static String kind;

    public EnumTest(TrainKind trainKind) {
        this.trainKind = trainKind;
    }

    public EnumTest(String type) {
        this.kind = type;
    }

    public String trainType() {
        switch (trainKind) {
            case A:
                kind = "諾亞方舟號";
                break;

            case B:
                kind = "霍格華茲號";
                break;

        }
        return kind;
    }

    public TrainKind trainChangeType() {
        switch (kind) {
            case "諾亞方舟號":
                trainKind = TrainKind.A;
                break;

            case "霍格華茲號":
                trainKind = TrainKind.B;
                break;
        }
        return trainKind;
    }


}
