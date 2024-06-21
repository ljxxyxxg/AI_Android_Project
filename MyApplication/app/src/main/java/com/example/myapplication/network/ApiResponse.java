package com.example.myapplication.network;

import com.example.myapplication.model.TrafficEvent;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {

    @SerializedName("header")
    private Header header;

    @SerializedName("body")
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Header {
        @SerializedName("resultCode")
        private int resultCode;

        @SerializedName("resultMsg")
        private String resultMsg;

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }

    public static class Body {
        @SerializedName("totalCount")
        private int totalCount;

        @SerializedName("items")
        private List<TrafficEvent> items;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<TrafficEvent> getItems() {
            return items;
        }

        public void setItems(List<TrafficEvent> items) {
            this.items = items;
        }
    }
}
