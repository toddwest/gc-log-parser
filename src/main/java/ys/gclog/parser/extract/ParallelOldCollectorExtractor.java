/*
 * Copyright 2015 Yauheni Shahun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ys.gclog.parser.extract;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extractor for the PS MarkSweep collector.
 */
public class ParallelOldCollectorExtractor implements LogExtractor {

  private static final Pattern PATTERN =
      Pattern.compile(String.format("\\[ParOldGen: %s]", REGEX_MEMORY_FROM_TO_KB));

  @Override
  public boolean accept(String log) {
    return PATTERN.matcher(log).find();
  }

  @Override
  public Optional<Map<String, String>> extract(String log, ExtractContext context) {
    Matcher matcher = PATTERN.matcher(log);
    if (!matcher.find()) {
      return Optional.empty();
    }

    Map<String, String> values = new LinkedHashMap<>();
    values.put(LogField.MEM_OLD_BEFORE.toString(), matcher.group(1));
    values.put(LogField.MEM_OLD_AFTER.toString(), matcher.group(2));
    values.put(LogField.MEM_OLD_TOTAL.toString(), matcher.group(3));

    return Optional.of(values);
  }
}
