package bank.springmvc.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@ConfigurationProperties("db")
public class DBProperties {
    private static String driverClassName;
    private static String username;
    private static String password;
    private static String url;
    private static String table;

    /**
     * Constructor to set up Data Source from app.prop file
     * @return Data Source
     */
    @Bean
    public DataSource datasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /**
     * Constructor to set up JdbcTemplate from Data Source
     * @param dataSource Configured Data Source from app.props file
     * @return JdbcTemplate to use for sql queries
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        return jdbcTemplate;
    }

    // Getters and Setters
    public void setDriverclassname(String driverClassName) { DBProperties.driverClassName = driverClassName; }
    public void setUsername(String username) { DBProperties.username = username; }
    public void setPassword(String password) { DBProperties.password = password; }
    public void setUrl(String url) { DBProperties.url = url; }
    public void setTableName(String table) { DBProperties.table = table; }
    public static String getUsername() { return username; }
    public static String getPassword() { return password; }
    public static String getUrl() { return url; }
    public static String getDriverclassname() { return driverClassName; }
    public static String getTableName() { return table; }
}