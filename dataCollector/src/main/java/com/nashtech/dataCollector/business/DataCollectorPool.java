package com.nashtech.dataCollector.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nashtech.dataCollector.enums.TdlogResultCode;
import com.nashtech.dataCollector.pojo.TdlogExtendResult;

@Service
@Transactional
public class DataCollectorPool {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorPool.class);

	private ExecutorService executor = null;

	private List<Future<TdlogExtendResult>> list = null;

	private boolean initFlag = false;

	public DataCollectorPool() {
		executor = Executors.newFixedThreadPool(20);
		list = new ArrayList<>();
		initFlag = true;
	}

	public void submitTask(DataCollectorRunnableTask dataCoolectorRunnableTask) {
		Future<TdlogExtendResult> f = (Future<TdlogExtendResult>) executor.submit(dataCoolectorRunnableTask);
		list.add(f);

	}

	public boolean isIntiFlag() {
		return initFlag;
	}

	public void setIntiFlag(boolean intiFlag) {
		this.initFlag = intiFlag;
	}

	// for collecting all the record having problem
	public List<TdlogExtendResult> getResultStatus() {

		List<TdlogExtendResult> ll = new ArrayList<>();
		List<Future<TdlogExtendResult>> rr = list.stream().filter(r -> {
			try {
				if (r.get() != null && r.get().getErrorLevel() != TdlogResultCode.OK.getResultCode()) {
					return true;
				}
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.debug("Unable to fetch the result");
				 Thread.currentThread().interrupt();
			}
			return false;
		}).collect(Collectors.toList());

		rr.forEach(item -> {
			try {
				ll.add(item.get());
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.debug("Unable to fetch the Future result");
				 Thread.currentThread().interrupt();
			}
		});
		return ll;
	}

	public void executorToFinish() {
		list.forEach(r -> {
			try {
				r.get();
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.debug("Unable to fetch the result");
				 Thread.currentThread().interrupt();
			}
		});
	}

	public void shutdown() {
		executor.shutdown();
	}

}
