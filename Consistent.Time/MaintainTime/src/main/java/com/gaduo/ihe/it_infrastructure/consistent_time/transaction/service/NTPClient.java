package com.gaduo.ihe.it_infrastructure.consistent_time.transaction.service;

/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Date;

import org.apache.commons.net.ntp.NtpUtils;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.log4j.Logger;

/***
 * af This is an example program demonstrating how to use the NTPUDPClient
 * class. This program sends a Datagram client request packet to a Network time
 * Protocol (NTP) service port on a specified server, retrieves the time, and
 * prints it to standard output along with the fields from the NTP message
 * header (e.g. stratum level, reference id, poll interval, root delay, mode,
 * ...) See <A HREF="ftp://ftp.rfc-editor.org/in-notes/rfc868.txt"> the spec
 * </A> for details.
 * <p>
 * Usage: NTPClient <hostname-or-address-list> <br>
 * Example: NTPClient clock.psu.edu
 * 
 * @author Jason Mathews, MITRE Corp
 ***/
public final class NTPClient {
	public static Logger logger = Logger.getLogger(NTPClient.class);
	private static final NumberFormat numberFormat = new java.text.DecimalFormat(
			"0.00");

	/**
	 * Process <code>TimeInfo</code> object and print its details.
	 * 
	 * @param info
	 *            <code>TimeInfo</code> object.
	 */
	public Date processResponse(TimeInfo info) {
		NtpV3Packet message = info.getMessage();
		int stratum = message.getStratum(); // 層級
		String refType;
		if (stratum <= 0)
			refType = "(未指定或不可用)";
		else if (stratum == 1)
			refType = "(主要參考; e.g., GPS)"; // GPS, radio clock,
											// etc.
		else
			refType = "(次要參考; e.g. via NTP or SNTP)";
		// stratum should be 0..15...
		logger.info(" Stratum: " + stratum + " " + refType);

		int leap = message.getLeapIndicator(); // 躍進指示器
		int version = message.getVersion(); // 版本
		int precision = message.getPrecision(); // 精確度
		logger.info(" leap=" + leap);
		logger.info(" version=" + version);
		logger.info(" precision=" + precision);
		String modeName = message.getModeName();
		int mode = message.getMode();
		logger.info(" mode: " + modeName + " (" + mode + ")");
		int poll = message.getPoll();
		// poll value typically btwn MINPOLL (4) and MAXPOLL (14)
		logger.info(" poll: " + (poll <= 0 ? 1 : (int) Math.pow(2, poll)));
		logger.info(" seconds" + " (2 ** " + poll + ")");
		double rootDisp = message.getRootDispersionInMillisDouble();
		double rootDelay = message.getRootDelayInMillisDouble();
		logger.info(" rootdelay=" + numberFormat.format(rootDisp));
		logger.info(" rootdispersion(ms): " + numberFormat.format(rootDelay));

		int refId = message.getReferenceId();
		String refAddr = NtpUtils.getHostAddress(refId);
		String refName = null;
		if (refId != 0) {
			if (refAddr.equals("127.127.1.0")) {
				refName = "LOCAL"; // This is the ref address for the Local
									// Clock
			} else if (stratum >= 2) {
				// If reference id has 127.127 prefix then it uses its own
				// reference clock
				// defined in the form 127.127.clock-type.unit-num (e.g.
				// 127.127.8.0 mode 5
				// for GENERIC DCF77 AM; see refclock.htm from the NTP software
				// distribution.
				if (!refAddr.startsWith("127.127")) {
					try {
						InetAddress addr = InetAddress.getByName(refAddr);
						String name = addr.getHostName();
						if (name != null && !name.equals(refAddr))
							refName = name;
					} catch (UnknownHostException e) {
						// some stratum-2 servers sync to ref clock device but
						// fudge stratum level higher... (e.g. 2)
						// ref not valid host maybe it's a reference clock name?
						// otherwise just show the ref IP address.
						refName = NtpUtils.getReferenceClock(message);
					}
				}
			} else if (version >= 3 && (stratum == 0 || stratum == 1)) {
				refName = NtpUtils.getReferenceClock(message);
				// refname usually have at least 3 characters (e.g. GPS, WWV,
				// LCL, etc.)
			}
			// otherwise give up on naming the beast...
		}
		if (refName != null && refName.length() > 1)
			refAddr += " (" + refName + ")";
		logger.info(" Reference Identifier:\t" + refAddr);

		TimeStamp refNtpTime = message.getReferenceTimeStamp();
		logger.info(" Reference Timestamp:\t" + refNtpTime + "  "
				+ refNtpTime.toDateString());

		// Originate Time is time request sent by client (t1)
		TimeStamp origNtpTime = message.getOriginateTimeStamp();
		logger.info(" Originate Timestamp:\t" + origNtpTime + "  "
				+ origNtpTime.toDateString());

		// Receive Time is time request received by server (t2)
		TimeStamp rcvNtpTime = message.getReceiveTimeStamp();
		logger.info(" Receive Timestamp:\t" + rcvNtpTime + "  "
				+ rcvNtpTime.toDateString());

		// Transmit time is time reply sent by server (t3)
		TimeStamp xmitNtpTime = message.getTransmitTimeStamp();
		logger.info(" Transmit Timestamp:\t" + xmitNtpTime + "  "
				+ xmitNtpTime.toDateString());

		// Destination time is time reply received by client (t4)
		long destTime = info.getReturnTime();
		TimeStamp destNtpTime = TimeStamp.getNtpTime(destTime);
		logger.info(" Destination Timestamp:\t" + destNtpTime + "  "
				+ destNtpTime.toDateString());
		info.computeDetails(); // compute offset/delay if not already done
		Long offsetValue = info.getOffset();
		Long delayValue = info.getDelay();
		String delay = (delayValue == null) ? "N/A" : delayValue.toString();
		String offset = (offsetValue == null) ? "N/A" : offsetValue.toString();

		logger.info(" Roundtrip delay(ms)=" + delay);
		logger.info(" clock offset(ms)=" + offset); // offset in ms
		

		Date Date = refNtpTime.getDate();
		return Date;
	}
}
