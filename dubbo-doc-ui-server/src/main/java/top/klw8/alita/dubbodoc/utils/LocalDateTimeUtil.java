/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.dubbodoc.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;


/**
 * @author klw
 * @ClassName: LocalDateTimeUtil
 * @Description: Date time tool class of LocalDateTime
 * @date 2019-01-29 11:23:26
 */
public class LocalDateTimeUtil {

    /**
     * @param dateStr
     * @param dateFormatter
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: Create a LocalDateTime based on the passed in string date and the corresponding format
     */
    public static LocalDateTime formatToLDT(String dateStr, String dateFormatter) {
        //yyyy-MM-dd HH:mm:ss
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    }

    /**
     * @param dateStr
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: Create LocalDateTime from a string in the format "yyyy-MM-dd HH:mm:ss"
     */
    public static LocalDateTime formatToLDT(String dateStr) {
        return formatToLDT(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @param dateStr
     * @param dateFormatter
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: Create a LocalDate based on the passed in string date and the corresponding format
     */
    public static LocalDate formatToLD(String dateStr, String dateFormatter) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    }

    /**
     * @param dateStr
     * @return
     * @Title: formatToLDT
     * @author klw
     * @Description: Create LocalDate from a string in the format "yyyy-MM-dd"
     */
    public static LocalDate formatToLD(String dateStr) {
        return formatToLD(dateStr, "yyyy-MM-dd");
    }




}
