package de.squiray.shutup.util.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

class FormattedTime {

	private static final String FORMAT = "yyyyMMddHHmmss.SSS";

	private static volatile FormattedTime now = new FormattedTime(0);

	public static FormattedTime now() {
		FormattedTime localNow = now;
		if (localNow.timestamp < currentTimeMillis()) {
			localNow = new FormattedTime(currentTimeMillis());
			now = localNow;
		}
		return localNow;
	}

	private final long timestamp;
	private volatile String formatted;

	private FormattedTime(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		if (formatted == null) {
			formatted = new SimpleDateFormat(FORMAT).format(new Date(timestamp));
		}
		return formatted;
	}
}
