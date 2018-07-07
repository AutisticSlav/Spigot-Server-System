package net.virushd.core.main;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class ConsoleFilter {

	private static ArrayList<String> messages = new ArrayList<>();
	
	public static void addFilter(String message) {
		messages.add(message);
	}
	
	public static void start() {
		((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(new Filter() {

			@Override
			public Result getOnMismatch() {
				return null;
			}

			@Override
			public Result getOnMatch() {
				return null;
			}

			@Override
			public Result filter(Logger logger, Level level, Marker marker, String s, Object... objects) {
				return null;
			}

			@Override
			public Result filter(Logger logger, Level level, Marker marker, Object o, Throwable throwable) {
				return null;
			}

			@Override
			public Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
				return null;
			}

			@Override
			public Result filter(LogEvent e) {
				for (int i = 0; i < messages.size(); i++) {
					if (e.getMessage().toString().toLowerCase().contains(messages.get(i).toLowerCase())) {
						return Result.DENY;
					}
				}
				return null;
			}
		});
	}
}
