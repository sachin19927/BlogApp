package com.blog.crm.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blog.crm.enums.ModuleName;

/**
 * <p>
 * GenericLogger is a abstract class which contains static methods, which is
 * used to write log information in different log level in our customized format
 * EX:INFO,DEBUG,ERROR,TRACE and WARN. Levels used for identifying the severity
 * of an event.
 * </p>
 * 
 * @author SACHIN HS
 * @version %I% %G%
 * @since 19-06-2020
 *
 */
public class GenericLogger {

	private static final Logger LOGGER = LogManager.getLogger(GenericLogger.class);

	private static final String SYMBOL = " @:@ ";

	private GenericLogger() {
	}

	/**
	 * Logs a message with parameters at the {@link Level#INFO INFO} level.
	 * 
	 * @param moduleName name of the module which your working.
	 * @param className  name of the class where the info method is called.
	 * @param message    the message string to log.
	 */
	public static void info(ModuleName moduleName, Object className, String message) {
		String msg = moduleName + SYMBOL + className.getClass().getName() + SYMBOL + " INFO " + SYMBOL + message;
		LOGGER.info(msg);
	}

	/**
	 * Logs a message with parameters at the {@link Level#ERROR ERROR} level.
	 * 
	 * @param moduleName name of the module which your working.
	 * @param className  name of the class where the error method is called.
	 * @param e          the message string to log.
	 */
	public static void error(ModuleName moduleName, Object className, String error) {
		String errormsg = moduleName + SYMBOL + className.getClass().getName() + SYMBOL + " ERROR " + SYMBOL + error;
		LOGGER.error(errormsg);
	}

	/**
	 * Logs a message with parameters at the {@link Level#ERROR ERROR} level
	 * including the stack trace of the {@link Throwable} <code>e</code> passed as
	 * parameter.
	 *
	 * @param moduleName name of the module which your working.
	 * @param className  name of the class where the error method is called.
	 * @param e          the exception to log, including its stack trace.
	 */
	public static void error(ModuleName moduleName, Object className, Throwable e) {

		String msg = moduleName + SYMBOL + className.getClass().getName() + SYMBOL + " ERROR " + SYMBOL
				+ e.getMessage();
		LOGGER.error(msg, e);
	}

	/**
	 * Logs a message with parameters at the {@link Level#DEBUG DEBUG} level.
	 * 
	 * @param moduleName name of the module which your working.
	 * @param className  name of the class where the debug method is called.
	 * @param message    the message string to log.
	 */
	public static void debug(ModuleName moduleName, Object className, String message) {
		String debugmsg = moduleName + SYMBOL + className.getClass().getName() + SYMBOL + " DEBUG " + SYMBOL + message;
		LOGGER.debug(debugmsg);
	}

	/**
	 * Logs a message with parameters at the {@link Level#WARN WARN} level.
	 * 
	 * @param moduleName name of the module which your working.
	 * @param className  name of the class where the warning method is called.
	 * @param message    the message string to log.
	 */
	public static void warn(ModuleName moduleName, Object className, String message) {
		String warnmsg = moduleName + SYMBOL + className.getClass().getName() + SYMBOL + " WARN " + SYMBOL + message;
		LOGGER.warn(warnmsg);
	}

	/**
	 * Logs a message with parameters at the {@link Level#TRACE TRACE} level.
	 * 
	 * @param moduleName name of the module which your working.
	 * @param className  name of the class where the warning method is called.
	 * @param message    the message string to log.
	 */
	public static void trace(ModuleName moduleName, Object className, String message) {
		String warnmsg = moduleName + SYMBOL + className.getClass().getName() + SYMBOL + " TRACE " + SYMBOL + message;
		LOGGER.trace(warnmsg);
	}

}
