package com.bevan.thread.source;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 批量修改ID，采用异步操作
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<String> businessSystemIds = List.of("ID1", "ID2", "ID3", "ID4");

        List<CompletableFuture<String>> disableTasks = businessSystemIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> disableSystem(id)))
                .collect(Collectors.toList());

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
                disableTasks.toArray(new CompletableFuture[0]) // 转成数组
        );

        // 在所有任务完成后，获取每个任务的结果
        CompletableFuture<List<String>> allResults = allTasks.thenApply(v ->
                disableTasks.stream()
                        .map(CompletableFuture::join)  // 获取每个任务的返回结果
                        .collect(Collectors.toList())
        );

        // 获取最终的批量停用结果
        List<String> results = allResults.get();
        System.out.println("批量停用结果: " + results);
    }

    // 模拟停用业务系统的方法
    public static String disableSystem(String systemId) {
        // 这里可以是数据库操作或远程服务调用，修改系统状态为“停用”
        System.out.println("停用系统: " + systemId);
        return "系统 " + systemId + " 已停用";
    }
}


