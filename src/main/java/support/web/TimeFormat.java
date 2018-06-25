package support.web;

public enum TimeFormat {
    NORMAL("yyyy-MM-dd HH:mm:ss");

    private String format;

    TimeFormat(String format) {
        this.format = format;
    }

    public static String getFormat(TimeFormat timeFormat) {
        return timeFormat.format;
    }
}
