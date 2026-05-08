package com.affordmed.logging_middleware.util;

import com.affordmed.logging_middleware.service.LoggingService;

public class Log {

    private static LoggingService loggingService;

    private Log() {
    }

    public static void setLoggingService(
            LoggingService service
    ) {
        loggingService = service;
    }

    private static boolean isValidPackage(
            String packageName
    ) {

        return packageName.equals("controller")
                || packageName.equals("service")
                || packageName.equals("handler")
                || packageName.equals("repository")
                || packageName.equals("route")
                || packageName.equals("middleware")
                || packageName.equals("utils")
                || packageName.equals("db")
                || packageName.equals("domain")
                || packageName.equals("auth")
                || packageName.equals("config")
                || packageName.equals("cache")
                || packageName.equals("cron_job");
    }

    public static void info(
            String packageName,
            String message
    ) {

        if (!isValidPackage(packageName)) {
            return;
        }

        loggingService.log(
                "backend",
                "info",
                packageName,
                message
        );
    }

    public static void error(
            String packageName,
            String message
    ) {

        if (!isValidPackage(packageName)) {
            return;
        }

        loggingService.log(
                "backend",
                "error",
                packageName,
                message
        );
    }

    public static void debug(
            String packageName,
            String message
    ) {

        if (!isValidPackage(packageName)) {
            return;
        }

        loggingService.log(
                "backend",
                "debug",
                packageName,
                message
        );
    }

    public static void warn(
            String packageName,
            String message
    ) {

        if (!isValidPackage(packageName)) {
            return;
        }

        loggingService.log(
                "backend",
                "warn",
                packageName,
                message
        );
    }

    public static void fatal(
            String packageName,
            String message
    ) {

        if (!isValidPackage(packageName)) {
            return;
        }

        loggingService.log(
                "backend",
                "fatal",
                packageName,
                message
        );
    }
}