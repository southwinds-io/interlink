/*
    Interlink Configuration Management Database
    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

    Contributors to this project, hereby assign copyright in their code to the
    project, to be licensed under the same terms as the rest of the code.
*/
package io.southwinds.interlink.conf;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

@Component
public class Info implements Serializable {
    private static final long serialVersionUID = 1L;

    private String description;
    private String version;

    public Info() {
        this.description = "Onix Config Manager service.";
        this.version = getFile("version");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    private String getFile(String fileName) {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();
        // get file from resources folder
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String output = result.toString();
        return output.substring(0, output.length() - 1);
    }

    public long getSinceReleaseDays() {
        // TODO: what happens when the version does not have a date? = release
        try {
            int i = version.lastIndexOf('-');
            if (i > 0) {
                String dateString = version.substring(i + 1, version.length() - 1);
                int day = Integer.parseInt(dateString.substring(0, 2));
                int month = Integer.parseInt(dateString.substring(2, 4));
                int year = Integer.parseInt("20" + dateString.substring(4, 6));
                LocalDate today = LocalDate.now();
                LocalDate release = LocalDate.of(year, month, day);
                Period p = Period.between(release, today);
                return ChronoUnit.DAYS.between(release, today);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("%s - %s days ago.", version, getSinceReleaseDays());
    }
}
