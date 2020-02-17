package com.example.springredis;

import com.example.springredis.service.CacheService;
import com.example.springredis.service.ControlledCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.UUID;

@SpringBootApplication
@Slf4j
@EnableCaching
public class SpringRedisApplication implements CommandLineRunner {

	@Autowired
	CacheService cacheService;

	@Autowired
	ControlledCacheService controlledCacheService;

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		//	1) Test with @Cacheable and @EnableCaching, if not exist cache else fetch and return
		/*String firstString = cacheService.simpleCacheThis();
		log.info("First: {}", firstString);
		String secondString = cacheService.simpleCacheThis();
		log.info("Second: {}", secondString);*/

		// 2. Method that use @Cacheable if doesn't find then @CachePut (this method not check if exist)
		/*getFromControlledCache();
		getFromControlledCache();*/

		// clean cache @CacheEvict only the respective name cache
		/*cacheService.forgetAboutThis();
		controlledCacheService.removeFromCache();*/

		// 3. Generate key customize
		String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		String uuid3 = UUID.randomUUID().toString();
		String uuid4 = UUID.randomUUID().toString();
		log.info("UUID1: {}", uuid1);
		log.info("UUID2: {}", uuid2);
		log.info("UUID3: {}", uuid3);
		log.info("UUID4: {}", uuid4);
		String firstString = cacheService.cacheThis("param1", uuid1);
		log.info("First: {}", firstString);
		String secondString = cacheService.cacheThis("param1", uuid2);
		log.info("Second: {}", secondString);
		String thirdString = cacheService.cacheThis("AnotherParam", uuid3);
		log.info("Third: {}", thirdString);
		String fourthString = cacheService.cacheThis("AnotherParam", uuid4);
		log.info("Fourth: {}", fourthString);

		log.info("Starting controlled cache: -----------");
		String controlledFirst = getFromControlledCache("first");
		log.info("Controlled First: {}", controlledFirst);
		String controlledSecond = getFromControlledCache("second");
		log.info("Controlled Second: {}", controlledSecond);

		getFromControlledCache("first");
		getFromControlledCache("second");
		getFromControlledCache("third");

	}

	private String getFromControlledCache() {
		String fromCache = controlledCacheService.getFromCache();
		if (fromCache == null) {
			log.info("Cache was empty. Going to populate it");
			String newValue = controlledCacheService.populateCache();
			log.info("Populated Cache with: {}", newValue);
			return newValue;
		}
		log.info("Returning from Cache: {}", fromCache);
		return fromCache;
	}

	private String getFromControlledCache(String param) {
		String fromCache = controlledCacheService.getFromCache(param);
		if (fromCache == null) {
			log.info("Oups - Cache was empty. Going to populate it");
			String newValue = controlledCacheService.populateCache(param, UUID.randomUUID().toString());
			log.info("Populated Cache with: {}", newValue);
			return newValue;
		}
		log.info("Returning from Cache: {}", fromCache);
		return fromCache;
	}
}
