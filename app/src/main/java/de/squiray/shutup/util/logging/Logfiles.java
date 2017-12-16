package de.squiray.shutup.util.logging;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Logfiles {

	static final int NUMBER_OF_LOGFILES = 2;

	/**
	 * Maximum size of all logfiles
	 */
	private static final long MAX_LOGS_SIZE = 1 << 20; // 1 MiB

	/**
	 * When this size is reached a logfile is rotated
	 */
	static final long ROTATION_FILE_SIZE = MAX_LOGS_SIZE / Logfiles.NUMBER_OF_LOGFILES;

	public static List<File> logfiles(Context context) {
		List<File> result = new ArrayList<>(NUMBER_OF_LOGFILES);
		for (int i = 0; i < NUMBER_OF_LOGFILES; i++) {
			result.add(logfile(context, i));
		}
		return result;
	}

	static File logsDir(Context context) {
		return new File(context.getCacheDir(), "logs");
	}

	public static List<File> existingLogfiles(Context context) {
		List<File> result = new ArrayList<>(NUMBER_OF_LOGFILES);
		for (File logfile : logfiles(context)) {
			if (logfile.exists()) {
				result.add(logfile);
			}
		}
		return result;
	}

	private static File logfile(Context context, int index) {
		return new File(logsDir(context), logfileName(index));
	}

	private static String logfileName(int index) {
		return format(index + ".txt");
	}

	private Logfiles() {
	}

}
