package Config;

public final class AppConfig {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/digital_wallet?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";

    private AppConfig() { }
}