/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.dynamictp.adapter.webserver.undertow.taskpool;

import org.dromara.dynamictp.adapter.webserver.undertow.TaskPoolHandlerFactory;
import org.dromara.dynamictp.adapter.webserver.undertow.UndertowTaskPoolEnum;
import org.dromara.dynamictp.common.util.ReflectionUtil;
import org.dromara.dynamictp.core.support.ExecutorAdapter;
import lombok.val;

import static org.dromara.dynamictp.adapter.webserver.undertow.UndertowTaskPoolEnum.EXTERNAL_TASK_POOL;

/**
 * ExternalTaskPoolHandler related
 *
 * @author yanhom
 * @since 1.1.3
 */
public class ExternalTaskPoolAdapter implements TaskPoolAdapter {

    @Override
    public UndertowTaskPoolEnum taskPoolType() {
        return EXTERNAL_TASK_POOL;
    }

    @Override
    public ExecutorAdapter<?> adapt(Object obj) {
        String taskPoolClassName = obj.getClass().getSimpleName();
        val handler = TaskPoolHandlerFactory.getTaskPoolHandler(taskPoolClassName);
        Object executor = ReflectionUtil.getFieldValue(obj.getClass(), handler.taskPoolType().getInternalExecutor(), obj);
        return handler.adapt(executor);
    }
}
