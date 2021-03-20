package com.rastelligualtieri.trainingschedule.server.apiresponse;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class ApiResponse {

    @JsonAlias("ErrorCode")
    @JsonProperty(value = "error")
    String errorCode;
    @JsonAlias("Message")
    @JsonProperty(value = "message")
    String message;
    @JsonAlias("Result")
    @JsonProperty(value = "result")
    @JsonRawValue
    String result;
    @JsonAlias("Timestamp")
    @JsonProperty(value = "timestamp")
    String timeStamp;
    @JsonAlias("Status")
    @JsonProperty(value = "status")
    int status;
    @JsonAlias("Path")
    @JsonProperty(value = "path")
    String path;

    /**
     * Method to create OK response body
     * @param path
     * @return
     */
    public static ApiResponse resultOk(String path, String message, String result) {
        String time = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(new Date());
        return ApiResponse.builder()
                .errorCode("")
                .message(message)
                .result(result)
                .timeStamp(time)
                .status(200)
                .path(path)
                .build();
    }

    /**
     * Method to create KO response body
     * @param code
     * @param message
     * @param path
     * @param stato
     * @return
     */
    public static ApiResponse resultKo(String code, String message, String path, int stato) {
        String time = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(new Date());
        return ApiResponse.builder()
                .errorCode(code)
                .message(message)
                .result("{}")
                .timeStamp(time)
                .status(stato)
                .path(path)
                .build();
    }

}
