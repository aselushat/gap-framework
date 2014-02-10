/*
 * Copyright (c) 2012, Liushangkun520@hotmail.com
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
package com.lianjita.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author shangkun.liusk
 */
public class DefaultErrorContext implements ErrorContext {

    private List<ErrorItem> errors = new ArrayList<ErrorItem>();

    private boolean         hasSystemError;

    private Throwable       exception;

    public Throwable getCurrentExceptionCause() {
        return exception;
    }

    public void setCurrentExceptionCause(Throwable exception) {
        this.exception = exception;
    }

    public boolean hasError() {
        return this.errors.size() > 0;
    }

    public Iterator<ErrorItem> getErrrors() {
        return this.hasError() ? this.errors.iterator() : null;
    }

    public ErrorContext addError(ErrorItem error) {

        this.errors.add(error);

        return this;
    }

    public void setHasSystemError(boolean hasSystemError) {
        this.hasSystemError = hasSystemError;
    }

    public boolean hasSystemError() {
        return this.hasSystemError;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hasSystemError=" + this.hasSystemError).append("\n");
        buffer.append("hasError=" + Boolean.toString(this.errors.size() > 0)).append("\n");
        buffer.append("errors:").append("\n");
        for (ErrorItem item : errors) {
            buffer.append("Field:" + item.getField()).append(" code:" + item.getCode()).append(" msg:" + item.getMsg())
                    .append("\n");
        }
        return buffer.toString();
    }
}
