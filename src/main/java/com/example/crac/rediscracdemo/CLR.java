/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.crac.rediscracdemo;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Christoph Strobl
 * @since 2023/06
 */
@Component
public class CLR implements CommandLineRunner, SmartLifecycle {

	StringRedisTemplate template;
	AtomicBoolean run = new AtomicBoolean(false);
	ApplicationContext ctx;

	public CLR(StringRedisTemplate template, ApplicationContext ctx) {
		this.template = template;
		this.ctx = ctx;
	}

	@Override
	public void run(String... args) throws Exception {

		long i = 0L;
		while (isRunning()) {
			Thread.sleep(5000);
			try {
				String counter = template.opsForValue().getAndSet("counter", String.format("%s", ++i));
				System.out.println("counter value: " + counter);
			} catch (java.lang.IllegalArgumentException | IllegalStateException e) {
				System.out.println("connection not yet ready");
			}
		}
	}

	@Override
	public void start() {
		run.set(true);
	}

	@Override
	public void stop() {
		run.set(false);
	}

	@Override
	public boolean isRunning() {
		return run.get();
	}
}
