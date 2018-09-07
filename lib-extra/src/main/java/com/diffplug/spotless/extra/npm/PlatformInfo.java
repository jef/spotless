/*
 * Copyright 2016 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.extra.npm;

import java.util.Locale;
import java.util.Optional;

import com.diffplug.common.base.StandardSystemProperty;

class PlatformInfo {
	private PlatformInfo() {
		// no instance
	}

	static String normalizedOSName() {
		final String osNameProperty = StandardSystemProperty.OS_NAME.value();
		if (osNameProperty == null) {
			throw new RuntimeException("No info about OS available, cannot decide which implementation of j2v8 to use");
		}
		final String normalizedOsName = osNameProperty.toLowerCase(Locale.ENGLISH);
		if (normalizedOsName.contains("win")) {
			return "win32";
		}
		if (normalizedOsName.contains("mac")) {
			return "macosx";
		}
		if (normalizedOsName.contains("nix") || normalizedOsName.contains("nux") || normalizedOsName.contains("aix")) {
			return "linux";
		}
		throw new RuntimeException("Cannot handle os " + osNameProperty);
	}

	static String archName() {
		return Optional.ofNullable(StandardSystemProperty.OS_ARCH.value())
				.orElseThrow(() -> new RuntimeException("Arch not detectable."));
	}
}
