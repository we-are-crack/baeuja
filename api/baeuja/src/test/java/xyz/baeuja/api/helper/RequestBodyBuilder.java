package xyz.baeuja.api.helper;

import java.util.Map;

public class RequestBodyBuilder {

    public static String buildRequestBody(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("{\n");
        int size = params.size();
        int count = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append("  \"").append(entry.getKey()).append("\": \"")
                    .append(entry.getValue()).append("\"");
            if (count++ < size - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        return sb.append("}").toString();
    }

}
