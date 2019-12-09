package com.baggins.abcqueryapp.repositories.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:queryVersionManagement.properties")
@ConfigurationProperties(prefix = "employee-repository.find-all-by-hire-date-range")
public class FindEmployeesByHireDateRangeConfig {
    private double version;
    private int hiredFromDefaultYear;
    private int hiredFromDefaultMonth;
    private int hiredFromDefaultDay;

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public int getHiredFromDefaultYear() {
        return hiredFromDefaultYear;
    }

    public void setHiredFromDefaultYear(int hiredFromDefaultYear) {
        this.hiredFromDefaultYear = hiredFromDefaultYear;
    }

    public int getHiredFromDefaultMonth() {
        return hiredFromDefaultMonth;
    }

    public void setHiredFromDefaultMonth(int hiredFromDefaultMonth) {
        this.hiredFromDefaultMonth = hiredFromDefaultMonth;
    }

    public int getHiredFromDefaultDay() {
        return hiredFromDefaultDay;
    }

    public void setHiredFromDefaultDay(int hiredFromDefaultDay) {
        this.hiredFromDefaultDay = hiredFromDefaultDay;
    }
}
