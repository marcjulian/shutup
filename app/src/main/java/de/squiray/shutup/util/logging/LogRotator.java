package de.squiray.shutup.util.logging;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

class LogRotator {

	private final Context context;

	private final List<File> logfiles;

	private volatile Logfile logfile;

	LogRotator(Context context) {
		if (Logfiles.NUMBER_OF_LOGFILES < 2) {
			throw new IllegalStateException("LogRotator needs at least two logfiles");
		}
		this.context = context;
		createLogDirIfMissing();
		this.logfiles = Logfiles.logfiles(context);
		this.logfile = new Logfile(indexOfNewestLogfile());
	}

	private void createLogDirIfMissing() {
		File logsDir = Logfiles.logsDir(context);
		if (!logsDir.exists() && !logsDir.mkdirs()) {
			throw new IllegalStateException("Creating logs dir failed");
		}
	}

	private int indexOfNewestLogfile() {
		int index = 0;
		long newestLastModified = 0L;
		for (int i = 0; i < logfiles.size(); i++) {
			long lastModified = logfiles.get(i).lastModified();
			if (lastModified > newestLastModified) {
				index = i;
				newestLastModified = lastModified;
			}
		}
		return index;
	}

	public void log(String message) {
		logfile().log(message);
	}

	private Logfile logfile() {
		// correct and fast double checked locking approach
		Logfile localLogfile = logfile;
		if (localLogfile.mustBeRotated()) {
			synchronized (this) {
				localLogfile = logfile;
				if (localLogfile.mustBeRotated()) {
					localLogfile = logfile = localLogfile.next();
				}
			}
		}
		return localLogfile;
	}

	private class Logfile {

		private final int index;
		private final PrintWriter writer;
		private SizeMeasuringOutputStream measuringOutputStream;

		private Logfile(int index, boolean deleteIfPresent) {
			this.index = index % logfiles.size();
			this.writer = open(logfiles.get(this.index), deleteIfPresent);
		}

		private Logfile(int index) {
			this(index, false);
		}

		private boolean mustBeRotated() {
			return measuringOutputStream.size() > Logfiles.ROTATION_FILE_SIZE;
		}

		private PrintWriter open(File logfile, boolean deleteIfPresent) {
			try {
				if (deleteIfPresent && logfile.exists()) {
					if (!logfile.delete()) {
						throw new IllegalStateException("Failed to delete log file");
					}
				}
				measuringOutputStream = new SizeMeasuringOutputStream(new FileOutputStream(logfile, true));
				return new PrintWriter(measuringOutputStream, true);
			} catch (IOException e) {
				throw new IllegalStateException("Opening ", e);
			}
		}

		private Logfile next() {
			writer.close();
			return new Logfile(index + 1, true);
		}

		public void log(String message) {
			writer.println(message);
		}
	}
}
