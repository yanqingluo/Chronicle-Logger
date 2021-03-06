/*
 * Copyright 2014 Higher Frequency Trading
 *
 * http://www.higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.logger.log4j1;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.IndexedChronicle;
import net.openhft.chronicle.logger.IndexedLogAppenderConfig;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;
import java.nio.ByteOrder;

public class TextIndexedChronicleAppender extends TextChronicleAppender {

    private final IndexedLogAppenderConfig config;
    private final Object lock;
    private ExcerptAppender appender;

    public TextIndexedChronicleAppender() {
        this.config = new IndexedLogAppenderConfig();
        this.lock = new Object();
        this.appender = null;
    }

    @Override
    protected Chronicle createChronicle() throws IOException {
        Chronicle chronicle = new IndexedChronicle(this.getPath(), this.config.cfg());
        this.appender = chronicle.createAppender();

        return chronicle;
    }

    @Override
    protected ExcerptAppender getAppender() {
        return this.appender;
    }

    @Override
    public void doAppend(final LoggingEvent event) {
        synchronized (this.lock) {
            super.doAppend(event);
        }
    }

    // *************************************************************************
    // IndexedLogAppenderConfig
    // *************************************************************************

    protected IndexedLogAppenderConfig getChronicleConfig() {
        return this.config;
    }

    public void setUseUnsafe(boolean useUnsafe) {
        config.setUseUnsafe(useUnsafe);
    }

    public void setByteOrder(ByteOrder byteOrder) {
        config.setByteOrder(byteOrder);
    }

    public void setIndexBlockSize(int indexBlockSize) {
        config.setIndexBlockSize(indexBlockSize);
    }

    public void setSynchronousMode(boolean synchronousMode) {
        config.setSynchronousMode(synchronousMode);
    }

    public void setCacheLineSize(int cacheLineSize) {
        config.setCacheLineSize(cacheLineSize);
    }

    public void setMessageCapacity(int messageCapacity) {
        config.setMessageCapacity(messageCapacity);
    }

    public void seMinimiseFootprint(boolean minimiseFootprint) {
        config.setMinimiseFootprint(minimiseFootprint);
    }

    public void setUseCheckedExcerpt(boolean useCheckedExcerpt) {
        config.setUseCheckedExcerpt(useCheckedExcerpt);
    }

    public void setDataBlockSize(int dataBlockSize) {
        config.setDataBlockSize(dataBlockSize);
    }

    public void setIndexFileExcerpts(int indexFileExcerpts) {
        config.setIndexFileExcerpts(indexFileExcerpts);
    }
}
