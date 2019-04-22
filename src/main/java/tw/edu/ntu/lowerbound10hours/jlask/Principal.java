package fr.paris10.pascalpoizat.templates.javaproject;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * template-java-project
 * Copyright 2015 pascalpoizat
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Principal {

    private static final Logger LOGGER = LogManager.getLogger(Principal.class.getName());

    private Principal() {

    }

    public static void main(String[] args) {
        LOGGER.trace("begin of program"); // only for test
        Point p = new Point(3, 4);
        LOGGER.info(p.toString()); // a message for the user
        LOGGER.warn("a fake warning");
        LOGGER.error("a fake error");
        LOGGER.trace("end of program"); // only for test
    }
}
