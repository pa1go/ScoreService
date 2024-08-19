package com.gameapp.scorecc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppConfigTest {

    @Mock
    private Environment env;

    @InjectMocks
    private AppConfig appConfig;

    @Test
    public void testDataSourceBean() {
        // Mock environment properties
        when(env.getProperty("driverClassName")).thenReturn("org.sqlite.JDBC");
        when(env.getProperty("url")).thenReturn("jdbc:mysql://localhost:3306/mydb");
        when(env.getProperty("user")).thenReturn("root");
        when(env.getProperty("password")).thenReturn("password");

        // Get the DataSource bean
        DataSource dataSource = appConfig.dataSource();

        // Verify the DataSource bean is not null and properties are set correctly
        assertNotNull(dataSource);
        // You can add additional assertions to check the configuration of the dataSource if needed
    }
}
